package com.eai.common.entities;

import java.io.Serializable;

import com.eai.common.utils.StringUtils;

public class Property<T,R> implements Serializable{
	private static final long serialVersionUID = 2326043383478030888L;
	
	public Property() {
	}
	
	public Property(T name, R value) {
		setName(name);
		setValue(value);
	}
	
	private T name;
	public T getName() {
		return name;
	}
	public void setName(T name) {
		this.name = name;
	}
	
	private R value;
	public R getValue() {
		return value;
	}
	public void setValue(R value) {
		this.value = value;
	}
	
	@Override
	public String toString(){
		return "name="+ (name == null ? "" :  name)+ "value="+ (value == null ? "" :  value);
	}
	@Override
	public boolean equals(Object obj){
		return StringUtils.equals(this, obj);
	}
}
