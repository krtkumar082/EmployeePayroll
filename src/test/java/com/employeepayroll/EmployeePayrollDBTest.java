package com.employeepayroll;

import java.time.LocalDate;
import java.util.ArrayList;
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
		Assert.assertEquals(9, empPayrollList.size());
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
		Assert.assertEquals(9, empPayrollList.size());
	}
	
	@Test
	public void givenDBFindSumOfSalaryOfFemale_shouldReturnSum() throws EmployeePayrollException {
		EmployeePayrollService empPayRollService = new EmployeePayrollService();
		double sum = empPayRollService.getSumByGender(IOService.DB_IO,"F");
		double sum1 = empPayRollService.getEmpDataGroupedByGender(IOService.DB_IO, "basic_pay", "SUM","F");
	
		Assert.assertTrue(sum == sum1);
	}
	
	@Test
	public void givenNewEmployee_WhenAdded_shouldSyncWithDatabase() throws EmployeePayrollException {
		List<String> deptList = new ArrayList<>();
		deptList.add("Sales");
		deptList.add("HR");
		EmployeePayrollService empPayRollService = new EmployeePayrollService();
		List<EmpPayrollData> empPayrollList = empPayRollService.readEmpPayrollData(IOService.DB_IO);
		empPayRollService.addEmployeeToPayroll("Mark",5000000.00,LocalDate.now(),"M",deptList);
		boolean result = empPayRollService.checkEmployeePayrollInSyncWithDB("Mark");
		Assert.assertTrue(result);
	}
	@Test
	public void givenEmployeeList_WhenDeleted_ShouldReturnProperCount() throws EmployeePayrollException {
		EmployeePayrollService empPayRollService = new EmployeePayrollService();
		empPayRollService.readEmpPayrollData(IOService.DB_IO);
		empPayRollService.remove("Mark");
		List<EmpPayrollData> empPayrollList = empPayRollService.readEmpPayrollData(IOService.DB_IO);
		Assert.assertEquals(3, empPayrollList.size());
	}
}
