package com.employeepayroll;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.empdb.EmployeePayrollException;
import com.empdb.EmployeePayrollFileDBService;
import com.employeepayroll.EmployeePayrollService.IOService;



public class EmployeePayrollService {
   public enum IOService{CONSOLE_IO,FILE_IO,DB_IO,REST_IO};
   private List<EmpPayrollData> employeePayrollList;
   private EmployeePayrollFileDBService employeePayrollDBService;
	
	public EmployeePayrollService() {
		employeePayrollDBService = EmployeePayrollFileDBService.getInstance();
	}
   
   public EmployeePayrollService(List<EmpPayrollData> employeePayrollList){
	   this();
	   this.employeePayrollList=employeePayrollList;
   }
   
  
   
   public static void main(String[] args) {
	ArrayList<EmpPayrollData> employeePayrollList=new ArrayList<>();
	EmployeePayrollService employeePayrollService=new EmployeePayrollService(employeePayrollList);
	Scanner sc=new Scanner(System.in);
	employeePayrollService.readEmployeeData(sc);
	employeePayrollService.writeEmployeeData(IOService.CONSOLE_IO);
}

  
private void readEmployeeData(Scanner sc) {
	System.out.println("enter the id");
	int id=sc.nextInt();
	System.out.println("enter the name");
	String name=sc.next();
	System.out.println("enter the salary");
	double salary=sc.nextDouble();
	employeePayrollList.add(new EmpPayrollData(id,name,salary));
	
}

public void writeEmployeeData(IOService ioService) {
	  if(ioService.equals(EmployeePayrollService.IOService.CONSOLE_IO))
	       System.out.println("writing the employee data"+employeePayrollList);
	  else if(ioService.equals(EmployeePayrollService.IOService.FILE_IO))
		  new EmployeePayrollFileIOService().writeData(employeePayrollList);
		  
	
}

public List<EmpPayrollData> readEmployeePayrollData(IOService ioService) {
	List<EmpPayrollData> employePayrollDataList=new ArrayList<EmpPayrollData>();
	if(ioService.equals(IOService.FILE_IO))
		employePayrollDataList=new EmployeePayrollFileIOService().readData();
	return employePayrollDataList;
}

public List<EmpPayrollData> readEmpPayrollData(IOService ioService) throws EmployeePayrollException {
	if(ioService.equals(IOService.DB_IO))
		this.employeePayrollList=employeePayrollDBService.readData();
	return employeePayrollList;
}

public boolean checkEmployeePayrollInSyncWithDB(String name) {
	List<EmpPayrollData> employeePayrollDataList = employeePayrollDBService.getEmployeePayrollData(name);
	return employeePayrollDataList.get(0).equals(getEmployeePayrollData(name));
}


public void updateEmployeeSalary(String name, double salary) {
	int result = employeePayrollDBService.updateEmployeeData(name, salary);
	if (result == 0)
		return;
	EmpPayrollData employeePayrollData = this.getEmployeePayrollData(name);
	if (employeePayrollData != null)
		employeePayrollData.salary = salary;
	
}
public void addEmployeeToPayroll(String name, double salary, LocalDate startDate, String gender,List<String> deptList) {
	
		this.employeePayrollList.add(employeePayrollDBService.addEmployeeToPayroll(name,salary,startDate,gender,deptList));
	
	
}

public void remove(String name) {
	employeePayrollDBService.remove(name);
	
}

public List<EmpPayrollData> getEmployeePayrollDataForDateRange(LocalDate startDate, LocalDate endDate) throws EmployeePayrollException{
	return employeePayrollDBService.getEmployeePayrollDataForDateRange(startDate,endDate);
}
private EmpPayrollData getEmployeePayrollData(String name) {
	return this.employeePayrollList.stream()
			.filter(employeePayrollData -> employeePayrollData.getName().equals(name))
			.findFirst()
			.orElse(null);
}

public void printData(IOService ioService) {
	if(ioService.equals(IOService.FILE_IO))
		new EmployeePayrollFileIOService().printData();
	
}

public long countEntries(IOService ioService) {
	if(ioService.equals(IOService.FILE_IO))
		return new EmployeePayrollFileIOService().countEntries();
	return 0;
}

public double getSumByGender(IOService ioService, String gender) throws EmployeePayrollException {
	double sum = 0.0;
	if (ioService.equals(IOService.DB_IO))
		return employeePayrollDBService.getSumByGender(gender);
	return sum;
}

public double getEmpDataGroupedByGender(IOService ioService, String column, String operation, String gender) {
	if (ioService.equals(IOService.DB_IO))
		return employeePayrollDBService.getEmpDataGroupedByGender(column, operation, gender);
	return 0.0;
}










}
