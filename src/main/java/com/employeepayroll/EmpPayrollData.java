package com.employeepayroll;

public class EmpPayrollData {
    int id;
    String name;
    double salary;
    
    EmpPayrollData(int id,String name,double salary){
    	this.id=id;
    	this.name=name;
    	this.salary=salary;
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
    
    public void setId(int id) {
    	this.id=id;
    }
    public void setName(String name) {
    	this.name=name;
    }
    public void setSalary(double salary) {
    	this.salary=salary;
    }
    @Override
    public String toString() {
 	   return "id="+id+", name="+name+",.salary="+salary;
    }
}
