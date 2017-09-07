package com.eai.common.ws.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.eai.common.utils.ListUtils;
import com.eai.common.utils.StringUtils;

public class Provider implements Serializable {
	private static final long serialVersionUID = 2124263483547080764L;
	private String name;
	private String description;
	private String url;
	
	public Provider(){
	}
	public Provider(String name, String description, String url){
		setName(name);
		setDescription(description);
		setUrl(url);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString(){
		return getName();
	}
	
	@Override
	public boolean equals(Object obj){
		return StringUtils.equals(this, obj);
	}
	
	public static class ProviderContainer {
		private List<Provider> providerLst;
		private Provider currentProvider;
		
		public List<Provider> getProviderLst() {
			if(providerLst == null) providerLst = new ArrayList<Provider>();
			return providerLst;
		}
		public void setProviderLst(List<Provider> providerLst) {
			this.providerLst = providerLst;
		}
		
		public Provider getCurrentProvider() {
			return currentProvider;
		}
		public void setCurrentProvider(Provider currentProvider) {
			this.currentProvider = currentProvider;
		}
		public void setCurrentProvider(String name) {
			Provider provider = ListUtils.getObject(getProviderLst(), name);
			setCurrentProvider(provider);
		}
	}
}
