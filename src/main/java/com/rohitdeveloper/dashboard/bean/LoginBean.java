package com.rohitdeveloper.dashboard.bean;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.faces.bean.ManagedBean;


import com.rohitdeveloper.dashboard.mysql.Mysql;

@ManagedBean(name="loginBean")
public class LoginBean {
        private String loginEmail;
        private String loginPassword;
        private String tableName="account";
         
        //no arg public constructor
        public LoginBean() {
        	 
        }

		public String getLoginEmail() {
			return loginEmail;
		}

		public void setLoginEmail(String loginEmail) {
			this.loginEmail = loginEmail;
		}

		public String getLoginPassword() {
			return loginPassword;
		}

		public void setLoginPassword(String loginPassword) {
			this.loginPassword = loginPassword;
		}
		
		
        public String userLogin() throws NoSuchAlgorithmException{
        	
            Mysql mysql=new Mysql(tableName);
            Boolean isLoginSucessful=false;
            try {
            	ArrayList<HashMap<String,String> > results=mysql.selectQuery(loginEmail);
            	String username=null,useremail=null,userhash=null;
				for(HashMap<String,String> map : results) {
					username=map.get("username");
					useremail=map.get("useremail");
					userhash=map.get("userhash");
				}
				System.out.println(username +" "+useremail);
				//check hash values
				String hash=mysql.getHashValue(loginPassword);
				
				if(hash.equals(userhash) || hash == userhash) {
					System.out.println("Hash Value: "+hash);
					isLoginSucessful=true;
				}
				
			  } catch (Exception e) {
				e.printStackTrace();
			}
            
            if(isLoginSucessful) {
            	return "dashboard?faces-redirect=true";
            }else {
            	return "error?faces-redirect=true";
            }
	
       }
        
            
}
