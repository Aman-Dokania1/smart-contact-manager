package com.smartcontactmanager.dao;

import com.smartcontactmanager.entities.User;

public interface UserDao {

    public User save(User user);

    public User getUserByName(String name);
}
