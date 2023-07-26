package com.turing.javaproject.job;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendEmailVerificationMessageJob implements Runnable {

    @Override
    public void run() {
        System.out.println("Doing stuff");
    }
}
