package com.eai.common.entities;

import java.util.Random;

public class DummyEntityGenerator {
	
	private DummyEntityGenerator(){
	}
	
	public static final Random random = new Random();
	
	private static final int MAX_INT = 999999;
	private static final int MIN_INT = 0;
	
	public static String getRandomNumber(int min, int max){
		return ""+random.nextInt((max - min) + 1) + min;
	}
	public static String getRandomNumber(){
		return getRandomNumber(MIN_INT, MAX_INT);
	}
}
