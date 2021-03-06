package com.nastya.tictactoe;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.text.SimpleDateFormat;
import java.util.*;

public class TicTacToe {

    public static final int GAME_MODE_SINGLE = 1;
    public static final int GAME_MODE_TWO_PLAYERS = 2;

    private static MatchInfo currentMatchInfo;
    private static Repository repository = /*new SqlRepository();*/ new FileRepository();

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    public static void main(String[] args) {
        //startGame(GAME_MODE_SINGLE);
        run();
    }


    private static void run() {
        while (true) {
            System.out.println("Choose your game mode:");
            System.out.println("   1 - Single player");
            System.out.println("   2 - Two players");
            System.out.println("   3 - Online game");
            System.out.println("   4 - Match history");
            System.out.println("   5 - Clear match history");
            System.out.println("   6 - Exit");
            Scanner in = new Scanner(System.in);
            String s = in.next();
            switch (s) {
                case "1":
                    startGame(GAME_MODE_SINGLE);
                    break;
                case "2":
                    startGame(GAME_MODE_TWO_PLAYERS);
                    break;
                case "3":
                    System.out.println("The mode hasn't been implemented yet");
                    break;
                case "4":
                    printMatchHistory();
                    break;
                case "5":
                    clearMatchHistory();
                    break;
                case "6":
                    System.exit(0);
            }
        }
    }

    private static void clearMatchHistory() {
        repository.clearMatchHistory();
    }

