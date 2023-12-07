package com.userData.usageCostLogger;

public class UsageCostDetails {
    private String userId;
    private double callCost;
    private double dataCost;
    private double totalCost;

    public UsageCostDetails() {
    }

    public UsageCostDetails(String userId, double callCost, double dataCost, double totalCost) {
        this.userId = userId;
        this.callCost = callCost;
        this.dataCost = dataCost;
        this.totalCost = totalCost;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getCallCost() {
        return callCost;
    }

    public void setCallCost(double callCost) {
        this.callCost = callCost;
    }

    public double getDataCost() {
        return dataCost;
    }

    public void setDataCost(double dataCost) {
        this.dataCost = dataCost;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost() {
        this.totalCost = this.callCost+this.dataCost;
    }

    @Override
    public String toString() {
        return "UsageCostDetails{" +
                "userId='" + userId + '\'' +
                ", callCost=" + callCost +
                ", dataCost=" + dataCost +
                ", totalCost=" + totalCost +
                '}';
    }
}

