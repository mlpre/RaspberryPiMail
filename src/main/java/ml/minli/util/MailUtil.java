package ml.minli.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;
import java.util.Properties;
import java.util.stream.Collectors;

public class MailUtil {

    public static Properties properties;

    static {
        try {
            properties = new Properties();
            properties.load(new FileReader(FileUtil.getCurrentConfigFilePath("mail.properties")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static MimeMessage getMimeMessage() {
        try {
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(properties.getProperty("email"), properties.getProperty("email.password"));
                }
            });
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(properties.getProperty("email")));
            mimeMessage.setRecipients(Message.RecipientType.TO, properties.getProperty("receiver.email"));
            return mimeMessage;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void sendEmail(String subjectKey, String textKey, String ip) {
        try {
            MimeMessage mimeMessage = getMimeMessage();
            mimeMessage.setSubject(properties.getProperty(subjectKey));
            String text = properties.getProperty(textKey);
            if (text.contains("$ip") && ip != null && !ip.isBlank()) {
                text = text.replace("$ip", ip);
            }
            mimeMessage.setText(text);
            mimeMessage.setSentDate(new Date());
            mimeMessage.saveChanges();
            Transport.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getIpAddress() {
        try {
            return new BufferedReader(new InputStreamReader(new URL(properties.getProperty("email.ip")).openStream())).lines().parallel().collect(Collectors.joining(System.lineSeparator()));
        } catch (Exception e) {
            e.printStackTrace();
            try {
                Thread.sleep(1000 * 10);
                getIpAddress();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }

}
