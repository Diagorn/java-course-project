package com.turing.javaproject.config.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "application.cron-task")
public class CronProperties {
    private String sendEmailCron;
    private String setAvatarCron;
}
