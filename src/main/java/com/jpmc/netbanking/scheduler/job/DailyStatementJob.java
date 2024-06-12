package com.jpmc.netbanking.scheduler.job;

import com.jpmc.netbanking.scheduler.dto.TransactionDto;
import com.jpmc.netbanking.scheduler.utils.PdfUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class DailyStatementJob extends QuartzJobBean {

    private final RestTemplate restTemplate;

    @Value("${web.application.client.port}")
    private String clientServerUrl;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        String accountNumber=jobDataMap.getString("accountNumber");
        try {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date date = formatter.parse(jobDataMap.get("date").toString());

            // Extract only the date part
            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy-MM-dd");
            String extractedDate = yearFormat.format(date);
        String url = clientServerUrl+"/api/account/transactions?accountNumber={accountNumber}&fromDate={fromDate}&toDate={toDate}" ;
        Map<String, String> params = new HashMap<>();
        params.put("accountNumber",accountNumber);
        params.put("fromDate", extractedDate);
        params.put("toDate", extractedDate);

            ResponseEntity<List<TransactionDto>> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
                    null, new ParameterizedTypeReference<List<TransactionDto>>() {}, params
            );
            List<TransactionDto> transactions = responseEntity.getBody();
            PdfUtils.generatePdf("statement-",transactions);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

  }
