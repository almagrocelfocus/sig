package com.eai.common.db.entity;

import javax.persistence.*;


/**
 * The persistent class for the EAI_PROPERTY database table - ensure that persistence.xml contains the included property
 * 
 */
@Entity
@Table(name=ConfigurationProperty.TABLE_NAME)
public class ConfigurationProperty implements ConfigurationPropertyLocal {
	private static final long serialVersionUID = 6634296031731446553L;
	public static final String TABLE_NAME = "EAI_PROPERTY";
	
	private String name;
	private String value;
	private String description;
    public ConfigurationProperty() {
    }
    
	@Id
	@Column(name="NAME")
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Column(name="VALUE")
	public String getValue() {
		return this.value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	@Column(name="DESCRIPTION")
	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	@Transient
	public String getTableName() {
		return "EAI_PROPERTY";
	}
}