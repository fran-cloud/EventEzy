package com.eventprogrammer.userservice.service;

import com.eventprogrammer.userservice.DTO.Email;

public interface EmailSend {
    public void send(String to, Email email);
}
