package com.turing.javaproject.config;

import com.turing.javaproject.config.props.CronProperties;
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
    private final CronProperties cronProperties;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addCronTask(setDefaultAvatarJob, cronProperties.getSetAvatarCron());
        taskRegistrar.addCronTask(sendEmailVerificationMessageJob, cronProperties.getSendEmailCron());
    }
}
