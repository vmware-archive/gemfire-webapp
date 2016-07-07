package io.pivotal.wealthfrontier.domain;



import org.springframework.data.gemfire.mapping.Region;
import java.util.ArrayList;
import java.util.Date;
import org.springframework.data.annotation.Id;

/**
 * Created by mross on 2/23/16.
 */
@Region("Customer")
public class Customer {

    @Id String id;
    String firstName;
    String LastName;
    String country;
    String streetAddress;
    String city;
    String state;
    String zip;
    String population;
    Date dob;
    String gender;
    Float netWorthInMillions;
    int children;
    Boolean selfMade;
    String sourceOfWealth;
    Float investableAssestsMillionsUSD;
    ArrayList<Product> portfolio;

    public Customer () {
        this.portfolio = new ArrayList<Product>();
    }

    public void addProductToPortfolio (Product p) {
        portfolio.add(p);
    }

    public ArrayList<Product> getPortfolio() {
        return portfolio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Float getNetWorthInMillions() {
        return netWorthInMillions;
    }

    public void setNetWorthInMillions(Float netWorthInMillions) {
        this.netWorthInMillions = netWorthInMillions;
    }

    public int getChildren() {
        return children;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public String getSourceOfWealth() {
        return sourceOfWealth;
    }

    public void setSourceOfWealth(String sourceOfWealth) {
        this.sourceOfWealth = sourceOfWealth;
    }

    public Float getInvestableAssestsMillionsUSD() {
        return investableAssestsMillionsUSD;
    }

    public void setInvestableAssestsMillionsUSD(Float investableAssestsMillionsUSD) {
        this.investableAssestsMillionsUSD = investableAssestsMillionsUSD;
    }

    public Boolean getSelfMade() {
        return selfMade;
    }

    public void setSelfMade(Boolean selfMade) {
        this.selfMade = selfMade;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "io.pivotal.wealthfrontier.domain.Customer{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", LastName='" + LastName + '\'' +
                '}';
    }
}