    private static void printMatchHistory() {
        List<MatchInfo> matchHistory = repository.getMatchHistory();
        for (MatchInfo matchInfo : matchHistory) {
            System.out.println();
            System.out.println("- - - - - - - - - - - - - - - - - - - - - ");
            System.out.println();
            System.out.println("First player: " + matchInfo.playersNames[0]);
            System.out.println("Second player: " + matchInfo.playersNames[1]);
            printField(matchInfo);
            System.out.print("Result: ");
            switch (matchInfo.result){
                case MatchInfo.RESULT_DRAW:
                    System.out.println("Draw");
                    break;
                case MatchInfo.RESULT_FIRST_WON:
                    System.out.println(matchInfo.playersNames[0] + " won");
                    break;
                case MatchInfo.RESULT_SECOND_WON:
                    System.out.println(matchInfo.playersNames[1] + " won");
                    break;

            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("Start time: " + formatter.format(new Date(matchInfo.startDate)));
            System.out.println("End time: " + formatter.format(new Date(matchInfo.endDate)));
            System.out.println("Total number of turns: "+ matchInfo.turnsCount);

        }
        System.out.println();
        if (!matchHistory.isEmpty()) {
            System.out.println("- - - - - - - - - - - - - - - - - - - - - ");
        }
    }


    private static void startGame(int gameMode) {
        currentMatchInfo = new MatchInfo();
        currentMatchInfo.playersNames = new String[]{"Computer", "Computer"};
        askPlayersName(0);
        if (gameMode == GAME_MODE_TWO_PLAYERS) {
            askPlayersName(1);
        }
        createField();
        printField(currentMatchInfo);
        currentMatchInfo.startDate = System.currentTimeMillis();
        while (true) {
            playersTurn(1);
            currentMatchInfo.turnsCount++;
            if (checkWin() || checkDraw()) {
                break;
            }
            if (gameMode == GAME_MODE_SINGLE) {
                computersTurn();
                currentMatchInfo.turnsCount++;
            } else if (gameMode == GAME_MODE_TWO_PLAYERS) {
                playersTurn(2);
                currentMatchInfo.turnsCount++;
            } else {
                throw new RuntimeException("The game mode is incorrect");
            }
            if (checkWin() || checkDraw()) {
                break;
            }
        }
        currentMatchInfo.endDate = System.currentTimeMillis();
        System.out.println();
        repository.saveMatch(currentMatchInfo);
        currentMatchInfo = null;
    }

    private static void askPlayersName(int playersNumber) {
        System.out.println("Type " + (playersNumber == 0 ? "first" : "second") + " player's name");
        Scanner in = new Scanner(System.in);
        String s = in.next();

        currentMatchInfo.playersNames[playersNumber] = s;

    }

    private static void createField() {
        currentMatchInfo.field = new String[3][3];
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                currentMatchInfo.field[x][y] = "_";
            }
        }
    }

    private static boolean checkDraw() {
        String fieldSum = "";
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                fieldSum = fieldSum + currentMatchInfo.field[x][y];
            }
        }
        boolean isDraw = !fieldSum.contains("_");
        if (isDraw) {
            System.out.println("Draw!");
            currentMatchInfo.result = MatchInfo.RESULT_DRAW;
        }
        return isDraw;
    }

    private static boolean checkWin() {
        String checkHor = "";
        String checkVer = "";
        String checkDiag1 = "";
        String checkDiag2 = "";
        for (int x = 0; x < 3; x++) {
            checkVer = currentMatchInfo.field[x][0] + currentMatchInfo.field[x][1] + currentMatchInfo.field[x][2];
            checkHor = currentMatchInfo.field[0][x] + currentMatchInfo.field[1][x] + currentMatchInfo.field[2][x];
            if (checkHor.equals("XXX") || checkVer.equals("XXX")) {
                System.out.println(currentMatchInfo.playersNames[0] + " won");
                currentMatchInfo.result = MatchInfo.RESULT_FIRST_WON;
                return true;
            }
            if (checkHor.equals("OOO") || checkVer.equals("OOO")) {
                System.out.println(currentMatchInfo.playersNames[1] + " won");
                currentMatchInfo.result = MatchInfo.RESULT_SECOND_WON;
                return true;
            }
        }
        checkDiag1 = currentMatchInfo.field[0][0] + currentMatchInfo.field[1][1] + currentMatchInfo.field[2][2];
        checkDiag2 = currentMatchInfo.field[2][0] + currentMatchInfo.field[1][1] + currentMatchInfo.field[0][2];
        if (checkDiag1.equals("XXX") || checkDiag2.equals("XXX")) {
            System.out.println(currentMatchInfo.playersNames[0] + " won");
            currentMatchInfo.result = MatchInfo.RESULT_FIRST_WON;
            return true;
        }
        if (checkDiag1.equals("OOO") || checkDiag2.equals("OOO")) {
            System.out.println(currentMatchInfo.playersNames[1] + " won");
            currentMatchInfo.result = MatchInfo.RESULT_SECOND_WON;
            return true;
        }
        return false;
    }

    private static void printField(MatchInfo currentMatchInfo) {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                System.out.print(currentMatchInfo.field[x][y] + " ");
            }
            System.out.println();

        }
    }

    private static void playersTurn(int playerNumber) {
        if (playerNumber == 1) {
            System.out.println("it's first player's turn, input cell's coordinate");
        } else {
            System.out.println("it's second player's turn, input cell's coordinate");
        }
        int x = 0;
        int y = 0;
        while (true) {
            while (true) {
                Scanner in = new Scanner(System.in);
                String s = in.next();
                if (s.length() != 2) {
                    System.out.println("Invalid coordinate, please, type in again");
                    continue;
                }
                try {
                    x = Integer.valueOf(s.substring(0, 1));
                    y = Integer.valueOf(s.substring(1, 2));
                } catch (Exception e) {
                    System.out.println("Please, use numbers only " + e.getClass().getCanonicalName());
                    continue;
                }

                if (x < 0 || x > 2 || y < 0 || y > 2) {
                    System.out.println("Invalid coordinate, please, type in again");
                } else {
                    break;
                }
            }

            if (currentMatchInfo.field[x][y].equals("_")) {
                if (playerNumber == 1) {
                    currentMatchInfo.field[x][y] = "X";
                } else {
                    currentMatchInfo.field[x][y] = "O";
                }
                break;
            } else {
                System.out.println("This cell is occupied, choose another one");
            }

        }

        printField(currentMatchInfo);

    }

    private static void computersTurn() {
        System.out.println("it's computer's turn");
        while (true) {
            int x = (int) (Math.random() * 3);
            int y = (int) (Math.random() * 3);
            if (currentMatchInfo.field[x][y].equals("_")) {
                currentMatchInfo.field[x][y] = "O";
                break;
            }
        }
        printField(currentMatchInfo);
    }

      /*   for (int i = 0; i < 10; i++) {
            System.out.print("\rlol " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
          System.out.print("%|=>");
        for (int i = 0; i < 70; i++) {
            System.out.print("\b=>");
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println();
        *//*   private static void collectionExample() {
           List<String> list = new ArrayList<>();
           list.add("lol");
           list.add("lol");
           list.add("lol2");

           list.get(0);//lol
           list.get(2);//lol2


           Set<String> set = new HashSet<>();
           set.add("lol");
           set.add("lol");
           set.add("lol2");

           set.contains("lol"); //true
           set.containsAll(list); //true


           Map<String, String> map = new HashMap<>();
           map.put("id_1", "Vasya");
           map.put("id_1", "Ivan");
           map.put("id_2", "Petya");
           map.get("id_2"); // Petya
       }
      */

}
