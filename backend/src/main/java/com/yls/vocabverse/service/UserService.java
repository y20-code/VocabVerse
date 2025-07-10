package com.yls.vocabverse.service;

import com.yls.vocabverse.entity.User;

public interface UserService {

    void register(User user);

    String login(User user);
}
