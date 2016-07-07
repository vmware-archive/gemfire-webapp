package io.pivotal.wealthfrontier.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.Region;

import java.util.ArrayList;

/**
 * Created by mross on 2/25/16.
 */
@Region("Alert")
public class Alert {

    @Id
    String customerId;
    ArrayList<AlertDetail> alertDetails;

    public Alert() {
        this.alertDetails = new ArrayList<AlertDetail>();
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public ArrayList<AlertDetail> getalertDetails() {
        return alertDetails;
    }

    public void addAlertDetail(AlertDetail message){
            alertDetails.add(message);
    }

    @Override
    public String toString() {
        return "Alert{" +
                "customerId='" + customerId + '\'' +
                ", alertDetails=" + alertDetails +
                '}';
    }
}
