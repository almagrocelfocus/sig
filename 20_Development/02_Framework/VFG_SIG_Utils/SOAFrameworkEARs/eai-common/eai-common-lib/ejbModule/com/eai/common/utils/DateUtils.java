package com.eai.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
	
	private static final ThreadLocal<SimpleDateFormat> _localStorageW3CDateFormat = new ThreadLocal<SimpleDateFormat>(){
    	protected SimpleDateFormat initialValue() {
    		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");//'Z'
    	}
    };
    public static SimpleDateFormat getW3CDateFormat(){
    	return _localStorageW3CDateFormat.get();
    }
	
    private static ThreadLocal<SimpleDateFormat[]> _localStorageFormaters = new ThreadLocal<SimpleDateFormat[]>(){
		protected SimpleDateFormat[] initialValue() {//use timezone to set the external date in the current timezone, converting the value correctly for UI
			return new SimpleDateFormat[] {
				//With zone
				new SimpleDateFormat("yyyyMMddHHmmssSSSZ"),//0
				new SimpleDateFormat("yyyyMMddHHmmssSZ"),
				new SimpleDateFormat("yyyyMMddHHmmssZ"),
				new SimpleDateFormat("yyyyMMddHHmmZ"),
				new SimpleDateFormat("yyyyMMddHHmmssSSSz"),//4
				new SimpleDateFormat("yyyyMMddHHmmssSz"),
				new SimpleDateFormat("yyyyMMddHHmmssz"),
				new SimpleDateFormat("yyyyMMddHHmmz"),
				//No zone
				new SimpleDateFormat("yyyyMMddHHmmssSSS"),//8
				new SimpleDateFormat("yyyyMMddHHmmssS"),
				new SimpleDateFormat("yyyyMMddHHmmss"),
				new SimpleDateFormat("yyyyMMddHHmm"),
				new SimpleDateFormat("yyyyMMdd")//12
			};
		}
	};
	
	private DateUtils(){
	}
	
	public static SimpleDateFormat[] getW3CDateFormatLst(){
    	return _localStorageFormaters.get();
    }
	public static SimpleDateFormat getCompleteDateStrFormat(){
    	return getW3CDateFormatLst()[8];
    }
	
	public static final String getMonthFromDate(Date dateInput){
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("MMMM");  // full month name
		return simpleDateformat.format(dateInput);
	}
	
	public static final String getYearFromDate(Date dateInput){
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("yyyy");  // full month name
		return simpleDateformat.format(dateInput);
	}
	
	public static final String getFileDateString( Date date ){
		return getCompleteDateStrFormat().format(date);
	}
	
	public static final String getStandardDateFormat(Date dateInput){
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("dd-MM-yyyy");  // full month name
		return simpleDateformat.format(dateInput);
	}
	
	public static final String getDateFormatedInterval(Date fromDate, Date toDate){
		return getStandardDateFormat(fromDate) + FileConfigurationUtils.getMessage("common.lblto") + getStandardDateFormat(toDate);
	}
	
	public static final Calendar getCalendarFromDate(Date date){
		return getCalendarFromDate(date,false);
	}
	public static final Calendar getCalendarFromDate(Date date, boolean resetHours){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if(resetHours){
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
		}
		return cal;
	}
	
	public static final Long getMilisToMidnight(Date fromDate){
		return getMilisToMidnight( getCalendarFromDate(fromDate) );
	}
	public static final Long getMilisToMidnight(Calendar fromDate){
		return getMilisBetweenMidnigth(fromDate, 1);
	}
	public static final Long getMilisFromMidnight(Date fromDate){
		return getMilisFromMidnight( getCalendarFromDate(fromDate) );
	}
	public static final Long getMilisFromMidnight(Calendar fromDate){
		return -getMilisBetweenMidnigth(fromDate, 0);
	}
	
	public static final Long getMilisBetweenMidnigth(Calendar fromDate, int numberOfDays){
		Calendar toDate = getCalendarFromDate(fromDate.getTime(), true);
		toDate.add(Calendar.DATE, numberOfDays);
		return toDate.getTimeInMillis() - fromDate.getTimeInMillis();
	}
	
	public static final Long getMilisFromCalendarField(int field, int value){
		switch (field) {
		case Calendar.DATE:
			return value*24*60*60*1000L;
		case Calendar.HOUR:
		case Calendar.HOUR_OF_DAY:
			return value*60*60*1000L;
		case Calendar.MINUTE:
			return value*60*1000L;
		case Calendar.SECOND:
			return value*1000L;
		case Calendar.MILLISECOND:
			return value+0L;
		default:
			throw new RuntimeException("Field not supported in getMilisFromCalendarField");
		}
	}
	
	/**
     * Devolve uma data igual a passada, desprezando o valor dos campos que néo fazem parte do filtro.
     *
     * @param filter campo a definir
     * @param date data por onde se paseia
     * @return a nova data com os campos truncados
     */
    private static Date truncateDate(int filter, Date date) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(date);
        switch (filter) {
            case Calendar.SECOND:
                return date.getTime() % 1000 != 0 ? new Date(date.getTime() / 1000 * 1000) : date;
            case Calendar.MINUTE :
            	if (date.getTime() % 1000 == 0 && (date instanceof java.sql.Date || (cal.get(Calendar.SECOND) == 0))) {
                    return date;
                }
                return getDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.SECOND));
            case Calendar.HOUR :
            	if (date.getTime() % 1000 == 0 && (date instanceof java.sql.Date || (cal.get(Calendar.MINUTE) == 0 && cal.get(Calendar.SECOND) == 0))) {
                    return date;
                }
                return getDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY));
            case Calendar.DATE :
                if (date.getTime() % 1000 == 0 && (date instanceof java.sql.Date || (cal.get(Calendar.HOUR_OF_DAY) == 0 && cal.get(Calendar.MINUTE) == 0 && cal.get(Calendar.SECOND) == 0))) {
                    return date;
                }
                return getDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            case Calendar.MONTH :
                if (date.getTime() % 1000 == 0 && cal.get(Calendar.DAY_OF_MONTH) == 1 && (date instanceof java.sql.Date || (cal.get(Calendar.HOUR_OF_DAY) == 0 && cal.get(Calendar.MINUTE) == 0 && cal.get(Calendar.SECOND) == 0))) {
                    return date;
                }
                return getDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
            default:
                return date;
        }
    }
    
    /**
     * Constroi uma nova instancia de uma data.
     *
     * <P>O ano 1900 corresponde ao ano 0.
     * <P>O mes de Janeiro corresponde ao mês 0.
     *
     * @param year ano
     * @param month mês
     * @param day dia do mês
     * @return uma data preenchida com a informação passada
     */
    public static Date getDate(int... fields) {
        Calendar cal = GregorianCalendar.getInstance();
        if( fields != null ){
        	cal.set(Calendar.YEAR, fields.length > 0 ? fields[0] : 0);
            cal.set(Calendar.MONTH, fields.length > 1 ? fields[1] : 0);
            cal.set(Calendar.DAY_OF_MONTH, fields.length > 2 ? fields[2] : 0);
            cal.set(Calendar.HOUR_OF_DAY, fields.length > 3 ? fields[3] : 0);
            cal.set(Calendar.MINUTE, fields.length > 4 ? fields[4] : 0);
            cal.set(Calendar.SECOND, fields.length > 5 ? fields[5] : 0);
            cal.set(Calendar.MILLISECOND, fields.length > 6 ? fields[6] : 0);        	
        }
        return cal.getTime();
    }
    
    /**
     * Devolve o número de segundos entre duas datas.
     *
     * @param date1 primeira data
     * @param date2 segunda data
     * @return o número de horas entre duas datas
     */
    private static final double MILISECONDS_PER_SECOND = 1000;//Número de milisegundos de um dia.
    private static final double MILISECONDS_PER_MINUTE = MILISECONDS_PER_SECOND * 60;//Número de milisegundos de um dia.
    private static final double MILISECONDS_PER_HOUR = MILISECONDS_PER_MINUTE * 60;//Número de milisegundos de um dia.
    private static final double MILISECONDS_PER_DAY = MILISECONDS_PER_HOUR * 24;//Número de milisegundos de um dia.
    public static int getSecondsBetween(Date date1, Date date2) {
    	if( date1 == null && date2 == null ){
    		return 0;
    	}
    	if (date1 == null || date2 == null) {
    		throw new IllegalArgumentException("The dates cannot be null");
    	}

    	final long time1 = truncateDate(Calendar.SECOND, date1).getTime();
    	final long time2 = truncateDate(Calendar.SECOND, date2).getTime();

    	return (int) Math.round((time2 - time1) / MILISECONDS_PER_SECOND);
    }
    public static int getMinutesBetween(Date date1, Date date2) {
    	if( date1 == null && date2 == null ){
    		return 0;
    	}
    	if (date1 == null || date2 == null) {
    		throw new IllegalArgumentException("The dates cannot be null");
    	}

    	final long time1 = truncateDate(Calendar.MINUTE, date1).getTime();
    	final long time2 = truncateDate(Calendar.MINUTE, date2).getTime();

    	return (int) Math.round((time2 - time1) / MILISECONDS_PER_MINUTE);
    }
    public static int getHoursBetween(Date date1, Date date2) {
    	if( date1 == null && date2 == null ){
    		return 0;
    	}
    	if (date1 == null || date2 == null) {
    		throw new IllegalArgumentException("The dates cannot be null");
    	}

    	final long time1 = truncateDate(Calendar.HOUR, date1).getTime();
    	final long time2 = truncateDate(Calendar.HOUR, date2).getTime();

    	return (int) Math.round((time2 - time1) / MILISECONDS_PER_HOUR);
    }
    public static int getDaysBetween(Date date1, Date date2) {
    	if( date1 == null && date2 == null ){
    		return 0;
    	}
    	if (date1 == null || date2 == null) {
    		throw new IllegalArgumentException("The dates cannot be null");
    	}

    	final long time1 = truncateDate(Calendar.DATE, date1).getTime();
    	final long time2 = truncateDate(Calendar.DATE, date2).getTime();

    	return (int) Math.round((time2 - time1) / MILISECONDS_PER_DAY);
    }
}
