package com.vodafone.sobe.bpel.management.impl;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;

import javax.ejb.Stateless;
import javax.naming.Context;

import oracle.soa.management.facade.Locator;
import oracle.soa.management.facade.LocatorFactory;

import com.vodafone.sobe.bpel.management.BpelManagerLocal;

@Stateless
public class BpelManager implements BpelManagerLocal{
	
	private Locator locator = null;
	private Properties configProperties = null;

	public Locator getLocator(){
		if(locator == null){
			locator = instantiateLocator();
		}
		
		return locator;
	}
	
	
	private Locator instantiateLocator(){
		Locator locator = null;
        
        String jndiUsername = getPropertyValue("username");
        String jndiPassword = getPropertyValue("password");
        
        String jndiProviderUrl = "t3://" + getPropertyValue("host") + ":" + getPropertyValue("port");
        String jndiFactory = "weblogic.jndi.WLInitialContextFactory";
        

        Hashtable jndi = new Hashtable();
        jndi.put(Context.PROVIDER_URL, jndiProviderUrl);
        jndi.put(Context.INITIAL_CONTEXT_FACTORY, jndiFactory);
        jndi.put(Context.SECURITY_PRINCIPAL, jndiUsername);
        jndi.put(Context.SECURITY_CREDENTIALS, jndiPassword);
        jndi.put("dedicated.connection", "true");

        try{
            System.out.println("getLocator() instantiating locator..."); 
            locator = LocatorFactory.createLocator(jndi);
            System.out.println("getLocator() instantiated locator"); 
        }
        catch (Exception e){
            System.out.println("getLocator() error"); 
            e.printStackTrace();
        }
        
        return locator;
	}
	
	
	private String getPropertyValue(String propertyKey){
		
		if(configProperties == null){
			if(!loadProperties()){
				return null;
			}
		}
		
		return configProperties.getProperty(propertyKey);
	}
	
	
	private boolean loadProperties(){
		try{
			configProperties = new Properties();			
			configProperties.load(new FileInputStream("bpel-management.properties"));
			
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	

}
