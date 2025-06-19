package org.example;

import java.io.*;

public class Main {


    public static void main(String[] args) throws Exception {

        String emailFile = "";
        String accountsFile = "";


        BufferedReader reader = new BufferedReader(new FileReader(emailFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(accountsFile, true));

        String line;

        while ((line = reader.readLine()) != null)
        {
            AccountGen.flow(line);
            writer.write(line + ":password");
            writer.newLine();
            System.out.println("Account written to file: " + line);
            System.out.println();
        }

        reader.close();
        writer.close();


    }
}
