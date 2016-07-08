package com.nastya.tictactoe;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileRepository implements Repository {

    public static final String FILE_REPO_TXT = "FileRepo.txt";

    public void saveMatch(MatchInfo matchInfo) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(FILE_REPO_TXT, true)));
            String fieldInLine = "";
            for (String[] column : matchInfo.field) {
                for (String cell : column) {
                    fieldInLine = fieldInLine + cell;
                }
            }
            writer.println(matchInfo.playersNames[0] + ":" + matchInfo.playersNames[1] + ":" + fieldInLine + ":" +
                    matchInfo.result + ":" + matchInfo.startDate + ":" + matchInfo.endDate + ":" + matchInfo.turnsCount);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<MatchInfo> getMatchHistory() {
        List<MatchInfo> results = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_REPO_TXT))) {
            String line = br.readLine();
            while (line != null) {
                String[] parts = line.split(":");
                MatchInfo matchInfo = new MatchInfo();
                matchInfo.playersNames = new String[]{parts[0], parts[1]};
                matchInfo.field = new String[3][3];
                int k = 0;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        matchInfo.field[i][j] = String.valueOf(parts[2].charAt(k));
                        k++;
                    }
                }
                matchInfo.result = Integer.valueOf(parts[3]);
                matchInfo.startDate = Long.valueOf(parts[4]);
                matchInfo.endDate = Long.valueOf(parts[5]);
                matchInfo.turnsCount = Integer.valueOf(parts[6]);
                results.add(matchInfo);
                line = br.readLine();
            }
        } catch (IOException e) {
            if (e instanceof FileNotFoundException) {
                System.out.println("The history is empty");
            } else {
                e.printStackTrace();
            }
        }
        return results;
    }

    @Override
    public void clearMatchHistory() {
        //noinspection ResultOfMethodCallIgnored
        new File(FILE_REPO_TXT).delete();
        System.out.println();
        System.out.println("Match history has been cleaned");
        System.out.println();
    }
}
