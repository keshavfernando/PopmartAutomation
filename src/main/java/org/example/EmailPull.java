package org.example;

import jakarta.mail.*;
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
        String code = "";

        String expectedSender = "na.support@popmart.com";

        String expectedSubject = "Your verification code:";

        Pattern codePattern = Pattern.compile("\\b\\d{6}\\b");

        try
        {
            Message[] messages = inbox.getMessages();

            for (int i = messages.length -1; i >= 0;i--)
            {
                Message message = messages[i];
                Address[] from = message.getFrom();

                if (from != null && from[0].toString().equalsIgnoreCase(expectedSender))
                {
                    Address[] to = message.getRecipients(Message.RecipientType.TO);
                    if (to != null)
                    {
                        for (Address addr : to)
                        {
                            if (addr.toString().equalsIgnoreCase(receiveEmail))
                            {
                                String subject = message.getSubject();

                                if (subject != null && subject.startsWith(expectedSubject))
                                {
                                    Matcher matcher = codePattern.matcher(subject);
                                    if (matcher.find())
                                    {
                                        inbox.close(false);
                                        store.close();
                                        code = matcher.group();
                                        System.out.println("Code is: " + code);
                                        return code;
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
