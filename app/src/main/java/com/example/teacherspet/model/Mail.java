package com.example.teacherspet.model;


import java.util.Date;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import android.os.AsyncTask;

/**
 * Creates a main client to send email without any user interaction.
 * 
 * Reference tutorial from http://www.jondev.net/articles/Sending_Emails_without_User_Intervention_(no_Intents)_in_Android
 * @author Johnathon Malott Kevin James
 * @version 11/11/2014
 */
public class Mail extends javax.mail.Authenticator { 
	//Username and password of gmail account
    private String user; 
    private String pass; 
    //Email address of who to go to and who it cam from
	private String[] to; 
	private String from; 
	//Port that gmail is listening on
	private String port; 
	private String sport; 
	//Who is sending email
	private String host; 
	//Information for email
	private String subject; 
	private String body; 
	//smtp authentication
	private boolean auth; 
	 //Use to debugging the mail client
	private boolean debuggable; 
	//Fixes javamail with mailcap
	private Multipart multipart; 


	 /**
	  * Sets up default values for mail client.
	  */
	 public Mail() { 
	
	   host = "smtp.gmail.com"; 
	   port = "465";
	   sport = "465";  
	   user = "teacherspetwcu@gmail.com";  
	   pass = "teacherspet"; 
	   from = "teacherspetwcu@gamil.com"; 
	   subject = ""; 
	   body = ""; 
	   debuggable = false;
	   auth = true; 
	
	   multipart = new MimeMultipart(); 
	
	   // Javamail need to help with Mailcap handler. 
	   MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap(); 
	   mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html"); 
	   mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml"); 
	   mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain"); 
	   mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed"); 
	   mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822"); 
	   CommandMap.setDefaultCommandMap(mc); 
	 } 
	
	 /**
	  * Sets up default values for mail client and allows user to pass in user name and password.
	  * 
	  * @param user Email Username
	  * @param pass Email Password
	  */
	 public Mail(String user, String pass) { 
	   this(); 
	
	  //Sets up user/pass from user input
	   this.user = user; 
	   this.pass = pass; 
	 } 

	 /**
	  * Checks to see if all information was filled out and then sends mail.
	  * 
	  * @return True is message was sent
	  * @throws Exception
	  */
	 public boolean send() throws Exception { 
	   Properties props = setProperties(); 
	
	   if(!user.equals("") && !pass.equals("") && to.length > 0 && 
			   !from.equals("") && !subject.equals("") && !body.equals("")) { 
		   //Sets up connection session and who the email is going to
	     Session session = Session.getInstance(props, this); 
	     MimeMessage msg = new MimeMessage(session); 
	     msg.setFrom(new InternetAddress(from)); 
	      
	     //Incase it is sent to more than one email
	     InternetAddress[] addressTo = new InternetAddress[to.length]; 
	     for (int i = 0; i < to.length; i++) { 
	       addressTo[i] = new InternetAddress(to[i]); 
	     } 
	       msg.setRecipients(MimeMessage.RecipientType.TO, addressTo); 
	
	     msg.setSubject(subject); 
	     msg.setSentDate(new Date()); 
	
	     // setup message body 
	     BodyPart messageBodyPart = new MimeBodyPart(); 
	     messageBodyPart.setText(body); 
	     multipart.addBodyPart(messageBodyPart); 
	
	     // Put parts in message 
	     msg.setContent(multipart); 
	     // send email 
	     Transport transport = session.getTransport("smtp");
	     transport.connect(host, 465, user, pass);
	     Transport.send(msg); 
	
	     return true; 
	   } else { 
	     return false; 
	   } 
	 } 
	 

 /**
  * For password protection.
  */
 @Override 
 public PasswordAuthentication getPasswordAuthentication() { 
   return new PasswordAuthentication(user, pass); 
 } 

 /**
  * Set up default server which is the gmail.
  * 
  * @return
  */
 private Properties setProperties() { 
   Properties props = new Properties(); 

   props.put("mail.smtp.host", host); 

   if(debuggable) { 
     props.put("mail.debug", "true"); 
   } 

   if(auth) { 
     props.put("mail.smtp.auth", "true"); 
   } 

   props.put("mail.smtp.port", port); 
   props.put("mail.smtp.socketFactory.port", sport); 
   props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
   props.put("mail.smtp.socketFactory.fallback", "false"); 

   return props; 
 } 
 
 public void sendMail(){
	 new SendTask().execute();
 }

 // the getters and setters 
    public void setTo(String[] to) { 
      this.to =  to; 
    } 

    public void setFrom(String from) { 
      this.from = from; 
    }  
 
    public void setSubject(String subject) { 
	   this.subject = subject; 
	} 	
    public void setBody(String body) { 
 	   this.body = body; 
 	}
    
    /**
	 * Sends email to user updating them on their username and password
	 */
	 public class SendTask extends AsyncTask<String, String, String> {
	
		 //Sends mail to mail server
		 protected String doInBackground(String... args) {
		     try {
		    	 //sendMail();
		         send();
		     } catch (Exception e) {
		    	 e.printStackTrace();
		     }
		     return "";
		 }
	 }
} 