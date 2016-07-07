package com.nastya.tictactoe;

public class MatchInfo {
    public static final int RESULT_DRAW = 0;
    public static final int RESULT_FIRST_WON = 1;
    public static final int RESULT_SECOND_WON = 2;
    public String[][] field;
    public String[] playersNames;
    public int result;
    public long startDate;
    public long endDate;
    public int turnsCount;
}
