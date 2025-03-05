package com.sygec.sygec.Api;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sygec.sygec.enume.EnumRole;
import com.sygec.sygec.model.Admin;
import com.sygec.sygec.model.DemandeConge;
import com.sygec.sygec.repository.AdminRepository;
import com.sygec.sygec.repository.DemandeCongeRepository;



@Service
public class EmailServiceImpl {

	@Autowired
    private JavaMailSender emailSender;
	@Autowired
	private AdminRepository adminRepository;
	
	Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
	
	@Autowired
	private DemandeCongeRepository demandeCongeRepository;

    public void sendSimpleMessage(String email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("amaracamara52@gmail.com");
        message.setTo(email);
        message.setSubject("hello");
        message.setText("hi");
        emailSender.send(message);
    }
    
    public void sendHtmlBeneficiaireToChef(String email) throws MessagingException {
    	MimeMessage mimeMessage = emailSender.createMimeMessage();
    	MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
    	String htmlMsg = "<!DOCTYPE html>\n"
    			+ "<html lang=\"en\">\n"
    			+ "<head>\n"
    			+ "    <meta charset=\"UTF-8\">\n"
    			+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
    			+ "    <title>SYGEC-APPPLICATION</title>\n"
    			+ "</head>\n"
    			+ "<body>\n"
    			+ "   <p>Salut Mr/Mme, ce message provient de l'application SYGEC. Vous avez une demande de conge en attente, veuillez vous connectez et traiter cette demande. Merci et bonne journée de travail</p>\n"
    			+ "</body>\n"
    			+ "</html>";
    	//mimeMessage.setContent(htmlMsg, "text/html"); /** Use this or below line **/
    	helper.setText(htmlMsg, true); // Use this or above line.
    	//helper.setTo("someone@abc.example");
    	helper.setSubject("This is the test message for testing gmail smtp server using spring mail");
    	helper.setFrom("amaracamara52@gmail.com");
    	helper.setTo(email);
    	emailSender.send(mimeMessage);
    }
    
    
    public void sendHtmlDGtoBenficiaire(String email) throws MessagingException {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Admin admin = adminRepository.findByEmail(auth.getName()).orElseThrow(null);
		String role = admin.getRoles().get(0).toString();
    	MimeMessage mimeMessage = emailSender.createMimeMessage();
    	MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
    	String htmlMsg = "<!DOCTYPE html>\n"
    			+ "<html lang=\"en\">\n"
    			+ "<head>\n"
    			+ "    <meta charset=\"UTF-8\">\n"
    			+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
    			+ "    <title>SYGEC-APPPLICATION</title>\n"
    			+ "</head>\n"
    			+ "<body>\n"
    			+ "    <p>Salut Mr/Mme, ce message provient de l'application SYGEC. Votre demande de conge à été approuver par le "+role+" . Merci et bonne journée de travail</p>\n"
    			+ "</body>\n"
    			+ "</html>";
    	//mimeMessage.setContent(htmlMsg, "text/html"); /** Use this or below line **/
    	helper.setText(htmlMsg, true); // Use this or above line.
    	//helper.setTo("someone@abc.example");
    	helper.setSubject("This is the test message for testing gmail smtp server using spring mail");
    	helper.setFrom("amaracamara52@gmail.com");
    	helper.setTo(email);
    	emailSender.send(mimeMessage);
    }
    
    public void sendHtmlDGtoBenficiaireRejetter(String email) throws MessagingException {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Admin admin = adminRepository.findByEmail(auth.getName()).orElseThrow(null);
		String role = admin.getRoles().get(0).toString();
    	MimeMessage mimeMessage = emailSender.createMimeMessage();
    	MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
    	String htmlMsg = "<!DOCTYPE html>\n"
    			+ "<html lang=\"en\">\n"
    			+ "<head>\n"
    			+ "    <meta charset=\"UTF-8\">\n"
    			+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
    			+ "    <title>SYGEC-APPPLICATION</title>\n"
    			+ "</head>\n"
    			+ "<body>\n"
    			+ "    <p>Salut Mr/Mme, ce message provient de l'application SYGEC. Votre demande de conge à été Rejetter par le "+role+" , pour plus d'information veuillez contacter DRH. Merci et bonne journée de travail</p>\n"
    			+ "</body>\n"
    			+ "</html>";
    	//mimeMessage.setContent(htmlMsg, "text/html"); /** Use this or below line **/
    	helper.setText(htmlMsg, true); // Use this or above line.
    	//helper.setTo("someone@abc.example");
    	helper.setSubject("This is the test message for testing gmail smtp server using spring mail");
    	helper.setFrom("amaracamara52@gmail.com");
    	helper.setTo(email);
    	emailSender.send(mimeMessage);
    }
    
    public void sendHtmlDGtoBenficiaireAnnuler(String email) throws MessagingException {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Admin admin = adminRepository.findByEmail(auth.getName()).orElseThrow(null);
		String role = admin.getRoles().get(0).toString();
    	MimeMessage mimeMessage = emailSender.createMimeMessage();
    	MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
    	String htmlMsg = "<!DOCTYPE html>\n"
    			+ "<html lang=\"en\">\n"
    			+ "<head>\n"
    			+ "    <meta charset=\"UTF-8\">\n"
    			+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
    			+ "    <title>SYGEC-APPPLICATION</title>\n"
    			+ "</head>\n"
    			+ "<body>\n"
    			+ "    <p>Salut Mr/Mme, ce message provient de l'application SYGEC. Votre demande de conge à été Annuler par le "+role+" , pour plus d'information veuillez contacter DRH. Merci et bonne journée de travail</p>\n"
    			+ "</body>\n"
    			+ "</html>";
    	//mimeMessage.setContent(htmlMsg, "text/html"); /** Use this or below line **/
    	helper.setText(htmlMsg, true); // Use this or above line.
    	//helper.setTo("someone@abc.example");
    	helper.setSubject("This is the test message for testing gmail smtp server using spring mail");
    	helper.setFrom("amaracamara52@gmail.com");
    	helper.setTo(email);
    	emailSender.send(mimeMessage);
    }
    

   

	public void sendHtml(String email) {
		// TODO Auto-generated method stub
		MimeMessage mimeMessage = emailSender.createMimeMessage();
    	MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
    	String htmlMsg = "<!DOCTYPE html>\n"
    			+ "<html lang=\"en\">\n"
    			+ "<head>\n"
    			+ "    <meta charset=\"UTF-8\">\n"
    			+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
    			+ "    <title>SYGEC-APPPLICATION</title>\n"
    			+ "</head>\n"
    			+ "<body>\n"
    			+ "    <p>BIENVENU SUR LA PLATFORM SYGEC</p>\n"
    			+ "</body>\n"
    			+ "</html>";
    	//mimeMessage.setContent(htmlMsg, "text/html"); /** Use this or below line **/
    	try {
			helper.setText(htmlMsg, true);
			helper.setSubject("This is the test message for testing gmail smtp server using spring mail");
	    	helper.setFrom("amaracamara52@gmail.com");
	    	helper.setTo(email);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Use this or above line.
    	//helper.setTo("someone@abc.example");
    	
    	emailSender.send(mimeMessage);
	}
}
