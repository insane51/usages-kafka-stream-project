package com.userData.usageDetailSender;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class UsageDetailSender {
    private static String users[] = {"user1","user2","user3","user4","user5","user6","user7"};

    public static UsageDetail userSupplier(){
        UsageDetail usageDetail = new UsageDetail();

        usageDetail.setUserId(users[new Random().nextInt(7)]);
        usageDetail.setCallTime(new Random().nextInt(500));
        usageDetail.setData(new Random().nextInt(1000));

        return usageDetail;
    }
}
