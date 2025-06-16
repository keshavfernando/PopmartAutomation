package org.example;

import jakarta.mail.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailPull
{
    public static String ImapClient(String username, String password, String recieveEmail)
    {
        String code = "";

        String host = "imap.gmail.com";

        String expectedSender = "na.support@popmart.com";

        String expectedSubject = "Your verification code:";

        Pattern codePattern = Pattern.compile("\\b\\d{6}\\b");

        try
        {
            Properties props = new Properties();
            props.put("mail.store.protocol" , "imap");
            props.put("mail.imap.host", host);
            props.put("mail.imap.port", "993");
            props.put("mail.imap.ssl.enable" , "true");

            Session session = Session.getInstance(props);
            Store store = session.getStore("imap");
            store.connect(username, password);

            System.out.println("Logged in");

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

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
                            if (addr.toString().equalsIgnoreCase(recieveEmail))
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


            inbox.close(false);
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return code;
    }


}
