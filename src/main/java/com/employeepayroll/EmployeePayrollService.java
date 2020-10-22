package com.employeepayroll;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.employeepayroll.EmployeePayrollService.IOService;

public class EmployeePayrollService {
   public enum IOService{CONSOLE_IO,FILE_IO,DB_IO,REST_IO};
   private List<EmpPayrollData> employeePayrollList;
   
   
   EmployeePayrollService(){
	   
   }
   
   public EmployeePayrollService(List<EmpPayrollData> employeePayrollList){
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


public void printData(IOService ioService) {
	if(ioService.equals(IOService.FILE_IO))
		new EmployeePayrollFileIOService().printData();
	
}

public long countEntries(IOService ioService) {
	if(ioService.equals(IOService.FILE_IO))
		return new EmployeePayrollFileIOService().countEntries();
	return 0;
}
}
