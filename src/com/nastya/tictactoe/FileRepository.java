package com.nastya.tictactoe;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class FileRepository implements Repository {

    public void saveMatch(MatchInfo matchInfo) {
        System.out.println("saving... todo"); //TODO
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("FileRepo.txt", "UTF-8");
            writer.println("Hello, ");
            writer.println("World");
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
