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
    public ArrayList<HashMap<String, String> > selectQuery(String id) throws SQLException{
    	Connection myConn=null;
    	PreparedStatement pstmt=null;
    	ResultSet myRs=null;
    	ArrayList< HashMap<String, String> > results =new ArrayList< HashMap<String, String> >();    
		try {
			// 1. Get a connection to database
			myConn=getConnection();	
			// 2. Create a statement
			if(id==null) {
				String sql="SELECT * FROM "+tableName;		
				pstmt=myConn.prepareStatement(sql);
				// 3. Execute SQL query
				myRs=pstmt.executeQuery();
				while (myRs.next()) {
					HashMap<String, String> map=new HashMap<String, String>();
					map.put("userid",String.valueOf(myRs.getString("id") ));
					map.put("username",myRs.getString("EmployeeName"));
					map.put("userdesignation",myRs.getString("Designation"));
					map.put("usersalary",myRs.getString("Salary"));
					//store all data into a List
					results.add(map);
				}
				
			}else {
				String sql="SELECT * FROM "+tableName+" WHERE Email=?";		
				pstmt=myConn.prepareStatement(sql);
				pstmt.setString(1,id);
				// 3. Execute SQL query
				myRs=pstmt.executeQuery();
				while (myRs.next()) {
					HashMap<String, String> map=new HashMap<String, String>();
					map.put("username",myRs.getString("Name"));
					map.put("useremail",myRs.getString("Email"));
					map.put("userhash",myRs.getString("Hash"));
					//store all data into a List
					results.add(map);
				}
			}
		   
			System.out.println("SELET QUERY: SUCCESS");
		}catch (SQLException exc) {
			System.out.println("Error: "+exc.getMessage());
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
    
    
    //INSERT INTO QUERY
    public String insertQuery(String employeename, String designation, Integer salary) throws SQLException{
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
 			System.out.println("Error: "+exc.getMessage());
 			
 		}finally {
 			if (pstmt != null) {
 				pstmt.close();
 			}
 			
 			if (myConn != null) {
 				myConn.close();
 			}
 		}
 	   
 		return null;
 	}
    
	//DELETE QUERY
    public String deleteQuery(Integer id) throws SQLException{
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
			System.out.println(exc.getMessage());
		}finally {
			if (pstmt != null) {
				pstmt.close();
			}
			
			if (myConn != null) {
				myConn.close();
			}
		}
		
		return null;
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
