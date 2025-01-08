package Utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.*;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.monte.media.Registry;

import javax.activation.*;


public class MonitoringMail {

	public void sendMail(String mailServer, String from, String[] to, String subject, String messageBody) throws MessagingException, AddressException
	{
		boolean debug = false;
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.starttls.required", "true");
		props.put("mail.smtp.ssl.protocols", "TLSv1.2");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.EnableSSL.enable","true");
		props.put("mail.smtp.auth", "true");

		props.put("mail.smtp.host", mailServer); 
		props.put("mail.debug", "true");
		
	     props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");   
	     props.setProperty("mail.smtp.socketFactory.fallback", "false");   
	     props.setProperty("mail.smtp.port", "465");   
	     props.setProperty("mail.smtp.socketFactory.port", "465"); 

		
		  Authenticator auth = new SMTPAuthenticator();
		    Session session = Session.getDefaultInstance(props, auth);

		    session.setDebug(debug);
		    session.setDebug(true);
		
		try
		{
			
			
			Transport bus = session.getTransport("smtp");
			bus.connect();
            Message message = new MimeMessage(session);
        
         //X-Priority values are generally numbers like 1 (for highest priority), 3 (normal) and 5 (lowest).
            
             message.addHeader("X-Priority", "1");
             message.setFrom(new InternetAddress(from));
             InternetAddress[] addressTo = new InternetAddress[to.length];
             for (int i = 0; i < to.length; i++)
      		 addressTo[i] = new InternetAddress(to[i]);
             message.setRecipients(Message.RecipientType .TO, addressTo);
             message.setSubject(subject); 
             BodyPart body = new MimeBodyPart();

            // body.setText(messageBody);
            body.setContent(messageBody,"text/html");

             BodyPart attachment = new MimeBodyPart();   
             BodyPart Rcord = new MimeBodyPart();           
             attachment.setText("Please find the TestNG report attached.");
             String filePath = "./reports/Report.html";
             DataSource source = new FileDataSource(filePath);         
             attachment.setDataHandler(new DataHandler(source));   
             attachment.setFileName("Report.html");           
              
//             String VidPath = "./test-recordings/login.avi";
//             DataSource source1 = new FileDataSource(VidPath);
//             Rcord.setDataHandler(new DataHandler(source1));
//             Rcord.setFileName("Recording Vedio");
             
             Multipart multipart = new MimeMultipart();
             multipart.addBodyPart(body);
             multipart.addBodyPart(attachment);              
//             multipart.addBodyPart(Rcord); 
             message.setContent(multipart);
             Transport.send(message);
             System.out.println("Sucessfully Sent mail to All Users");    
         	 bus.close();
    		
		}
		catch (MessagingException mex)
		{
            mex.printStackTrace();
        }		
	
	}
	private class SMTPAuthenticator extends javax.mail.Authenticator
	{

	    public PasswordAuthentication getPasswordAuthentication()
	    {
	        String username = MailConfig.from;
	        String password = MailConfig.password;
	        return new PasswordAuthentication(username, password);
	    }
	}
	
}
