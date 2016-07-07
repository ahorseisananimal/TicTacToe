package com.nastya.tictactoe;

import java.io.*;
import java.util.List;

public class FileRepository implements Repository {

    public static final String FILE_REPO_TXT = "FileRepo.txt";

    public void saveMatch(MatchInfo matchInfo) {
        System.out.println("saving... todo"); //TODO
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(FILE_REPO_TXT, "UTF-8");
            String fieldInLine = "";
            for (String[] column : matchInfo.field) {
                for (String cell : column) {
                    fieldInLine = fieldInLine + cell;
                }
            }
            writer.println(matchInfo.playersNames[0] + ":" + matchInfo.playersNames[1] + ":" + fieldInLine + ":" +
                    matchInfo.result+":"+ matchInfo.startDate+":"+matchInfo.endDate+":"+matchInfo.turnsCount);
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<MatchInfo> getMatchHistory() {
        try(BufferedReader br = new BufferedReader(new FileReader(FILE_REPO_TXT))) {
            String line = br.readLine();
            while (line != null) {
                String[] parts = line.split(":");
                MatchInfo matchInfo = new MatchInfo();
              //todo  matchInfo.playersNames =
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
