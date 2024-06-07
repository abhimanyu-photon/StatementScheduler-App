package com.jpmc.netbanking.scheduler.client;

import com.jpmc.netbanking.scheduler.client.response.TransactionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "netbanking", url = "http://localhost:8180")
public interface NetBankingClient {

    @GetMapping(value = "/api/account/transactions")
    TransactionResponse clientResponse(@RequestParam(required = true) String accountNumber, @RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate);
}
