package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.mybatis.TableDataUser;
import com.example.demo.model.mybatis.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
        logger.info("test");
        List<User> userList = userMapper.getUser();
        for (User user:
             userList) {
            logger.info("user :" + user);
        }

    }

    @Override
    public String selectTable() {
        logger.info("select table called");
        List<User> selectUser = userMapper.selectUser();
        TableDataUser tableDataUser = new TableDataUser();
        tableDataUser.setData(selectUser);
        tableDataUser.setDraw(1);
        tableDataUser.setRecordsFiltered(selectUser.size());
        tableDataUser.setRecordsTotal(selectUser.size());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String result = gson.toJson(tableDataUser);
        logger.info("result : \n" + result);
        return result;
    }

}
