package com.shss.restaurantwaiter.object;

public class TablesInfo {

    public static final int TABLE_USING = 1;
    public static final int TABLE_FREE = 2;
    private String tablesId;
    private String imgUrl;
    private int Status;

    // private String imageUrl;
    // private String numberTable;

    public TablesInfo() {

    }

    /**
     * @return the imgUrl
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * @param imgUrl the imgUrl to set
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return Status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        Status = status;
    }

    /**
     * @return the tablesId
     */
    public String getTablesId() {
        return tablesId;
    }

    /**
     * @param tablesId the tablesId to set
     */
    public void setTablesId(String tablesId) {
        this.tablesId = tablesId;
    }

    public TablesInfo(String tablesId) {
        super();
        this.tablesId = tablesId;
    }

}
