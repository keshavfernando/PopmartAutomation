package org.example;

import java.io.*;

public class Main {


    public static void main(String[] args) throws Exception {

        String emailFile = "";
        String accountsFile = "";
        String proxyFile = "";


        BufferedReader reader = new BufferedReader(new FileReader(emailFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(accountsFile, true));
        BufferedReader proxyReader = new BufferedReader(new FileReader(proxyFile));

        String emailLine;

        while ((emailLine = reader.readLine()) != null)
        {
            String proxyLine = proxyReader.readLine();
            AccountGen.flow(emailLine, proxyLine);
            writer.write(emailLine + ":password"); //chage
            writer.newLine();
            System.out.println("Account written to file: " + emailLine);
            System.out.println();
        }

        reader.close();
        writer.close();

    }
}
