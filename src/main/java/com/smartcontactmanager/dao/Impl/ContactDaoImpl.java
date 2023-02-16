package com.smartcontactmanager.dao.Impl;

import com.smartcontactmanager.dao.ContactDao;
import com.smartcontactmanager.entities.Contact;
import com.smartcontactmanager.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ContactDaoImpl implements ContactDao {
    @Autowired
    private ContactRepository contactRepository;
    @Override
    public Optional<Contact> findById(int id) {
        return this.contactRepository.findById(id);
    }

    @Override
    public Contact save(Contact contact) {
        return this.contactRepository.save(contact);
    }

    @Override
    public Page<Contact> getAllContactByUserId(int id, Pageable pageable) {
        return this.contactRepository.getAllContactByUserId(id,pageable);
    }
}
