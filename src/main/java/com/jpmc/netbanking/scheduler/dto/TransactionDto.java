package com.jpmc.netbanking.scheduler.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jpmc.netbanking.scheduler.enums.TransactionType;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDto {
    private Long id; 
    private double amount;
    private TransactionType transaction_type;
    private LocalDate transaction_date;
    private String sourceAccountNumber;
    private String targetAccountNumber;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public TransactionType getTransaction_type() {
		return transaction_type;
	}
	public void setTransaction_type(TransactionType transaction_type) {
		this.transaction_type = transaction_type;
	}
	public LocalDate getTransaction_date() {
		return transaction_date;
	}
	public void setTransaction_date(LocalDate transaction_date) {
		this.transaction_date = transaction_date;
	}
	public String getSourceAccountNumber() {
		return sourceAccountNumber;
	}
	public void setSourceAccountNumber(String sourceAccountNumber) {
		this.sourceAccountNumber = sourceAccountNumber;
	}
	public String getTargetAccountNumber() {
		return targetAccountNumber;
	}
	public void setTargetAccountNumber(String targetAccountNumber) {
		this.targetAccountNumber = targetAccountNumber;
	}


    // getters and setters
}