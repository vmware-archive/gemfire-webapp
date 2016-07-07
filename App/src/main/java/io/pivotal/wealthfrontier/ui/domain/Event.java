package io.pivotal.wealthfrontier.ui.domain;

/**
 * Created by mross on 2/26/16.
 */
public class Event {
    String customerId;
    String products;
    String name;
    String netWorth;

    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public String getProducts() {
        return products;
    }
    public void setProducts(String products) {
        this.products = products;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getNetWorth() {
        return netWorth;
    }
    public void setNetWorth(String netWorth) {
        this.netWorth = netWorth;
    }
    @Override
    public String toString() {
        return "Event [customerId=" + customerId + ", products=" + products
                + ", name=" + name + ", netWorth=" + netWorth + "]";
    }
}
