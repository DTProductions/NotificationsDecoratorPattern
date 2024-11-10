import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailNotificationDecorator extends NotificationDecorator{

    private String senderEmail;
    private String senderName;
    private String senderAppPassword;
    private String recipientEmail;

    public EmailNotificationDecorator(Notification notification, String senderEmail, String senderName, String senderAppPassword, String recipientEmail) {
        super(notification);
        this.senderEmail = senderEmail;
        this.senderName = senderName;
        this.senderAppPassword = senderAppPassword;
        this.recipientEmail = recipientEmail;
    }

    public EmailNotificationDecorator(Notification notification, String senderEmail, String senderAppPassword, String recipientEmail) {
        this(notification, senderEmail, null, senderAppPassword, recipientEmail);
    }

    @Override
    public void send(String message){
        super.send(message);
        sendEmail(message);
    }

    public void sendEmail(String message) {
        try{
            Message emailMessage = new MimeMessage(getEmailSession());
            String senderAddress = senderName == null ? senderEmail : senderName + " <" + senderEmail + ">";

            emailMessage.setSubject("App Notification");
            emailMessage.setFrom(new InternetAddress(senderAddress));
            emailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            emailMessage.setText(message);

            Transport.send(emailMessage);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private Session getEmailSession() {
        return Session.getInstance(EmailNotificationDecorator.getGmailProps(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderAppPassword);
            }
        });
    }

    private static Properties getGmailProps() {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        return prop;
    }
}
