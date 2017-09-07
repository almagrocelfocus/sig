package com.telco.osb.cache.clean.db;

import javax.ejb.Local;

@Local
public interface OCacheLocal {
	public void clearCache();
}
