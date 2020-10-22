package com.employeepayroll;


import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.employeepayroll.EmpPayrollData;
import com.employeepayroll.EmployeePayrollService;
import com.employeepayroll.EmployeePayrollService.IOService;


public class EmployeeRollServiceTest {
	@Test
	public void given3EmployeesWhenWrittenToFileShouldMatchNumberOfEmployeeEntries() {
		EmpPayrollData[] arrayOfEmployees = { new EmpPayrollData(1, "KK", 1000.0),
												   new EmpPayrollData(2, "MK", 2000.0),
												   new EmpPayrollData(3, "DK", 3000.0) };
		EmployeePayrollService empPayrollService;
		empPayrollService = new EmployeePayrollService(Arrays.asList(arrayOfEmployees));
		empPayrollService.writeEmployeeData(IOService.FILE_IO);
		empPayrollService.printData(IOService.FILE_IO);
		long entries=empPayrollService.countEntries(IOService.FILE_IO);
		Assert.assertEquals(3,entries);
}
	
	@Test
	public void givenFileOnReadingFromFilesShouldMatchEmployeeCount() {
		EmployeePayrollService employeePayrollService=new EmployeePayrollService();
		List<EmpPayrollData> employeePayrollDataList=employeePayrollService.readEmployeePayrollData(IOService.FILE_IO);
		System.out.println( employeePayrollDataList);
		
		assertEquals(3,employeePayrollDataList.size());
	}
}
