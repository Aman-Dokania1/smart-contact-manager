package com.smartcontactmanager.service;

import com.smartcontactmanager.entities.User;

public interface UserService {

    public User save(User user);

    public User getUserByName(String name);
}
