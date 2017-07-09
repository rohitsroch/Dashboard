package com.rohitdeveloper.dashboard.model;

public class Employee {
	
	  private Integer id;
      private String employeename;
      private String designation;
      private Integer salary;
      
      public Employee (Integer id,String employeename,String designation,Integer salary) {
	      this.employeename = employeename;
	      this.designation = designation;
	      this.salary = salary;
	      this.id = id;
	  }
      
      
  	 public  Employee() {
  		
  	 }


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getEmployeename() {
		return employeename;
	}


	public void setEmployeename(String employeename) {
		this.employeename = employeename;
	}


	public String getDesignation() {
		return designation;
	}


	public void setDesignation(String designation) {
		this.designation = designation;
	}


	public Integer getSalary() {
		return salary;
	}


	public void setSalary(Integer salary) {
		this.salary = salary;
	}
  	 
  	  
}
