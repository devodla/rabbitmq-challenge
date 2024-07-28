package uk.layme.btgpactual.orderms.entity;

import java.math.BigDecimal;

public class OrderItem {

    private String product;
    private Integer quantity;
    private BigDecimal price;

    public OrderItem() {
    }

    public OrderItem(String produto, Integer quantidade, BigDecimal preco) {
        this.product = produto;
        this.quantity = quantidade;
        this.price = preco;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
