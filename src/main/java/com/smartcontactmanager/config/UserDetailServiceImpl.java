package com.smartcontactmanager.config;

import com.smartcontactmanager.repository.UserRepository;
import com.smartcontactmanager.entities.User;
import com.smartcontactmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=this.userService.getUserByName(username);
        if(user==null)
            throw new UsernameNotFoundException("could not find user !!");

        CustomUserDetails customUserDetails=new CustomUserDetails(user);
        return customUserDetails;
    }
}
