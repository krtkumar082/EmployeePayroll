package com.empdb;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.employeepayroll.EmpPayrollData;


public class EmployeePayrollFileDBService {
    
	private PreparedStatement employeePayrollDataStatement;
	private static EmployeePayrollFileDBService employeePayrollDBService;

	private EmployeePayrollFileDBService() {

	}

	public static EmployeePayrollFileDBService getInstance() {
		if (employeePayrollDBService == null)
			employeePayrollDBService = new EmployeePayrollFileDBService();
		return employeePayrollDBService;
	}
	
	private Connection getConnection() throws SQLException {
		String jdbcURL="jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
		   String userName="root";
		   String password="Sushila@7838";
		   Connection connection=null;
		   System.out.println("Connecting to DB "+jdbcURL);
		   connection=DriverManager.getConnection(jdbcURL,userName,password);
		   System.out.println("Connection is successful"+connection);
		return connection;
	}
	public List<EmpPayrollData> readData() throws EmployeePayrollException {
		String sql = "SELECT * FROM  employee_payroll;";
		List<EmpPayrollData> employeePayrollList = new ArrayList<>();
		try(Connection connection=this.getConnection();) {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			employeePayrollList = this.getEmployeePayrollData(result);
		} catch (SQLException e) {
			throw new EmployeePayrollException(EmployeePayrollException.ExceptionType.CONNECTION_ERROR, e.getMessage());
		}
		return employeePayrollList;
	}
	
	public List<EmpPayrollData> getEmployeePayrollData(String name) {
		List<EmpPayrollData> employeePayrollList = null;
		if (this.employeePayrollDataStatement == null)
			this.prepareStatementForEmployeeData();
		try {
			employeePayrollDataStatement.setString(1, name);
			ResultSet resultSet = employeePayrollDataStatement.executeQuery();
			employeePayrollList = this.getEmployeePayrollData(resultSet);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollList;
	}
	
	private List<EmpPayrollData> getEmployeePayrollData(ResultSet result) {
		List<EmpPayrollData> employeePayrollList = new ArrayList<>();
		try {

			while (result.next()) {
				int id = result.getInt("id");
				String name = result.getString("name");
				double salary = result.getDouble("basic_pay");
				LocalDate startDate = result.getDate("start").toLocalDate();
				String gender = result.getString("gender");
				employeePayrollList.add(new EmpPayrollData(id, name, salary, startDate,gender));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollList;
	}
	private void prepareStatementForEmployeeData() {
		try {
			Connection connection = this.getConnection();
			String sql = "SELECT * FROM employee_payroll WHERE name = ?";
			employeePayrollDataStatement = connection.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public int updateEmployeeData(String name, double salary) {
		return this.updateEmployeeDataUsingStatement(name, salary);
	}

	private int updateEmployeeDataUsingStatement(String name, double salary) {
		String sql = String.format("update employee_payroll set basic_pay = %.2f where name = '%s';", salary, name);
		try (Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			return statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<EmpPayrollData> getEmployeePayrollDataForDateRange(LocalDate startDate, LocalDate endDate) throws EmployeePayrollException {
		String sql = String.format("Select * FROM employee_payroll WHERE start BETWEEN '%s' AND '%s';",
				Date.valueOf(startDate), Date.valueOf(endDate));
		List<EmpPayrollData> employeePayrollList = new ArrayList<>();
		try(Connection connection=this.getConnection();) {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			employeePayrollList = this.getEmployeePayrollData(result);
		} catch (SQLException e) {
			throw new EmployeePayrollException(EmployeePayrollException.ExceptionType.CONNECTION_ERROR, e.getMessage());
		}
		return employeePayrollList;
	}

	public double getSumByGender(String gender) throws EmployeePayrollException {
		List<EmpPayrollData> employeePayrollList = this.readData();
		double sum = 0.0;
		List<EmpPayrollData> sortByGenderList = employeePayrollList.stream()
				.filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList());
		sum = sortByGenderList.stream().map(employee -> employee.getSalary()).reduce(0.0, Double::sum);
		return sum;
	}

	public double getEmpDataGroupedByGender(String column, String operation, String gender) {
		Map<String, Double> sumByGenderMap = new HashMap<>();
		String sql = String.format("SELECT gender, %s(%s) FROM employee_payroll GROUP BY gender;", operation, column);
		try (Connection connection = getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				sumByGenderMap.put(resultSet.getString(1), resultSet.getDouble(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (gender.equals("M")) {
			return sumByGenderMap.get("M");
		}
		return sumByGenderMap.get("F");
	}

	public EmpPayrollData addEmployeeToPayrollUC7(String name, double salary, LocalDate startDate, String gender) {
		int empID=-1;
		EmpPayrollData employeePayrollData = null;
		String sql = String.format(
				"INSERT INTO employee_payroll(name, basic_pay, start, gender) VALUES('%s', '%s', '%s', '%s');", name, salary,
				Date.valueOf(startDate), gender);
		try (Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			int rowAffected = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
			if (rowAffected == 1) {
				ResultSet resultSet = statement.getGeneratedKeys();
				if (resultSet.next())
					empID = resultSet.getInt(1);
			}
			employeePayrollData = new EmpPayrollData(empID, name, salary, startDate, gender);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employeePayrollData;
	}
	public EmpPayrollData addEmployeeToPayroll(String name, double salary, LocalDate start, String gender) {
		int empId = -1;
		Connection connection = null;
		EmpPayrollData employeePayrollData = null;
		try {
			connection = this.getConnection();
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try (Statement statement = connection.createStatement()) {
			String sql = String.format(
					"INSERT INTO employee_payroll(name, basic_pay, start, gender) VALUES('%s', '%s', '%s', '%s');", name,
					salary, Date.valueOf(start), gender);
			int rowAffected = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
			if (rowAffected == 1) {
				ResultSet resultSet = statement.getGeneratedKeys();
				if (resultSet.next())
					empId = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		}
		try (Statement statement = connection.createStatement()) {
			double deductions = salary * 0.2;
			double taxablePay = salary - deductions;
			double tax = taxablePay * 0.1;
			double netPay = salary - tax;
			String sql = String.format("INSERT INTO payroll_details "
					+ "(employee_id, basic_pay, deductions, taxable_pay, tax, net_pay) VALUES ('%s', '%s', '%s', '%s', '%s', '%s')",
					empId, salary, deductions, taxablePay, tax, netPay);
			int rowAffected = statement.executeUpdate(sql);
			if (rowAffected == 1)
				employeePayrollData = new EmpPayrollData(empId, name, salary, start, gender);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
				return employeePayrollData;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		try {
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return employeePayrollData;
	}
	
}
	
	
	
  

