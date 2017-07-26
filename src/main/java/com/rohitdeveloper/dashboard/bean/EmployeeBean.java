package com.rohitdeveloper.dashboard.bean;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import com.rohitdeveloper.dashboard.entity.Employee;
import com.rohitdeveloper.dashboard.rest.RestClient;
import com.rohitdeveloper.dashboard.utils.SessionUtils;



@ManagedBean(name="employeeBean")
@SessionScoped
public class EmployeeBean {  
	private String searchQuery;
	
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
    
    
	public void setEmployees(ArrayList<Employee> employees) {
		this.employees = employees;
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
	
    
	public String getSearchQuery() {
		return searchQuery;
	}

	public void setSearchQuery(String searchQuery) {
		this.searchQuery = searchQuery;
	}

	public ArrayList<Employee> getEmployeeList(){ 
		ArrayList<Employee> emp=new ArrayList<Employee>();
		try {
			  emp=RestClient.getRequest();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return emp;
	} 
	
	
	public String insertEmployee() {
		
		try {
			Employee emp = new Employee();
			emp.setEmployeename(employeename);
			emp.setDesignation(designation);
			emp.setSalary(salary.intValue());
			Integer empId=RestClient.postRequest(emp);
			emp.setId(empId);
			boolean isDocumentadded=RestClient.solrSearchPostRequest(emp);
			if(empId!=null && isDocumentadded) {
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
		try {
			boolean status=RestClient.deleteRequest(id);
			boolean isDocumentdeleted=RestClient.solrSearchDeleteRequest(id);
			if(status &&  isDocumentdeleted) {
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
	

	public void getFilterSearchResult() {
		if(searchQuery.isEmpty()) {
			 RequestContext.getCurrentInstance().update("growl");
			 FacesContext context = FacesContext.getCurrentInstance(); 
    	     context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Search: Validation Error: Value is required.",""));
			 return;
		}
		try {
		    employees=RestClient.solrSearchGetRequest(searchQuery);
			if(employees.size()== 0) {
				RequestContext.getCurrentInstance().update("growl");
	        	FacesContext context = FacesContext.getCurrentInstance(); 
	    	    context.addMessage(null, new FacesMessage("No Records Found !",""));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error: "+e.getMessage());
			e.printStackTrace();
		}
			
	}
 
	public void updateDataTable() {
		if(searchQuery.length() ==0  || searchQuery.isEmpty()) {
			employees=getEmployeeList();
		}	
	}
	
	
	//logout event, invalidate session
	public String userLogout() {
		System.out.println("Logout");
		HttpSession session = SessionUtils.getSession();
		session.invalidate();
		return "account?faces-redirect=true";
	}
	
	public String getSessionUserName() {
		String username=SessionUtils.getUserName();
		return username;
	}
	
	public String getSessionUserEmail() {
		String useremail=SessionUtils.getUserEmail();
		return useremail;
	}
}
