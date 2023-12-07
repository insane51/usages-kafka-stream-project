package com.userData.usageDetailSender;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class UsageDetail {
    private String userId;
    private long callTime;
    private long data;

    @Override
    public String toString() {
        return "UsageDetails{" +
                "userId='" + userId + '\'' +
                ", callTime='" + callTime + '\'' +
                ", data='" + data + '\'' +
                '}';
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getCallTime() {
        return callTime;
    }

    public void setCallTime(long callTime) {
        this.callTime = callTime;
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }
}
