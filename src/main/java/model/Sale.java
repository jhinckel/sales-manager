package model;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class Sale {
    
    private String id;
    private String salesmanName;
    private List<Item> items = new LinkedList<>();
    
    public Sale(String id, String salesman) {
        this.id = id;
        this.salesmanName = salesman;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItem(Item saleItem) {
        this.items.add(saleItem);
    }
    
    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;

        for (Item item : this.items) {
            total = total.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        return total;
    }
    
}
