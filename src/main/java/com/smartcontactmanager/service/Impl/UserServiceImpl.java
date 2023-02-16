package com.smartcontactmanager.service.Impl;

import com.smartcontactmanager.dao.UserDao;
import com.smartcontactmanager.entities.User;
import com.smartcontactmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User save(User user) {
        return userDao.save(user);
    }

    @Override
    public User getUserByName(String name) {
        return this.userDao.getUserByName(name);
    }
}
