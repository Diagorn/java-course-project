package com.turing.javaproject.job;

import com.turing.javaproject.entity.User;
import com.turing.javaproject.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SetDefaultAvatarJob implements Runnable {

    @Value("${application.default-avatar-url}")
    private String defaultAvatarUrl;

    private final UserRepo userRepo;

    @Override
    public void run() {
        List<User> emptyAvatarUserList = userRepo.findAllByAvatarUrlNull();
        emptyAvatarUserList.forEach(user -> {
            user.setAvatarUrl(defaultAvatarUrl);
            userRepo.save(user);
        });
    }
}
