package com.eventprogrammer.userservice.service;

import com.eventprogrammer.userservice.entity.ConfirmationToken;
import com.eventprogrammer.userservice.repository.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private ConfirmationTokenRepository confirmationTokenRepository;

    void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    public ConfirmationToken getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public  void setConfirmedAt(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token);
        confirmationToken.setConfirmedAt(LocalDateTime.now());
        confirmationTokenRepository.save(confirmationToken);
    }
}
