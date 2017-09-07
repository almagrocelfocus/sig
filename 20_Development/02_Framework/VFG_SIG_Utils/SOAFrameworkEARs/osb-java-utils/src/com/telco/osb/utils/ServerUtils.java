package com.telco.osb.utils;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Get OSB Server or Host Name
 */
public final class ServerUtils implements Serializable {
	private static final long serialVersionUID = 4055048819845029320L;
	private static final String UNKNOWN = "UNKNOWN";

	private static String _engineName;
	private static String _serverName;

	private ServerUtils() {
	}
	
	/**
	 * Return the server name
	 * 
	 * @return
	 */
	public static final String serverName() {
		if (_serverName == null || _serverName.isEmpty()) {
			try {
				_serverName = System.getProperty("weblogic.Name");
			} catch (Exception e) {
				e.printStackTrace();
				_serverName = UNKNOWN;
			}
		}
		return _serverName;
	}

	/**
	 * Return the server ip name
	 * 
	 * @return
	 */
	public static String serverHostName() {
		if (_engineName == null || _engineName.isEmpty()) {
			try {
				_engineName = InetAddress.getLocalHost().getHostName();
			} catch (UnknownHostException e) {
				e.printStackTrace();
				_engineName = UNKNOWN;
			}
		}
		return _engineName;
	}
}
