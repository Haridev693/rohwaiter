package com.shss.restaurantwaiter.object;

public class ProductInfo {
	private String id;
	private String idTable;
	private String name;
	private String code;
	private double price;
	private int numberName;
	private String imgCategory;
	private String categoryId;
	private String Totalqty;

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getIdTable() {
		return idTable;
	}

	public void setIdTable(String idTable) {
		this.idTable = idTable;
	}

	public ProductInfo(String name, String code, double price) {
		this.name = name;
		this.code = code;
		this.price = price;
		this.numberName = 0;

	}

	/**
	 * @return the imgCategory
	 */
	public String getImgCategory() {
		return imgCategory;
	}

	/**
	 * @param imgCategory
	 *            the imgCategory to set
	 */
	public void setImgCategory(String imgCategory) {
		this.imgCategory = imgCategory;
	}

	public ProductInfo() {

	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the numberName
	 */
	public int getNumberName() {
		return numberName;
	}

	/**
	 * @param numberName
	 *            the numberName to set
	 */
	public void setNumberName(int numberName) {
		this.numberName = numberName;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
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

	public void setTotalqty(String totalqty)
	{
		this.Totalqty = totalqty;
	}

	public String getTotalqty()
	{
		return Totalqty;
	}
}
