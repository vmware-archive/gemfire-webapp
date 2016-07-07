package io.pivotal.wealthfrontier.domain;

import java.util.ArrayList;

public class AlertDetail {

	String alertMessage;
	ArrayList<String> details;

	public AlertDetail() {
		details = new ArrayList<String>();
	}


	public String getAlertMessage() {
		return alertMessage;
	}
	public void setAlertMessage(String alertMessage) {
		this.alertMessage = alertMessage;
	}
	public ArrayList<String> getDetails() {
		return details;
	}
	public void addDetails(String detail) {
		this.details.add(detail);
	}

}
