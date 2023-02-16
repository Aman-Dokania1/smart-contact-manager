package com.smartcontactmanager.dao.Impl;

import com.smartcontactmanager.dao.UserDao;
import com.smartcontactmanager.entities.User;
import com.smartcontactmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public User getUserByName(String name) {
        return this.userRepository.getUserByName(name);
    }
}
