package io.pivotal.wealthfrontier.domain;


import org.springframework.data.gemfire.mapping.Region;


/**
 * Created by mross on 2/23/16.
 */
@Region("Customer")
public class Product {


    String product_category;
    String product;
    int credit_duration;
    float credit_rate;
    String credit_mature_date;
    float credit_amt;
    float investment_value;
    float investment_pct_change_daily;
    float investment_pct_change_monthly;
    boolean investment_consumer_goods;
    boolean investment_consumer_services;
    boolean investment_telco;
    boolean investment_healthcare;
    boolean investment_industrials;
    boolean investment_financials;
    boolean investment_tech;
    boolean investment_basic_materials;
    boolean investment_oil_gas;
    float deposit_value;
    float deposit_pct_change_monthly;
    boolean deposit_pct_ira_paid;


    public  Product() {

    }

    public Product(String product_category, String product, int credit_duration, float credit_rate, String credit_mature_date, float credit_amt, float investment_value,
                   float investment_pct_change_daily, float investment_pct_change_monthly, boolean investment_consumer_goods,
                   boolean investment_consumer_services, boolean investment_telco, boolean investment_healthcare,
                   boolean investment_industrials, boolean investment_financials, boolean investment_tech, boolean investment_basic_materials,
                   boolean investment_oil_gas, float deposit_value, float deposit_pct_change_monthly, boolean deposit_pct_ira_paid) {
        this.product_category = product_category;
        this.product = product;
        this.credit_duration = credit_duration;
        this.credit_rate = credit_rate;
        this.credit_mature_date = credit_mature_date;
        this.credit_amt = credit_amt;
        this.investment_value = investment_value;
        this.investment_pct_change_daily = investment_pct_change_daily;
        this.investment_pct_change_monthly = investment_pct_change_monthly;
        this.investment_consumer_goods = investment_consumer_goods;
        this.investment_consumer_services = investment_consumer_services;
        this.investment_telco = investment_telco;
        this.investment_healthcare = investment_healthcare;
        this.investment_industrials = investment_industrials;
        this.investment_financials = investment_financials;
        this.investment_tech = investment_tech;
        this.investment_basic_materials = investment_basic_materials;
        this.investment_oil_gas = investment_oil_gas;
        this.deposit_value = deposit_value;
        this.deposit_pct_change_monthly = deposit_pct_change_monthly;
        this.deposit_pct_ira_paid = deposit_pct_ira_paid;
    }

    public int getCredit_duration() {
        return credit_duration;
    }

    public void setCredit_duration(int credit_duration) {
        this.credit_duration = credit_duration;
    }

    public String getProduct_category() {
        return product_category;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public float getCredit_rate() {
        return credit_rate;
    }

    public void setCredit_rate(float credit_rate) {
        this.credit_rate = credit_rate;
    }

    public String getCredit_mature_date() {
        return credit_mature_date;
    }

    public void setCredit_mature_date(String credit_mature_date) {
        this.credit_mature_date = credit_mature_date;
    }

    public float getCredit_amt() {
        return credit_amt;
    }

    public void setCredit_amt(float credit_amt) {
        this.credit_amt = credit_amt;
    }

    public float getInvestment_value() {
        return investment_value;
    }

    public void setInvestment_value(float investment_value) {
        this.investment_value = investment_value;
    }

    public float getInvestment_pct_change_daily() {
        return investment_pct_change_daily;
    }

    public void setInvestment_pct_change_daily(float investment_pct_change_daily) {
        this.investment_pct_change_daily = investment_pct_change_daily;
    }

    public float getInvestment_pct_change_monthly() {
        return investment_pct_change_monthly;
    }

    public void setInvestment_pct_change_monthly(float investment_pct_change_monthly) {
        this.investment_pct_change_monthly = investment_pct_change_monthly;
    }

    public boolean isInvestment_consumer_goods() {
        return investment_consumer_goods;
    }

    public void setInvestment_consumer_goods(boolean investment_consumer_goods) {
        this.investment_consumer_goods = investment_consumer_goods;
    }

    public boolean isInvestment_consumer_services() {
        return investment_consumer_services;
    }

    public void setInvestment_consumer_services(boolean investment_consumer_services) {
        this.investment_consumer_services = investment_consumer_services;
    }

    public boolean isInvestment_telco() {
        return investment_telco;
    }

    public void setInvestment_telco(boolean investment_telco) {
        this.investment_telco = investment_telco;
    }

    public boolean isInvestment_healthcare() {
        return investment_healthcare;
    }

    public void setInvestment_healthcare(boolean investment_healthcare) {
        this.investment_healthcare = investment_healthcare;
    }

    public boolean isInvestment_industrials() {
        return investment_industrials;
    }

    public void setInvestment_industrials(boolean investment_industrials) {
        this.investment_industrials = investment_industrials;
    }

    public boolean isInvestment_financials() {
        return investment_financials;
    }

    public void setInvestment_financials(boolean investment_financials) {
        this.investment_financials = investment_financials;
    }

    public boolean isInvestment_tech() {
        return investment_tech;
    }

    public void setInvestment_tech(boolean investment_tech) {
        this.investment_tech = investment_tech;
    }

    public boolean isInvestment_basic_materials() {
        return investment_basic_materials;
    }

    public void setInvestment_basic_materials(boolean investment_basic_materials) {
        this.investment_basic_materials = investment_basic_materials;
    }

    public boolean isInvestment_oil_gas() {
        return investment_oil_gas;
    }

    public void setInvestment_oil_gas(boolean investment_oil_gas) {
        this.investment_oil_gas = investment_oil_gas;
    }

    public float getDeposit_value() {
        return deposit_value;
    }

    public void setDeposit_value(float deposit_value) {
        this.deposit_value = deposit_value;
    }

    public float getDeposit_pct_change_monthly() {
        return deposit_pct_change_monthly;
    }

    public void setDeposit_pct_change_monthly(float deposit_pct_change_monthly) {
        this.deposit_pct_change_monthly = deposit_pct_change_monthly;
    }

    public boolean isDeposit_pct_ira_paid() {
        return deposit_pct_ira_paid;
    }

    public void setDeposit_pct_ira_paid(boolean deposit_pct_ira_paid) {
        this.deposit_pct_ira_paid = deposit_pct_ira_paid;
    }



}
