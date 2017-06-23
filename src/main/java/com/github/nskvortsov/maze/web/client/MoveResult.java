package com.github.nskvortsov.maze.web.client;

import java.io.Serializable;

/**
 * Created by Nikita.Skvortsov
 * date: 22.06.2017.
 */
public class MoveResult implements Serializable {
  private String lastMove;
  private String lastMoveResult;
  private String movedPlayerName;
  private String nextPlayerName;

  public MoveResult() {
    lastMove = null;
    lastMoveResult = null;
    movedPlayerName = null;
    nextPlayerName = null;
  }

  public MoveResult(String lastMove, String lastMoveResult, String movedPlayerName, String nextPlayerName) {

    this.lastMove = lastMove;
    this.lastMoveResult = lastMoveResult;
    this.movedPlayerName = movedPlayerName;
    this.nextPlayerName = nextPlayerName;
  }

  public String getLastMove() {
    return lastMove;
  }

  public String getLastMoveResult() {
    return lastMoveResult;
  }

  public String getNextPlayerName() {
    return nextPlayerName;
  }

  public String getMovedPlayerName() {
    return movedPlayerName;
  }
}
