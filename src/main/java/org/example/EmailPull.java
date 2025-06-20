package org.example;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailPull
{

    private Store store;
    private Folder inbox;
    private Session session;


    public void initialize(String username, String password) throws MessagingException
    {

        String host = "imap.gmail.com";


        Properties props = new Properties();
        props.put("mail.store.protocol" , "imap");
        props.put("mail.imap.host", host);
        props.put("mail.imap.port", "993");
        props.put("mail.imap.ssl.enable" , "true");


        this.session = Session.getInstance(props);
        this.store = this.session.getStore("imap");
        this.store.connect(username,password);


        this.inbox = this.store.getFolder("INBOX");
        this.inbox.open(Folder.READ_ONLY);

        System.out.println("IMAP Account Logged in");

    }

    public void closeMail()
    {
        try
        {
            inbox.close(false);
            store.close();
            System.out.println("IMAP Account Logged out");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    public String getCode(String receiveEmail)
    {
        System.out.println("getCode function initializing");
        String expectedSender = "na.support@popmart.com";
        String expectedSubject = "Your verification code:";
        String code = "";
        Pattern codePattern = Pattern.compile("\\b\\d{6}\\b");

        try
        {
            int totalMessages = inbox.getMessageCount();
            int start = Math.max(1, totalMessages - 9);
            Message[] messages = inbox.getMessages(start, totalMessages);
            FetchProfile fetchProfile = new FetchProfile();
            fetchProfile.add(FetchProfile.Item.ENVELOPE);

            for (int i = messages.length -1; i >= 0; i--)
            {
                Message message = messages[i];
                Address[] from = message.getFrom();
                Address[] to = message.getRecipients(Message.RecipientType.TO);
                String subject = message.getSubject();
                System.out.println(subject);

                if (from != null && from.length > 0) {
                    InternetAddress sender = (InternetAddress) from[0];
                    if (sender.getAddress().equalsIgnoreCase(expectedSender)) {

                        if (to != null) {
                            for (Address addr : to) {
                                InternetAddress recipient = (InternetAddress) addr;
                                if (recipient.getAddress().equalsIgnoreCase(receiveEmail)) {
                                    if (subject != null && subject.startsWith(expectedSubject)) {
                                        Matcher matcher = codePattern.matcher(subject);
                                        if (matcher.find()) {
                                            code = matcher.group();
                                            System.out.println("Code found! Code is: " + code);
                                            return code;
                                        }
                                    }
                                }
                            }
                        }

                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return code;
    }

}
