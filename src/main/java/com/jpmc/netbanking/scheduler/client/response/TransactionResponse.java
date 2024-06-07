package com.jpmc.netbanking.scheduler.client.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jpmc.netbanking.scheduler.dto.TransactionDto;
import lombok.Data;

import java.util.List;
@Data
public class TransactionResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
   private List<TransactionDto> transactionDtoList;

}
