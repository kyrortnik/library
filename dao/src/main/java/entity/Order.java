package entity;

public class Order extends Entity {

    private Product product;
    private UserDTO userDTO;

    public Order() {
    }

    public Order(Long id) {
        super(id);
    }

    public Order(Long id, Product product,UserDTO userDTO) {
        super(id);
        this.product = product;
        this.userDTO = userDTO;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Order order = (Order) o;

        if (product != null ? !product.equals(order.product) : order.product != null) return false;
        return userDTO != null ? userDTO.equals(order.userDTO) : order.userDTO == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (product != null ? product.hashCode() : 0);
        result = 31 * result + (userDTO != null ? userDTO.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "product=" + product +
                ", userDTO=" + userDTO +
                '}';
    }
}

