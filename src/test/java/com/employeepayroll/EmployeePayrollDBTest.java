package com.employeepayroll;

import java.time.LocalDate;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.empdb.EmployeePayrollException;
import com.employeepayroll.EmployeePayrollService.IOService;

public class EmployeePayrollDBTest {
	@Test
	public void givenEmployeePayrollDB_shouldReturnCount() throws EmployeePayrollException {
		EmployeePayrollService empPayRollService = new EmployeePayrollService();
		List<EmpPayrollData> empPayrollList = empPayRollService.readEmpPayrollData(IOService.DB_IO);
		Assert.assertEquals(4, empPayrollList.size());
	}
	
	@Test
	public void givenNewSalayForEmployee_WhenUpdated_shouldSyncWithDB() throws EmployeePayrollException{
		EmployeePayrollService empPayRollService = new EmployeePayrollService();
		List<EmpPayrollData> empPayrollList = empPayRollService.readEmpPayrollData(IOService.DB_IO);
		 empPayRollService.updateEmployeeSalary("Deepak",3000000.0);
		 boolean result = empPayRollService.checkEmployeePayrollInSyncWithDB("Deepak");
		 Assert.assertTrue(result);
	}
	
	@Test
	public void givenDateRangeWhenRetrieved_ShouldReturnEmpCount() throws EmployeePayrollException {
		EmployeePayrollService empPayRollService = new EmployeePayrollService();
		LocalDate startDate = LocalDate.of(2018, 01, 01);
		LocalDate endDate = LocalDate.now();
		List<EmpPayrollData> empPayrollList = empPayRollService.getEmployeePayrollDataForDateRange(startDate, endDate);
		Assert.assertEquals(4, empPayrollList.size());
	}
	
}
