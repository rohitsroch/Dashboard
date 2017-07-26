package com.rohitdeveloper.dashboard.mysql;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.processing.Filer;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.rohitdeveloper.dashboard.entity.Account;


public class Mysql {
	
	public static PersistenceManagerFactory getInstance() {
	    Properties p=new Properties();		
		p.setProperty("javax.jdo.PersistenceManagerFactoryClass", "org.datanucleus.api.jdo.JDOPersistenceManagerFactory");
		p.setProperty("javax.jdo.option.ConnectionURL", "jdbc:mysql://localhost:3306/employeedb");
		p.setProperty("javax.jdo.option.ConnectionDriverName", "com.mysql.jdbc.Driver");
		p.setProperty("javax.jdo.option.ConnectionUserName", "root");
		p.setProperty("javax.jdo.option.ConnectionPassword", "root");
		p.setProperty("datanucleus.autoCreateSchema", "true");
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory(p);
		return pmf;
    }
	
	/**
	 * returns an account by id from the database
	 * @param id
	 * @return
	 */
	public static Account selectByIdQuery(String email) {
		PersistenceManagerFactory pmf = getInstance();
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		Account account = new Account();
		try {
			tx.begin();
			Query query = pm.newQuery(Account.class);
			query.setFilter("email == :theEmail");
			Map<String, String> paramValues = new HashMap();
			paramValues.put("theEmail", email);
			Collection result = (Collection) query.executeWithMap(paramValues);
			if (result.size() != 0) {
				account = (Account) result.iterator().next();
				tx.commit();
			}
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		return account;
	}
  
	/**
	 *  insert account data into the database
	 * 
	 * @param name
	 * @return
	 */
	public static boolean insertQuery(String username, String useremail , String userhash) {
		PersistenceManagerFactory pmf = getInstance();
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean isInserted = false;
		try {
			tx.begin();
			Account account = new Account(); // insert object data
			account.setName(username);
			account.setEmail(useremail);
			account.setHash(userhash);
			pm.makePersistent(account); // insert into table
			tx.commit();
			isInserted = true;
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		return isInserted;
	}
    
    
	/**
	 * delete account data from the database
	 * @param id
	 * @return
	 */
	public static boolean deleteQuery(String email) {
		PersistenceManagerFactory pmf = getInstance();
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		boolean isDeleted = false;
		try {
			tx.begin();
			Query query = pm.newQuery(Account.class);
			query.setFilter("email == :theEmail");
			Map<String, String> paramValues = new HashMap();
			paramValues.put("theEmail", email);
			Collection result = (Collection) query.executeWithMap(paramValues);
			if (result.size() != 0) {
				Account toBeDeleted = (Account) result.iterator().next();
				pm.deletePersistent(toBeDeleted); // delete from table
				tx.commit();
				isDeleted = true;
			}
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

		return isDeleted;
	}
     
    
    public static String getHashValue(String password) throws NoSuchAlgorithmException {
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
