package io.pivotal.wealthfrontier.business;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import io.pivotal.wealthfrontier.domain.Alert;
import io.pivotal.wealthfrontier.domain.AlertDetail;
import io.pivotal.wealthfrontier.repositories.AlertRepository;
import io.pivotal.wealthfrontier.ui.domain.Event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component(value="eventHandler")
public class EventHandler {

    @Autowired
    ClientCache gemfireCache;

    public void handleEvent(Event[] events) {

        StringBuffer sb = new StringBuffer("similarcustomer|");
        String customerId = events[0].getCustomerId();

        for (Event event : events) {
            sb.append(event.getName());
            sb.append(".");
            sb.append(event.getProducts());
            sb.append("|");
        }

        Region<String, Alert> alertRegion = gemfireCache.getRegion("Alert");
        Alert alert = alertRegion.get(customerId);
        AlertDetail details = new AlertDetail();

        if (alert == null) {
            alert = new Alert();
            alert.setCustomerId(customerId);
            details.setAlertMessage(sb.toString());
            alert.addAlertDetail(details);
        } else {
            details.setAlertMessage(sb.toString());
            alert.addAlertDetail(details);
        }
        alertRegion.put(alert.getCustomerId(), alert);
    }
}