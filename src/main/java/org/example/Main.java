package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {



    public static void main(String[] args) throws Exception {

        String emailFile = "data/emails";
        String accountsFile = "data/accounts"; // Setup file Reading
        String proxyFile = "data/proxies";
        String userAgentFile = "data/userAgents";


        List<String> userAgentLines = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(emailFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(accountsFile, true)); //setup File paths and File Readers
        BufferedReader proxyReader = new BufferedReader(new FileReader(proxyFile));
        BufferedReader userAgentReader = new BufferedReader(new FileReader(userAgentFile));
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        String userAgentLine;
        while ((userAgentLine = userAgentReader.readLine()) != null)
        {
            userAgentLines.add(userAgentLine);
        }

        System.out.println("Welcome to the PopMart Account Gen by KF (Volt Supply)");
        System.out.print("Enter IMAP email: ");
        String ImapEmail = scanner.nextLine();
        System.out.print("\nEnter IMAP password: ");
        String ImapPass = scanner.nextLine();
        System.out.print("\nEnter password to use for accounts: ");
        String acctPassword = scanner.nextLine();

        // Takes input for IMAP email + password

        EmailPull client = new EmailPull();
        client.initialize(ImapEmail, ImapPass);

        // Initializes Imap Client


        String emailLine;

        while ((emailLine = reader.readLine()) != null)
        {
            String proxyLine = proxyReader.readLine();
            String[] proxyParts = proxyLine.split(":");
            String host = proxyParts[0];
            String port = proxyParts[1];
            String userName = proxyParts[2];
            String proxyPass = proxyParts[3];
            String randomUserAgent = userAgentLines.get(random.nextInt(userAgentLines.size()));
            AccountGenNew.flowNew(client, emailLine, host, port, userName, proxyPass, randomUserAgent, acctPassword);
            writer.write(emailLine + ":" + acctPassword);
            writer.newLine();
            System.out.println("Account written to file: " + emailLine);
            System.out.println();
        }

        client.closeMail();
        reader.close();
        writer.close();
        proxyReader.close();
        userAgentReader.close();
    }
}
