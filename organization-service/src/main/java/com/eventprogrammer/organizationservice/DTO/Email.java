package com.eventprogrammer.organizationservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Email {

    private String msgBody;

    private String subject;

}
