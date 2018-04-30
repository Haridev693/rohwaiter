package com.shss.restaurantwaiter.object;

public class UserInfo extends CommonInfo {
	private String userId;

	private String userName;

	private String userPassword;

	/**
	 * Constructor
	 */
	public UserInfo() {
		this.userId = "0";
		this.userName = "";
		this.userPassword = "";
	}

	/**
	 * Constructor
	 * 
	 * @param userId
	 * @param userName
	 * @param accessToken
	 */
	public UserInfo(String userId, String userName) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPassword = "";
	}

	/**
	 * Constructor
	 * 
	 * @param success
	 * @param message
	 * @param userId
	 * @param userName
	 * @param accessToken
	 */
	public UserInfo(boolean success, String message, String userId,
			String userName) {
		this.success = success;
		this.message = message;
		this.userId = userId;
		this.userName = userName;
		this.userPassword = "";
	}

	/**
	 * Constructor
	 * 
	 * @param userId
	 * @param userName
	 * @param userPassword
	 * @param accessToken
	 */
	public UserInfo(String userId, String userName, String userPassword) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPassword = userPassword;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the userPassword (XMPP)
	 */
	public String getUserPassword() {
		return userPassword;
	}

	/**
	 * @param userPassword
	 *            the userPassword to set (XMPP)
	 */
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

}
