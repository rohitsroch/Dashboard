package com.rohitdeveloper.dashboard.bean;

import java.security.NoSuchAlgorithmException;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

import com.rohitdeveloper.dashboard.mysql.Mysql;

@ManagedBean(name="signupBean")
public class SignUpBean {
     private String signUpName;
     private String signUpEmail;
     private String signUpPassword;
     private String signUpPasswordRepeat;
     private boolean isCreateAccount;
     private String tableName="account";
    
     public SignUpBean() {
    	 
     }

	public String getSignUpName() {
		return signUpName;
	}

	public void setSignUpName(String signUpName) {
		this.signUpName = signUpName;
	}

	public String getSignUpEmail() {
		return signUpEmail;
	}

	public void setSignUpEmail(String signUpEmail) {
		this.signUpEmail = signUpEmail;
	}

	public String getSignUpPassword() {
		return signUpPassword;
	}

	public void setSignUpPassword(String signUpPassword) {
		this.signUpPassword = signUpPassword;
	}

	public String getSignUpPasswordRepeat() {
		return signUpPasswordRepeat;
	}

	public void setSignUpPasswordRepeat(String signUpPasswordRepeat) {
		this.signUpPasswordRepeat = signUpPasswordRepeat;
	}
	
	 
	public boolean getIsCreateAccount() {
		return isCreateAccount;
	}

	public void setIsCreateAccount(boolean isCreateAccount) {
		this.isCreateAccount = isCreateAccount;
	}

	public void userSignUp() throws NoSuchAlgorithmException{
    	
    	 if(signUpPassword != signUpPasswordRepeat) {
    		 FacesContext context = FacesContext.getCurrentInstance(); 
    	     context.addMessage(null, new FacesMessage("Error: Password and Repeat-Password not equal !",""));
    		 return;
    	 }
    	
    	 Mysql mysql=new Mysql(tableName);
    	 Boolean isSignUpSucessful=false;
            try {
            	    String signUpHash=mysql.getHashValue(signUpPassword);
         	        Boolean status=mysql.insertQueryForSignUp(signUpName,signUpEmail,signUpHash);
         	        if(status) {
         	        	isSignUpSucessful=true;
         	        }
			   } catch (Exception e) {
				e.printStackTrace();
		     }
         
         if(isSignUpSucessful) {
        	 FacesContext context = FacesContext.getCurrentInstance(); 
    	     context.addMessage(null, new FacesMessage("Successful: Acccount created ! "+signUpEmail,""));
         }else {
        	 FacesContext context = FacesContext.getCurrentInstance(); 
    	     context.addMessage(null, new FacesMessage("Error: creating account !",""));
         }	
         
         return ;
    }
    
           
}
