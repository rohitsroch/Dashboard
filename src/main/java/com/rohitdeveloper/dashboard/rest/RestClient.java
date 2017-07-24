package com.rohitdeveloper.dashboard.rest;

import java.util.ArrayList;

import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
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

	public static boolean postRequest(Employee employee) {
		boolean isInserted=false;
		try {
			Client client = Client.create();
			WebResource webResource = client.resource(REST_URI).path("employees");
			Gson gson = new Gson();
			String input = gson.toJson(employee);
			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON_TYPE)
					.accept(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, input);
			if (response.getStatus() == 201) {
				isInserted=true;
			} else {
				response.close();
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return isInserted;
	}

	public static boolean putRequest(Integer id,Employee employee) {
		boolean isUpdated=false;
		try {
			Client client = Client.create();
			WebResource webResource = client.resource(REST_URI).path("employees").path(id.toString());
			Gson gson = new Gson();
			String input = gson.toJson(employee);

			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON)
					.put(ClientResponse.class, input);
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

}
