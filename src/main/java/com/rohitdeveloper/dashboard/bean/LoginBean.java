package com.rohitdeveloper.dashboard.bean;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import com.rohitdeveloper.dashboard.mysql.Mysql;
import com.rohitdeveloper.dashboard.utils.SessionUtils;

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
            	HashMap<String,String> result=mysql.selectQueryForLogin(loginEmail);
            	//String username=result.get("username");
            	//String useremail=result.get("useremail");
            	String userhash=result.get("userhash");
				
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
            	HttpSession session = SessionUtils.getSession();
    			session.setAttribute("username", loginEmail);
            	return "dashboard?faces-redirect=true";
            }else {
            	 RequestContext.getCurrentInstance().update("growl");
            	 FacesContext context = FacesContext.getCurrentInstance(); 
        	     context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Incorrect Email or Password !",""));
        	     return null;
            }
	
       }
        
        
       //logout event, invalidate session
    	public String userLogout() {
    		System.out.println("Logout");
    		HttpSession session = SessionUtils.getSession();
    		session.invalidate();
    		return "account?faces-redirect=true";
    	}
                  
}
