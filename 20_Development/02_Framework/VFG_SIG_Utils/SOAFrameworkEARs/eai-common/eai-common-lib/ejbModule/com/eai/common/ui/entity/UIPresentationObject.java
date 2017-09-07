package com.eai.common.ui.entity;

import java.io.Serializable;

import com.eai.common.utils.StringUtils;

public abstract class UIPresentationObject implements Serializable {
	private static final long serialVersionUID = -918845240470666599L;
	
	public UIPresentationObject(){
	}
	
	private Boolean isSelected = false;
	public Boolean getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String toString(){
		//complex calculation used to allow ListUtils to recover value based on toString value
		return getId() == null ? "HASH="+(hashCode() > 0 ? -hashCode() : hashCode()) : getId();
	}
	@Override
	public boolean equals(Object obj){
		return StringUtils.equals(this, obj);
	}
}
