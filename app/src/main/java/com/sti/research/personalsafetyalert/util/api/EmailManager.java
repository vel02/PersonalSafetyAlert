/*
 *
 *   Created by Austria, Ariel Namias on 2/10/21 8:59 AM
 *   Copyright Ⓒ 2021. All rights reserved Ⓒ 2021 https://github.com/vel02
 *   Last modified: 2/10/21 7:01 AM
 *
 *   Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 *   except in compliance with the License. You may obtain a copy of the License at
 *   http://www.apache.org/licenses/LICENS... Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 *    either express or implied. See the License for the specific language governing permissions and
 *    limitations under the License.
 * /
 */

package com.sti.research.personalsafetyalert.util.api;

import static com.sti.research.personalsafetyalert.util.Constants.*;

import android.util.Log;

import com.sti.research.personalsafetyalert.util.Constants;

import java.util.List;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailManager {

//    private static final String email = "personal.safety.alert.bot@gmail.com";
//    private static final String password = "sphhknjqkmtyvzcm";//"jpflvwtjeawbmfeo";

    private MimeMessage message;
    private MimeBodyPart bodyPart;

    private final Multipart multipart;

    public EmailManager() {
        this.multipart = new MimeMultipart();
        getDefaultCommandMap();
    }

    private void getDefaultCommandMap() {
        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
        CommandMap.setDefaultCommandMap(mc);
    }

    private Properties getProperties() {//465,587,25
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.socketFactory.port", "587");
        properties.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); //TLS
        return properties;
    }

    private Session getSessionDefaultInstance() {
        return Session.getDefaultInstance(getProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        MessagingManager.EMAIL_HOST,
                        MessagingManager.EMAIL_PASSWORD);
            }
        });
    }

    public void from() throws MessagingException {
        Session session = getSessionDefaultInstance();
        message = new MimeMessage(session);
        message.setFrom(new InternetAddress(MessagingManager.EMAIL_HOST));
    }

    public void recipients(String recipients) throws MessagingException {
        if (recipients.indexOf(',') > 0)
            message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
        else message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipients));
    }

    public void subject(String subject) throws MessagingException {
        message.setSubject(subject);
    }

    public void body(String body) throws MessagingException {
        bodyPart = new MimeBodyPart();
        bodyPart.setText(body);
        multipart.addBodyPart(bodyPart);
    }

    public void attachment(String path, String fileName) throws MessagingException {
        bodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(path);
        bodyPart.setDataHandler(new DataHandler(source));
        bodyPart.setFileName(fileName);
        multipart.addBodyPart(bodyPart);
    }

    public void attachments(List<String> paths, String fileName) throws MessagingException {
        int count = 1;
        for (String path : paths) {
            String lastSegment = path.substring(path.indexOf(".") + 1);
            Log.e("ATTACHMENT", lastSegment);
            if (lastSegment.equals("mp4")) {
                this.attachment(path, "video_" + count + ".mp4");
            } else this.attachment(path, fileName + "_" + count + ".png");
            count++;
        }
    }

    public synchronized void send() throws MessagingException {
        message.setContent(multipart);
        Transport.send(message);
    }
}
