package com.eai.common.db.entity;

import java.io.Serializable;

import javax.ejb.Local;

@Local
public interface ConfigurationPropertyLocal extends Serializable{
	public String getName();
	public String getValue();
	public String getDescription();
	public String getTableName();
}
