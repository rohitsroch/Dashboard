package com.rohitdeveloper.dashboard.mysql;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.rohitdeveloper.dashboard.model.Employee;


public class Mysql {
	
	private String tableName;
	

	public Mysql(String tableName) {
		this.tableName=tableName;
	}
	
	public Connection getConnection() {
	      Connection con = null;
	      String url = "jdbc:mysql://localhost:3306/employeedb";
	      String user = "root";
	      String password = "qwerty";
	      
	      try {
	         con = DriverManager.getConnection(url, user, password);
	         System.out.println("Connection completed.");
	      } catch (SQLException ex) {
	         System.out.println(ex.getMessage());
	      }
	      return con;
	}
	
   //SELECT ALL QUERY WITHOUT ID
    public ArrayList<Employee> selectQueryForEmployee() throws SQLException{
    	Connection myConn=null;
    	PreparedStatement pstmt=null;
    	ResultSet myRs=null;
    	ArrayList<Employee> results =new ArrayList<Employee>();    
		try {
			// 1. Get a connection to database
			myConn=getConnection();	
			// 2. Create a statement
			String sql="SELECT * FROM "+tableName;		
			pstmt=myConn.prepareStatement(sql);
			// 3. Execute SQL query
			myRs=pstmt.executeQuery();
			while (myRs.next()) {
				    Employee curremp=new Employee();
				    curremp.setId(myRs.getInt("id"));
					curremp.setEmployeename(myRs.getString("EmployeeName"));
					curremp.setDesignation(myRs.getString("Designation"));
					curremp.setSalary(myRs.getInt("Salary"));
					//store all data into a List
					results.add(curremp);
			 }
				
			System.out.println("SELECT QUERY: SUCCESS");
		}catch (SQLException exc) {
			System.out.println("SQL Error: "+exc.getMessage());
		}finally {
			
			if (myRs != null) {
				myRs.close();
			}
			
			if (pstmt != null) {
				pstmt.close();
			}
			
			if (myConn != null) {
				myConn.close();
			}
		}
		
		return results;
	}
    
    
    //SELECT ALL QUERY WITH  ID
    public HashMap<String, String> selectQueryForLogin(String id) throws SQLException{
    	Connection myConn=null;
    	PreparedStatement pstmt=null;
    	ResultSet myRs=null;
    	HashMap<String, String> result =new  HashMap<String, String>();;   
		try {
			// 1. Get a connection to database
			myConn=getConnection();	
			// 2. Create a statement
			String sql="SELECT * FROM "+tableName+" WHERE Email=?";		
			pstmt=myConn.prepareStatement(sql);
			pstmt.setString(1,id);
			// 3. Execute SQL query
			myRs=pstmt.executeQuery();
			//store all data into a HashMap
			while(myRs.next()) {
				result.put("username",myRs.getString("Name"));
				result.put("useremail",myRs.getString("Email"));
				result.put("userhash",myRs.getString("Hash"));
			}
		    
			System.out.println("SELECT QUERY: SUCCESS");
		}catch (SQLException exc) {
			System.out.println("SQL Error: "+exc.getMessage());
		}finally {
			
			if (myRs != null) {
				myRs.close();
			}
			
			if (pstmt != null) {
				pstmt.close();
			}
			
			if (myConn != null) {
				myConn.close();
			}
		}
		
		return result;
	}
    
    
    //INSERT INTO QUERY FOR EMPLOYEE INSERT
    public Boolean insertQueryForEmployee(String employeename, String designation, Integer salary) throws SQLException{
    	Boolean status=true;
        Connection myConn=null;
    	PreparedStatement pstmt=null;	
 	   try {
 			// 1. Get a connection to database
 			myConn=getConnection();	
 			// 2. Create a statement	
 			String sql="INSERT INTO details (EmployeeName,Designation,Salary) "+
 					" VALUES (?,?,?)"; 
 			pstmt=myConn.prepareStatement(sql);
 			pstmt.setString(1,employeename);
 			pstmt.setString(2,designation);
 			pstmt.setInt(3,salary);
 		    // 3. Execute SQL query
 			pstmt.execute();
 			
 			System.out.println("INSERT QUERY: SUCCESS");
 			
 		}catch (SQLException exc) {
 			System.out.println("SQL Error: "+exc.getMessage());
 			
 		}finally {
 			if (pstmt != null) {
 				pstmt.close();
 			}
 			
 			if (myConn != null) {
 				myConn.close();
 			}
 		}
 	   
 		return status;
 	}
    
    
    //INSERT INTO QUERY FOR USER INSERT
    public Boolean insertQueryForSignUp(String username, String useremail , String userhash) throws SQLException{
    	Boolean status=true;
        Connection myConn=null;
    	PreparedStatement pstmt=null;	
    	
 	   try {
 			// 1. Get a connection to database
 			myConn=getConnection();	
 			// 2. Create a statement	
 			String sql="INSERT INTO account (Name,Email,Hash) "+
 					" VALUES (?,?,?)"; 
 			pstmt=myConn.prepareStatement(sql);
 			pstmt.setString(1,username);
 			pstmt.setString(2,useremail);
 			pstmt.setString(3,userhash);
 		    // 3. Execute SQL query
 			pstmt.execute();
 			
 			System.out.println("INSERT QUERY: SUCCESS");
 			
 		}catch (SQLException exc) {
 			System.out.println("SQL Error: "+exc.getMessage());
 			
 		}finally {
 			if (pstmt != null) {
 				pstmt.close();
 			}
 			
 			if (myConn != null) {
 				myConn.close();
 			}
 		}
 	   
 		return status;
 	}
    
	//DELETE QUERY
    public Boolean deleteQuery(Integer id) throws SQLException{
    	Boolean status=true;
    	Connection myConn=null;
      	PreparedStatement pstmt=null;	
		try {
			// 1. Get a connection to database
			myConn=getConnection();	
			// 2. Create a statement		
			String sql="DELETE FROM details WHERE id=?";		
			pstmt=myConn.prepareStatement(sql);
			pstmt.setInt(1,id);
			
			// 3. Execute SQL query
			pstmt.execute();
			
			System.out.println("DELETE QUERY: SUCCESS");
			
		}catch (SQLException exc) {
			System.out.println("SQL Error: "+exc.getMessage());
		}finally {
			if (pstmt != null) {
				pstmt.close();
			}
			
			if (myConn != null) {
				myConn.close();
			}
		}
		
		return status;
	}
    
    
    public String getHashValue(String password) throws NoSuchAlgorithmException {
    	//get the hash value using sha256 algorithm!
    	String secret="qwerty";
    	String input= secret+""+password;
    	
    	final MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
