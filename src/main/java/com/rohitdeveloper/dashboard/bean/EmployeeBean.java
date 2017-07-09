package com.rohitdeveloper.dashboard.bean;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.rohitdeveloper.dashboard.model.Employee;
import com.rohitdeveloper.dashboard.mysql.Mysql;


@ManagedBean(name="employeeBean")
@SessionScoped
public class EmployeeBean {  
	private String tableName="details";
	//For insertion in database
    private String employeename;
    private String designation;
    private Double  salary;
    
    //All Employees list
    private ArrayList<Employee> employees=getEmployeeList();
    
    
    public EmployeeBean() {
    	
    }
    
    public ArrayList<Employee> getEmployees() {
		return employees;
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
	public Double  getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}	
	
    
	public ArrayList<Employee> getEmployeeList(){ 
		Mysql mysql=new Mysql(tableName);
		ArrayList<Employee> emp=new ArrayList<Employee>();
		try {
			  emp=mysql.selectQueryForEmployee();  
		}catch(Exception e) {
			e.printStackTrace();
		}
		return emp;
	} 
	
	
	public String insertEmployee() {
		Mysql mysql=new Mysql(tableName);
		try {
			Boolean status=mysql.insertQueryForEmployee(employeename,designation,salary.intValue());
			if(status) {
				employees=getEmployeeList();
				clear(); 
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "dashboard?faces-redirect=true";
	}
	
	public void deleteEmployee(Employee emp){
	    Integer id=emp.getId();
		Mysql mysql=new Mysql(tableName);
		try {
			Boolean status=mysql.deleteQuery(id);
			if(status) {
				employees.remove(emp);
				clear(); 
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void clear() {
		setEmployeename(null);
		setDesignation(null);
		setSalary(null);
	}
}
