package com.shss.restaurantwaiter.object;

public class CartInfo {
	private String id;
	private String idTable;
	private String imgUrl;
	private String nameCart;
	private double price;
	private int numberCart;
	private String note;
	private boolean isTrue;
	private String productId;
	private String tableId;
	private String CARTID;

	public String getCARTID() {
		return CARTID;
	}

	public void setCARTID(String cARTID) {
		CARTID = cARTID;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public CartInfo(String imgUrl, String nameCart, double price, int numberCart) {
		super();
		this.imgUrl = imgUrl;
		this.nameCart = nameCart;
		this.price = price;
		this.numberCart = numberCart;
	}

	public CartInfo() {

	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	public String getIdTable() {
		return idTable;
	}

	public void setIdTable(String idTable) {
		this.idTable = idTable;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the isTrue
	 */
	public boolean isTrue() {
		return isTrue;
	}

	/**
	 * @param isTrue
	 *            the isTrue to set
	 */
	public void setTrue(boolean isTrue) {
		this.isTrue = isTrue;
	}

	/**
	 * @return the imgUrl
	 */
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	/**
	 * @param imgUrl
	 *            the imgUrl to set
	 */
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	/**
	 * @return the nameCart
	 */
	public String getNameCart() {
		return nameCart;
	}

	/**
	 * @param nameCart
	 *            the nameCart to set
	 */
	public void setNameCart(String nameCart) {
		this.nameCart = nameCart;
	}

	/**
	 * @return the numberCart
	 */
	public int getNumberCart() {
		return numberCart;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @param numberCart
	 *            the numberCart to set
	 */
	public void setNumberCart(int numberCart) {
		this.numberCart = numberCart;
	}

}
