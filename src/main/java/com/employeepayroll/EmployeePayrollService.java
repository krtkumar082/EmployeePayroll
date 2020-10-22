package com.employeepayroll;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeePayrollService {
   private List<EmpPayrollData> employeePayrollList;
   
   
   EmployeePayrollService(){
	   
   }
   
   EmployeePayrollService(List<EmpPayrollData> employeePayrollList){
	   this.employeePayrollList=employeePayrollList;
   }
   
  
   
   public static void main(String[] args) {
	ArrayList<EmpPayrollData> employeePayrollList=new ArrayList<>();
	EmployeePayrollService employeePayrollService=new EmployeePayrollService(employeePayrollList);
	Scanner sc=new Scanner(System.in);
	employeePayrollService.readEmployeeData(sc);
	employeePayrollService.writeEmployeeData();
}

  private void writeEmployeeData() {
	System.out.println("writing the employee data"+employeePayrollList);
	
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
   
}
