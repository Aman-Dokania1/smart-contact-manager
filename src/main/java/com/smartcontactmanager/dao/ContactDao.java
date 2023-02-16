package com.smartcontactmanager.dao;

import com.smartcontactmanager.entities.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ContactDao {
    public Optional<Contact> findById(int id);

    public Contact save(Contact contact);

    public Page<Contact> getAllContactByUserId(int id, Pageable pageable);
}
