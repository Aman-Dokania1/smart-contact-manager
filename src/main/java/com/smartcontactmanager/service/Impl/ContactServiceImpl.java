package com.smartcontactmanager.service.Impl;

import com.smartcontactmanager.dao.ContactDao;
import com.smartcontactmanager.entities.Contact;
import com.smartcontactmanager.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    private ContactDao contactDao;
    @Override
    public Optional<Contact> findById(int id) {
        return this.contactDao.findById(id);
    }

    @Override
    public Contact save(Contact contact) {
        return this.contactDao.save(contact);
    }

    @Override
    public Page<Contact> getAllContactByUserId(int id, Pageable pageable) {
        return this.contactDao.getAllContactByUserId(id,pageable);
    }
}
