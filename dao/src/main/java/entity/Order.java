package entity;

public class Order extends Entity {

    private Long productId;
    private Long userId;

    public Order(){

    }


    public Order(Long id) {
        super(id);
    }

   public Order(Long id,Long productId,Long userId){
        super(id);
        this.productId = productId;
        this.userId = userId;
   }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

        if (productId != null ? !productId.equals(order.productId) : order.productId != null) return false;
        return userId != null ? userId.equals(order.userId) : order.userId == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (productId != null ? productId.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "productId=" + productId +
                ", userId=" + userId +
                '}';
    }
}

