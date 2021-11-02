package com.epam.entity;

public class Reserve {

    private Long id;
    private Long userId;
    private Long productId;

    public Reserve() {
    }

    public Reserve(Long id){
       this.id = id;
    }
    public Reserve(Long userId,Long productId){
        this.userId = userId;
        this.productId = productId;
    }
    public Reserve(Long id,Long userId,Long productId){
        this.id = id;
        this.userId = userId;
        this.productId = productId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reserve reserve = (Reserve) o;

        if (id != null ? !id.equals(reserve.id) : reserve.id != null) return false;
        if (userId != null ? !userId.equals(reserve.userId) : reserve.userId != null) return false;
        return productId != null ? productId.equals(reserve.productId) : reserve.productId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (productId != null ? productId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Reserve{" +
                "id=" + id +
                ", userId=" + userId +
                ", productId=" + productId +
                '}';
    }
}
