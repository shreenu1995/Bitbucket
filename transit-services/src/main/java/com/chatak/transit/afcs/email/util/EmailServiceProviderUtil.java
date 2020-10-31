package com.chatak.transit.afcs.email.util;


import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Email Utility class to configure the Source Email Settings to send email
 */
public class EmailServiceProviderUtil {

  private static Logger logger = LoggerFactory.getLogger(EmailServiceProviderUtil.class);

  private static final String SMTP_PORT = "mail.smtp.port";

  private static Authenticator authenticator = null;

  private static Properties mailProps = null;
  
  private EmailServiceProviderUtil() {}

  public static Properties getEmailMessageProperty() {
    return mailProps;
  }

  /**
   * Method to get Email Authenticator
   * @return
   */
  public static Authenticator getEmailAuthenticator(String userName,String password, String smptHost, String smtpStarttlsEnable, String smptPort) {
    try {
      if (null == authenticator) {
        authenticator = new Authenticator() {
          @Override
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(userName, password);
          }
        };
        mailProps = new Properties();
        
        mailProps.put("mail.transport.protocol", "smtp");
        mailProps.put("mail.smtp.socketFactory.port",smptPort);
        mailProps.put("mail.smtp.socketFactory.fallback", "true");
        
        mailProps.put("mail.smtp.host", smptHost);
        mailProps.put("mail.smtp.auth", "true");
        mailProps.put("mail.smtp.ssl.trust", "true");
        
        mailProps.put("mail.smtp.starttls.enable", smtpStarttlsEnable == null ? "false" : smtpStarttlsEnable);
        mailProps.put(SMTP_PORT, smptPort);
      }

    } catch (Exception e) {
    	logger.error("EmailServiceProviderUtil:: getEmailAuthenticator method:Exception", e);
    }
    return authenticator;
  }

}
