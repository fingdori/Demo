package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.mybatis.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseServiceImpl implements DatabaseService{

    private final Logger logger = LogManager.getLogger("DatabaseServiceImpl");

    private UserMapper userMapper;

    DatabaseServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public void getUser() {
        List<User> userList = userMapper.getUser();
        for (User user:
             userList) {
            logger.info("user :" + user);
        }

    }
}
