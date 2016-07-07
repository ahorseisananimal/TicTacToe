package com.nastya.tictactoe;

import java.util.List;

public interface Repository {
    void saveMatch(MatchInfo matchInfo);

    List<MatchInfo> getMatchHistory();
}
