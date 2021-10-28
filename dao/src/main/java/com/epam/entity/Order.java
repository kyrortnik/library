package com.epam.entity;

public class Order extends Entity {

    private String productIds;
    private Long userId;
    private String status;

    public Order(){

    }


    public Order(Long id) {
        super(id);
    }

    public Order(String productIds, Long userId){
        this.productIds = productIds;
        this.userId = userId;
    }

   public Order(Long id, String productIds, Long userId){
        super(id);
        this.productIds = productIds;
        this.userId = userId;
   }
    public Order(Long id, String productIds, Long userId,String status){
        super(id);
        this.productIds = productIds;
        this.userId = userId;
        this.status = status;
    }


    public String getProductIds() {
        return productIds;
    }

    public void setProductIds(String productIds) {
        this.productIds = productIds;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Order order = (Order) o;

        if (productIds != null ? !productIds.equals(order.productIds) : order.productIds != null) return false;
        if (userId != null ? !userId.equals(order.userId) : order.userId != null) return false;
        return status != null ? status.equals(order.status) : order.status == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (productIds != null ? productIds.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "productIds='" + productIds + '\'' +
                ", userId=" + userId +
                ", status='" + status + '\'' +
                '}';
    }
}

