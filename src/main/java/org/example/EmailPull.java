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

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.getMessages();

            if (messages.length > 0)
            {
                Message latest = messages[messages.length - 1];

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        //finish



        return code;
    }


}
