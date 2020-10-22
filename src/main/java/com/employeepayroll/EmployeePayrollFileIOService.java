package com.employeepayroll;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.employeepayroll.EmployeePayrollService.IOService;



public class EmployeePayrollFileIOService {
public static String PAYROLL_FILE_NAME="payroll-file.text";
	
	public void writeData(List<EmpPayrollData> employeePayrollList) {
		StringBuffer empBuffer=new StringBuffer();
		employeePayrollList.forEach(employee->{
			String employeeDataString=employee.toString().concat("\n");
			empBuffer.append(employeeDataString);
		});
		try {
			Files.write(Paths.get(PAYROLL_FILE_NAME), empBuffer.toString().getBytes());
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public List<EmpPayrollData> readData() {
		List<EmpPayrollData> employeePayrollList = new ArrayList<>();
		try {
			Files.lines(new File(PAYROLL_FILE_NAME).toPath()).map(line -> line.trim()).forEach(line -> {
				String[] words = line.split("[\\s,=]+");
				int id = Integer.parseInt(words[1]);
				String name = words[3];
				double salary = Double.parseDouble(words[5]);
				EmpPayrollData e = new EmpPayrollData(id, name, salary);
				employeePayrollList.add(e);
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return employeePayrollList;
	}

	public void printData() {
		try {
			  Files.lines(new File(PAYROLL_FILE_NAME).toPath())
			    .forEach(System.out::println);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}

	public long countEntries() {
		long entries=0;
		try {
			 entries=Files.lines(new File(PAYROLL_FILE_NAME).toPath())
			    .count();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return entries;
	}
	
	
	
}
