package com.eai.common.ws;

import com.eai.common.ws.entity.Provider;

public class WSProxyFactory {
	public static <T extends WSProxy> T getInstance(Class<T> classz, Provider provider){
		T t = null;
		try {
			t = classz.newInstance();
			t.setProvider(provider);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}
}
