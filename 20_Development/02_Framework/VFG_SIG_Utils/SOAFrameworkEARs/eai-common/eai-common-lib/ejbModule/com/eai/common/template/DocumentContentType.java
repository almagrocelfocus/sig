package com.eai.common.template;

public class DocumentContentType {
	public static final String GENERAL_DOCUMENT_CONTENT_DISPOSITION_FORMAT = "attachment;filename=%1$s";
	public static final String IMG_DOCUMENT_CONTENT_DISPOSITION_FORMAT = "attachment;filename=%1$s.%2$s";
	public static final String IMG_DOCUMENT_CONTENT_TYPE_FORMAT = "image/%1$s";
	public static final String RTF_DOCUMENT_CONTENT_TYPE = "application/rtf";
	public static final String XLS_DOCUMENT_CONTENT_TYPE = "application/ms-excel";
	public static final String CSV_DOCUMENT_CONTENT_TYPE = "text/csv";
	public static final String EMAIL_DOCUMENT_CONTENT_TYPE = "text/html";
	public static final String CONTENT_ID = "%1$s.%2$s";
	
	private String contentId;
	private String contentType;
	private String contentDisposition;
	
	public DocumentContentType(){
	}
	
	public DocumentContentType(String contentId, String contentType, String contentDisposition){
		setContentId(contentId);
		setContentType(contentType);
		setContentDisposition(contentDisposition);
	}

	/*
	 * START - PROPERTIES
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getContentType() {
		return contentType;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	public String getContentId() {
		return contentId;
	}
	
	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}
	public String getContentDisposition() {
		return contentDisposition;
	}
	/*
	 * END - PROPERTIES
	 */

	@Override
	public String toString(){
		return String.format("ContentId=%1$s - ContentType=%2$s - ContentDisposition=%3$s", getContentId(), getContentType(), getContentDisposition());
	}
	
}
