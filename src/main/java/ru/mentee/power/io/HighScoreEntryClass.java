package ru.mentee.power.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Обычный класс для хранения рекорда
class HighScoreEntryClass {
  private final String playerName;
  private final int score;


  public HighScoreEntryClass(String playerName, int score) {
    this.playerName = playerName;
    this.score = score;
  }

  public String getPlayerName() {return playerName;}

  public int getScore() {return score;}

  @Override
  public String toString() {
    return "HighScoreEntryClass{" + "playerName='" + playerName + '\'' + ", score=" + score + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    HighScoreEntryClass that = (HighScoreEntryClass) o;
    return score == that.score && Objects.equals(playerName, that.playerName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(playerName, score);
  }
}
