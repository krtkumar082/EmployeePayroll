package com.employeepayroll;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.empdb.EmployeePayrollException;


public class EmployeePayrollFileDBService {

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
		try {
			Connection connection=this.getConnection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) {
				int id = result.getInt("id");
				String name = result.getString("name");
				double salary = result.getDouble("net_pay");
				LocalDate startDate = result.getDate("start").toLocalDate();
				employeePayrollList.add(new EmpPayrollData(id, name, salary, startDate));
			}
		} catch (SQLException e) {
			throw new EmployeePayrollException(EmployeePayrollException.ExceptionType.CONNECTION_ERROR, e.getMessage());
		}
		return employeePayrollList;
	}

	
  
}
