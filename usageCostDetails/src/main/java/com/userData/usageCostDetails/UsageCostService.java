package com.userData.usageCostDetails;

import org.springframework.stereotype.Service;

@Service
public class UsageCostService {

    private final static double callrate = 0.01;
    private final static double dataRate = 0.10;

    public static UsageCostDetails costService( UsageDetail usageDetail){
        UsageCostDetails usageCostDetails = new UsageCostDetails();

        usageCostDetails.setUserId(usageDetail.getUserId());
        usageCostDetails.setCallCost(usageDetail.getCallTime()*callrate);
        usageCostDetails.setDataCost(usageDetail.getData()*dataRate);
        usageCostDetails.setTotalCost();

        return usageCostDetails;
    }
}
