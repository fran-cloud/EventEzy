package com.eventprogrammer.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(value = "tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfirmationToken {

    @Id
    private String id;

    private String token;

    private LocalDateTime createdAt;

    private LocalDateTime expiresAt;

    private LocalDateTime confirmedAt;
    private User user;


    public ConfirmationToken(String token,
                             LocalDateTime createdAt,
                             LocalDateTime expiresAt,
                             User user) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.user = user;
    }
}
