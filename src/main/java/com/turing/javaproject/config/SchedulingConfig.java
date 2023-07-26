package com.turing.javaproject.config;

import com.turing.javaproject.job.SendEmailVerificationMessageJob;
import com.turing.javaproject.job.SetDefaultAvatarJob;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulingConfig implements SchedulingConfigurer {

    private final SetDefaultAvatarJob setDefaultAvatarJob;
    private final SendEmailVerificationMessageJob sendEmailVerificationMessageJob;

    @Value("${application.cron-task.send-email-cron}")
    private String emailCron;

    @Value("${application.cron-task.set-avatar-cron}")
    private String avatarCron;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addCronTask(setDefaultAvatarJob, avatarCron);
        taskRegistrar.addCronTask(sendEmailVerificationMessageJob, emailCron);
    }
}
