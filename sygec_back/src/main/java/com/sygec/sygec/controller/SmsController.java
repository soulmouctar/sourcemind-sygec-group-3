package com.sygec.sygec.controller;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sygec.sygec.Api.ApiSms;
import com.sygec.sygec.Api.EmailServiceImpl;
import com.sygec.sygec.dto.OrangeTokenDto;




@RestController
public class SmsController {
	
	@Autowired
	private ApiSms apiSms;
	
	@Autowired
	private EmailServiceImpl emailServiceImpl;
	
	@GetMapping("/sendSms/{tel}")
	public void senderSms(@PathVariable String tel) {
		OrangeTokenDto orangeTokenDto = apiSms.generateToken();
		if(orangeTokenDto.getAccessToken() != null) {
			String telephone = "+224"+tel;
			String sms = "Salut Mr/Mme, ce message provient de l'application SYGEM. Vous avez une demande en attente, veuillez vous connectez enfin d'approuver. Merci et bonne journée de travail";
			String message = "Bienvenue Mr/Mme tel "+tel;
			apiSms.sendSms(telephone, sms, orangeTokenDto.getAccessToken());
			
		}
	}
	@GetMapping("/sendEmail/{email}")
	public void sendMail(@PathVariable String email) {
		emailServiceImpl.sendHtml(email);
	}

}
