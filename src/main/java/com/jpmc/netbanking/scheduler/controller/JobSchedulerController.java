package com.jpmc.netbanking.scheduler.controller;

import com.jpmc.netbanking.scheduler.payload.StatementEmailRequest;
import com.jpmc.netbanking.scheduler.payload.StatementEmailResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import com.jpmc.netbanking.scheduler.job.DailyStatementJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

@Slf4j
@RestController
public class JobSchedulerController {
    @Autowired
    private Scheduler scheduler;
    @PostMapping("/scheduleStatement")
    public ResponseEntity<StatementEmailResponse> scheduleEmail(@Valid @RequestBody StatementEmailRequest statementEmailRequest) {
        try {
            ZonedDateTime dateTime = ZonedDateTime.of(statementEmailRequest.getDateTime(), statementEmailRequest.getTimeZone());
            if(dateTime.isBefore(ZonedDateTime.now())) {
                StatementEmailResponse scheduleEmailResponse = new StatementEmailResponse(false,
                        "dateTime must be after current time");
                return ResponseEntity.badRequest().body(scheduleEmailResponse);
            }

            JobDetail jobDetail = buildJobDetail(statementEmailRequest);
            Trigger trigger = buildJobTrigger(jobDetail, dateTime);
            scheduler.scheduleJob(jobDetail, trigger);

            StatementEmailResponse scheduleEmailResponse = new StatementEmailResponse(true,
                    jobDetail.getKey().getName(), jobDetail.getKey().getGroup(), "Statement Scheduled Successfully!");
            return ResponseEntity.ok(scheduleEmailResponse);
        } catch (SchedulerException ex) {
            log.error("Error scheduling Statement", ex);

            StatementEmailResponse statementEmailResponse = new StatementEmailResponse(false,
                    "Error scheduling Statement. Please try later!");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(statementEmailResponse);
        }
    }

    private JobDetail buildJobDetail(StatementEmailRequest scheduleEmailRequest) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("accountNumber",scheduleEmailRequest.getAccountNumber());
        jobDataMap.put("date", scheduleEmailRequest.getDateTime());

        return JobBuilder.newJob(DailyStatementJob.class)
                .withIdentity(UUID.randomUUID().toString(), "statement-jobs")
                .withDescription("Send statement Job")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    private Trigger buildJobTrigger(JobDetail jobDetail, ZonedDateTime startAt) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "statement-triggers")
                .withDescription("Send statement Trigger")
                .startAt(Date.from(startAt.toInstant()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();
    }

}
