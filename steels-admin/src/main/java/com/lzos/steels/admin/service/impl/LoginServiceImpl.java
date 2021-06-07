package com.lzos.steels.admin.service.impl;

import com.lzos.steels.admin.dao.UserMapper;
import com.lzos.steels.admin.entity.dto.User;
import com.lzos.steels.admin.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User doLogin(int id) {
        return userMapper.getUserById(id);
    }

}
