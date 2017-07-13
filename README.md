## Dashboard
JSF web framework based Employee dashboard application which allows the user to save employees details in mysql database by authenticating user (using SHA256 hash).And updating the dataTable on saving employees details providing search capability using solr search.

### Workflow of application
![alt text](https://github.com/rdeveloperIITR/Dashboard/blob/master/Screenshots/1.png)
![alt text](https://github.com/rdeveloperIITR/Dashboard/blob/master/Screenshots/2.png)
![alt text](https://github.com/rdeveloperIITR/Dashboard/blob/master/Screenshots/3.png)

### Use link
localhost:8080/EmployeeDashboard/faces/account.xhtml

### Update src/main/java/com/rohitdeveloper/dashboard/mysql/Mysql.java
```
public Connection getConnection() {
	      Connection con = null;
	      String url = "jdbc:mysql://localhost:3306/employeedb?useSSL=false";
	      String user = "root";
	      String password = "qwerty";
}
```
Replace "user" and "password"
