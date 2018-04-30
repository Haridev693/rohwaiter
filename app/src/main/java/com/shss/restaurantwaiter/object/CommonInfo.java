/*
 * Name: $RCSfile: CommonInfo.java,v $
 * Version: $Revision: 1.1 $
 * Date: $Date: Oct 31, 2011 3:27:18 PM $
 *
 * Copyright (C) 2011 COMPANY_NAME, Inc. All rights reserved.
 */

package com.shss.restaurantwaiter.object;

/**
 * CommonInfo uses to save common http response
 * 
 * @author Lemon
 */
public class CommonInfo {
	protected boolean success;
	protected String error;
	protected String message;

	/**
	 * CommonInfo constructor
	 */
	public CommonInfo() {
		this.success = false;
		this.message = "";
		this.error="";
	}

	/**
	 * CommonInfo constructor
	 * 
	 * @param success
	 * @param message
	 */
	public CommonInfo(boolean success, String message,String error) {
		this.success = success;
		this.message = message;
		this.error=error;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success
	 *            the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
