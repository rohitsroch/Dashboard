package com.rohitdeveloper.dashboard.rest;

import java.util.ArrayList;

import javax.ws.rs.core.MediaType;

import com.rohitdeveloper.dashboard.entity.Employee;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

public class RestClient {
	private static final String REST_URI = "http://localhost:8080/employeeService/api";

	public static ArrayList<Employee> getRequest() {
		ArrayList<Employee> employeeList=new ArrayList<Employee>();
		try {
			Client client = Client.create();
			WebResource webResource = client.resource(REST_URI).path("employees");
			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
					.get(ClientResponse.class);
			if (response.getStatus() == 200) {
				employeeList = response.getEntity(new GenericType<ArrayList<Employee>>() {});
			} else {
				response.close();
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return employeeList;
	}

	public static Employee getRequestById(Integer id) {
		Employee employeeById=new Employee();
		try {
			Client client = Client.create();
			WebResource webResource = client.resource(REST_URI).path("employees").path(id.toString());
			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON_TYPE)
					.accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
			if (response.getStatus() == 200) {
				employeeById = response.getEntity(new GenericType<Employee>() {});
			} else {
				response.close();
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employeeById;
	}

	public static Integer postRequest(Employee employee) {
        Integer empId=null;
		try {
			Client client = Client.create();
			WebResource webResource = client.resource(REST_URI).path("employees");
			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON_TYPE)
					.accept(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, employee);
			if (response.getStatus() == 201) {
				empId=response.getEntity(Integer.class);
			} else {
				response.close();
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return empId;
	}

	public static boolean putRequest(Integer id,Employee employee) {
		boolean isUpdated=false;
		try {
			Client client = Client.create();
			WebResource webResource = client.resource(REST_URI).path("employees").path(id.toString());
			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON)
					.put(ClientResponse.class, employee);
			if (response.getStatus() == 201) {
				isUpdated=true;
			} else {
				response.close();
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return isUpdated;
	}

	public static boolean deleteRequest(Integer id) {
		boolean isDeleted=false;
		try {
			Client client = Client.create();
			WebResource webResource = client.resource(REST_URI).path("employees").path(id.toString());
			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON)
					.delete(ClientResponse.class);
			if (response.getStatus() == 201) {
				isDeleted=true;
			} else {
				response.close();
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return isDeleted;
	}

	//-----------------------------------------Solr Search Method------------------------------------------------------------------
	
	public static ArrayList<Employee> solrSearchGetRequest(String searchQuery){
		ArrayList<Employee> employeeList=new ArrayList<Employee>();
		try {
			Client client = Client.create();
			WebResource webResource = client.resource(REST_URI).path("search").queryParam("query", searchQuery);
			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
					.get(ClientResponse.class);
			if (response.getStatus() == 200) {
				employeeList = response.getEntity(new GenericType<ArrayList<Employee>>() {});	
			} else {
				response.close();
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return employeeList;
	}
	
	public static boolean solrSearchPostRequest(Employee employee) {
		boolean isDocumentadded=false;
		try {
			Client client = Client.create();
			WebResource webResource = client.resource(REST_URI).path("search");
			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON_TYPE)
					.accept(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, employee);
			if (response.getStatus() == 201) {
				isDocumentadded=true;
			} else {
				response.close();
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isDocumentadded;
	}
    
	public static boolean solrSearchDeleteRequest(Integer id) {
		boolean isDocumentdeleted = false;
		try {
			Client client = Client.create();
			WebResource webResource = client.resource(REST_URI).path("search").path("delete").queryParam("id", id.toString());
			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON_TYPE)
					.accept(MediaType.APPLICATION_JSON_TYPE).delete(ClientResponse.class);
			if (response.getStatus() == 201) {
				isDocumentdeleted=true;
			} else {
				response.close();
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isDocumentdeleted;
	}
}
