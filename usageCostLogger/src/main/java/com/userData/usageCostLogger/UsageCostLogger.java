package com.userData.usageCostLogger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


public class UsageCostLogger {

    public static Logger log = LoggerFactory.getLogger(UsageCostLogger.class.getSimpleName());

    public static void dataLogger(String usageCostDetails){
        log.info(usageCostDetails);
    }
}
