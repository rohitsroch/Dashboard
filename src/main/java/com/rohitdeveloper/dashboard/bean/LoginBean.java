package com.rohitdeveloper.dashboard.bean;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import com.rohitdeveloper.dashboard.entity.Account;
import com.rohitdeveloper.dashboard.mysql.Mysql;
import com.rohitdeveloper.dashboard.utils.SessionUtils;

@ManagedBean(name="loginBean")
@SessionScoped
public class LoginBean {
        private String loginEmail;
        private String loginPassword;
         
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
        	
            boolean isLoginSucessful=false;
            String username=null,useremail=null,userhash=null;
            
            try {
            	 Account result=Mysql.selectByIdQuery(loginEmail);
            	 username=result.getName();
            	 useremail=result.getEmail();
            	 userhash=result.getHash();
				//check hash values
            	 System.out.println(userhash);
				String hash=Mysql.getHashValue(loginPassword);
				
				if(hash.equals(userhash)) {
					System.out.println("Hash Value: "+hash);
					isLoginSucessful=true;
				}
				
			  } catch (Exception e) {
				e.printStackTrace();
			}
            
            if(isLoginSucessful) {
            	HttpSession session = SessionUtils.getSession();
    			session.setAttribute("username", username);
    			session.setAttribute("useremail",useremail);
            	return "dashboard?faces-redirect=true";
            }else {
            	 RequestContext.getCurrentInstance().update("growl");
            	 FacesContext context = FacesContext.getCurrentInstance(); 
        	     context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Incorrect Email or Password !",""));
        	     return null;
            }
	
       }
        
                   
}
