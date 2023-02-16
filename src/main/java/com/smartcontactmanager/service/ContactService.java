package com.smartcontactmanager.service;

import com.smartcontactmanager.entities.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ContactService {

    public Optional<Contact> findById(int id);

    public Contact save(Contact contact);

    public Page<Contact> getAllContactByUserId(int id, Pageable pageable);
}
