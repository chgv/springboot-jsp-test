package com.baizhi.service;

import com.baizhi.entity.User;

public interface UserService {

    //用戶註冊
    void register(User user);

    //用戶登入
    User login(String username, String password);
}
