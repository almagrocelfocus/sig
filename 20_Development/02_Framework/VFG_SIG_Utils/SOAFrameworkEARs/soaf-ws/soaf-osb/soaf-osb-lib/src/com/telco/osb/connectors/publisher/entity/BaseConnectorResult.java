package com.telco.osb.connectors.publisher.entity;

import java.io.Serializable;

import com.eai.common.entities.Jsonable;

public class BaseConnectorResult extends Jsonable implements Serializable {
	private static final long serialVersionUID = -6808362565341916966L;
	private PublisherReturn returnCode = null;

	public PublisherReturn getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(PublisherReturn returnCode) {
		this.returnCode = returnCode;
	}
}
