package com.telco.osb.cache.clean.db;

import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 * Session Bean implementation class Cache
 */
@Stateless(mappedName = "OCache")
public class OCache implements OCacheLocal {

	@PersistenceUnit(unitName = "soaf-osb-db")
	private EntityManagerFactory emf;

	public OCache() {
		
	}

	@Override
	public void clearCache() {
		emf.getCache().evictAll();
	}

}