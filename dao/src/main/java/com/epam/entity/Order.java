package com.epam.entity;

import java.util.List;

public class Order extends Entity {

    private List<Long> productIds;
    private Long userId;

    public Order(){
    }


    public Order(Long id) {
        super(id);
    }

    public Order(List<Long> productIds, Long userId){
        this.productIds = productIds;
        this.userId = userId;
    }

   public Order(Long id, List<Long> productIds, Long userId){
        super(id);
        this.productIds = productIds;
        this.userId = userId;
   }


    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Order order = (Order) o;

        if (productIds != null ? !productIds.equals(order.productIds) : order.productIds != null) return false;
        return userId != null ? userId.equals(order.userId) : order.userId == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (productIds != null ? productIds.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "productIds=" + productIds +
                ", userId=" + userId +
                '}';
    }
}

