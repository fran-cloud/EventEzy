package com.eventprogrammer.organizationservice.service;

import com.eventprogrammer.organizationservice.DTO.Email;

public interface EmailSend {
    public void send(String to, Email email);
}
