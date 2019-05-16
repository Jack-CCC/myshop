package cn.myshop.domain;

public class Cart {
    private String id;

    private String commodityId;

    private Integer commodityNum;

    private String customerId;

    private double commodityPrice;

    public Cart() {
    }

    public Cart(String id, String commodityId, Integer commodityNum, String customerId, double commodityPrice) {
        this.id = id;
        this.commodityId = commodityId;
        this.commodityNum = commodityNum;
        this.customerId = customerId;
        this.commodityPrice = commodityPrice;
    }

    public double getPay(){
        return this.commodityNum * this.commodityPrice;
    }

    public double getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(double commodityPrice) {
        this.commodityPrice = commodityPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId == null ? null : commodityId.trim();
    }

    public Integer getCommodityNum() {
        return commodityNum;
    }

    public void setCommodityNum(Integer commodityNum) {
        this.commodityNum = commodityNum;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }
}