package com.baizhi.dao;

import com.baizhi.entity.User;

public interface UserDao {

    //根據用戶名查詢用戶
    User findByUserName(String username);

    //註冊用戶
    void save(User user);
}
