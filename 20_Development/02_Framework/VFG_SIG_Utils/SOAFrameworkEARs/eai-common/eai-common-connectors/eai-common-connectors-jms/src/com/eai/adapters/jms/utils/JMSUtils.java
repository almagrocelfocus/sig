package com.eai.adapters.jms.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.eai.adapters.jms.exception.JMSEAIException;
import com.eai.common.exception.DevelopmentException;
import com.eai.common.utils.EAILogger;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JMSUtils {
	
	private JMSUtils(){
	}
	
	public static Context getJNDIContext(){
		Context ctx;
		try {
			ctx = new InitialContext();
		} catch (NamingException e) {
			throw new DevelopmentException(e.getMessage());//FIXME: correct exception type
		}
		return ctx;
	}
	@SuppressWarnings("unchecked")
	public static <T> T getJNDIContextObject(Class<T> clazz, String objectName){
		try {
			return (T)getJNDIContext().lookup(objectName);
		} catch (NamingException e) {
			throw new DevelopmentException(e.getMessage());//FIXME: correct exception type
		}
	}
	
	public static void sendStreamMessage(String connectionFactoryName, String destinationQueueName, Object message){
		QueueConnectionFactory connectionFactory = getJNDIContextObject(QueueConnectionFactory.class, connectionFactoryName);
		Queue destinationQueue = getJNDIContextObject(Queue.class, destinationQueueName);
		sendStreamMessage(connectionFactory, destinationQueue, message);
	}
	
	@SuppressWarnings("unchecked")
	public static void sendStreamMessage(QueueConnectionFactory connectionFactory, Queue destinationQueue, Object message) {
		QueueConnection jmsConnection = null;
		QueueSession session = null;
		QueueSender sender = null;
		try {
			jmsConnection = connectionFactory.createQueueConnection();
			session = jmsConnection.createQueueSession(false, Session.CLIENT_ACKNOWLEDGE);
			StreamMessage jmsMessage = session.createStreamMessage();
			sender = session.createSender(destinationQueue);
			
			ObjectMapper m = new ObjectMapper();
			Map<String,Object> props = m.convertValue(message, Map.class);
			jmsMessage.writeBytes(serialize(props));
			sender.send(jmsMessage);
		} catch (JMSException e) {
			throw new JMSEAIException(e);
		} finally {
			forceClose(jmsConnection);
			forceClose(session);
			forceClose(sender);
		}
	}
	
	public static void sendMapMessage(String connectionFactoryName, String destinationQueueName, Map<String,?> mapMessage){
		QueueConnectionFactory connectionFactory = getJNDIContextObject(QueueConnectionFactory.class, connectionFactoryName);
		Queue destinationQueue = getJNDIContextObject(Queue.class, destinationQueueName);
		sendMapMessage(connectionFactory, destinationQueue, mapMessage);
	}
	
	public static void sendMapMessage(QueueConnectionFactory connectionFactory, Queue destinationQueue, Map<String,?> mapMessage) {	
		sendMapMessage(connectionFactory, destinationQueue, mapMessage, Session.CLIENT_ACKNOWLEDGE);
	}
	
	public static void sendMapMessage(QueueConnectionFactory connectionFactory, Queue destinationQueue, Map<String,?> mapMessage, int jmsSession) {
		QueueConnection jmsConnection = null;
		QueueSession session = null;
		QueueSender sender = null;
		try {
			jmsConnection = connectionFactory.createQueueConnection();
			session = jmsConnection.createQueueSession(false, jmsSession);
			MapMessage jmsMessage = session.createMapMessage();
			sender = session.createSender(destinationQueue);
			if(mapMessage != null){
				for(Map.Entry<String, ?> entry : mapMessage.entrySet()){
					jmsMessage.setObject(entry.getKey(), entry.getValue());
				}
			}
			sender.send(jmsMessage);
		} catch (JMSException e) {
			throw new JMSEAIException(e);
		} finally {
			forceClose(jmsConnection);
			forceClose(session);
			forceClose(sender);
		}
	}
	
	public static void sendObjectMessage(String connectionFactoryName, String destinationQueueName, Serializable message){
		QueueConnectionFactory connectionFactory = getJNDIContextObject(QueueConnectionFactory.class, connectionFactoryName);
		Queue destinationQueue = getJNDIContextObject(Queue.class, destinationQueueName);
		sendObjectMessage(connectionFactory, destinationQueue, message);
	}
	public static void sendObjectMessage(QueueConnectionFactory connectionFactory, Queue destinationQueue, Serializable message) {
		QueueConnection jmsConnection = null;
		QueueSession session = null;
		QueueSender sender = null;
		try {
			jmsConnection = connectionFactory.createQueueConnection();
			session = jmsConnection.createQueueSession(false, Session.CLIENT_ACKNOWLEDGE);
			ObjectMessage jmsMessage = session.createObjectMessage();
			sender = session.createSender(destinationQueue);
			jmsMessage.setObject(message);
			sender.send(jmsMessage);
		} catch (JMSException e) {
			throw new JMSEAIException(e);
		} finally {
			forceClose(jmsConnection);
			forceClose(session);
			forceClose(sender);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T processStreamMessage(Class<T> clazz, byte[] bytes) {
		Map<String, Object> props = deserialize(Map.class, bytes);
		ObjectMapper m = new ObjectMapper();
		return m.convertValue(props, clazz);
	}
	public static byte[] serialize(Object obj) {
		try {
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			ObjectOutputStream o;
			o = new ObjectOutputStream(b);
			o.writeObject(obj);
			return b.toByteArray();
		} catch (IOException e) {
			throw new DevelopmentException(e.getMessage());//FIXME
		}
    }
    @SuppressWarnings("unchecked")
	public static <T> T deserialize(Class<T> clazz, byte[] bytes) {
    	try {
    		ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        	ObjectInputStream o = new ObjectInputStream(b);
        	return (T)o.readObject();
    	}catch (Exception e) {
        	throw new DevelopmentException(e.getMessage());//FIXME
		}
    }
    
	
	public static void forceClose(QueueConnection obj){
		if(obj != null){
			try {
				obj.close();
			} catch (JMSException e) {
				EAILogger.error("Ignored error while closing connection: "+e.getMessage());
			}
		}
	}
	public static void forceClose(QueueSession obj){
		if(obj != null){
			try {
				obj.close();
			} catch (JMSException e) {
				EAILogger.error("Ignored error while closing connection: "+e.getMessage());
			}
		}
	}
	public static void forceClose(QueueSender obj){
		if(obj != null){
			try {
				obj.close();
			} catch (JMSException e) {
				EAILogger.error("Ignored error while closing connection: "+e.getMessage());
			}
		}
	}
}
