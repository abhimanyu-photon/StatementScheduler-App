package com.jpmc.netbanking.scheduler.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;
@Setter
@Getter
public class StatementEmailRequest {
    @NotEmpty
    private String accountNumber;
    @NotNull
    private LocalDateTime dateTime;
    @NotNull
    private ZoneId timeZone;
}
