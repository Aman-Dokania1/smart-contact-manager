package com.smartcontactmanager.repository;

import com.smartcontactmanager.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Integer> {

    @Query("select u from USER u where u.email =:email")
    public User getUserByName(@Param("email") String email);
}
