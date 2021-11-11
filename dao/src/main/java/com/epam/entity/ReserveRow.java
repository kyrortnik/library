package com.epam.entity;

public class ReserveRow extends Entity{

    private Long userId;
    private Long productId;

    public ReserveRow() {
    }

    public ReserveRow(Long id){
        super(id);
    }
    public ReserveRow(Long userId,Long productId){
        this.userId = userId;
        this.productId = productId;
    }
    public ReserveRow(Long id,Long userId,Long productId){
        super(id);
        this.userId = userId;
        this.productId = productId;
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
        if (!super.equals(o)) return false;

        ReserveRow reserve = (ReserveRow) o;

        if (userId != null ? !userId.equals(reserve.userId) : reserve.userId != null) return false;
        return productId != null ? productId.equals(reserve.productId) : reserve.productId == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (productId != null ? productId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Reserve{" +
                "userId=" + userId +
                ", productId=" + productId +
                '}';
    }
}
