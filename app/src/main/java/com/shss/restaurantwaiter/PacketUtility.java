/*
 * Name: $RCSfile: PacketUtility.java,v $
 * Version: $Revision: 1.1 $
 * Date: $Date: Nov 15, 2011 2:05:59 PM $
 *
 * Copyright (C) 2011 COMPANY_NAME, Inc. All rights reserved.
 */

package com.shss.restaurantwaiter;

/**
 * PacketUtility
 * 
 * @author Lemon
 */
public class PacketUtility {
	/**
	 * Constructor
	 * 
	 * @param context
	 */
	public PacketUtility() {
	}

	/**
	 * Get package name
	 * 
	 * @return
	 */
	public String getPacketName() {
		return this.getClass().getPackage().getName();
	}
}
