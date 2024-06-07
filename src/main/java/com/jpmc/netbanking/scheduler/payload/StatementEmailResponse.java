package com.jpmc.netbanking.scheduler.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StatementEmailResponse {
    private boolean success;
    private String jobId;
    private String jobGroup;
    private String message;
    public StatementEmailResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

}
