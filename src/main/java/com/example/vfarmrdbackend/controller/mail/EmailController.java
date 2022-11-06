package com.example.vfarmrdbackend.controller.mail;

import com.example.vfarmrdbackend.entity.EmailDetails;
import com.example.vfarmrdbackend.service.email.EmailService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path = "/api")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/sendMail")
    public boolean sendMail(@RequestBody EmailDetails details){
        boolean status = emailService.sendSimpleMail(details);
        return  status;
    }
}
