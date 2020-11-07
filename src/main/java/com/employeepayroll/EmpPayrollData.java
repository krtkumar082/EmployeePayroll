package com.employeepayroll;

import java.time.LocalDate;
import java.util.List;

public class EmpPayrollData {
    int id;
    String name;
    double salary;
    String gender;
    public LocalDate startDate;
    public List<String> deptList;
    
    public EmpPayrollData(int id,String name,double salary){
    	this.id=id;
    	this.name=name;
    	this.salary=salary;
    }
    
    public EmpPayrollData(int id,String name,double salary,LocalDate startDate){
    	this.id=id;
    	this.name=name;
    	this.salary=salary;
    	this.startDate=startDate;
    }
    public EmpPayrollData(int id, String name, double salary, LocalDate startDate, String gender) {
		this(id,name,salary,startDate);
		this.gender = gender;
	}
    
    public EmpPayrollData(Integer id, String name, Double salary, LocalDate startDate, String gender,
			List<String> deptList) {
		this(id, name, salary, startDate, gender);
		this.setDeptList(deptList);
	}

	public List<String> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<String> deptList) {
		this.deptList = deptList;
	}
    public int getId() {
    	return this.id;
    }
    public String getName() {
    	return this.name;
    }
    public double getSalary() {
    	return this.salary;
    }
    public String getGender() {
    	return this.gender;
    }
    public void setId(int id) {
    	this.id=id;
    }
    public void setName(String name) {
    	this.name=name;
    }
    public void setSalary(double salary) {
    	this.salary=salary;
    }
    public void setGender(String gender) {
    	this.gender=gender;
    }
    @Override
    public String toString() {
 	   return "id="+id+", name="+name+",.salary="+salary;
    }
    
    @Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		EmpPayrollData that = (EmpPayrollData) o;
		return id == that.id && Double.compare(that.salary, salary) == 0 && name.equals(that.name);
	}
    
}
