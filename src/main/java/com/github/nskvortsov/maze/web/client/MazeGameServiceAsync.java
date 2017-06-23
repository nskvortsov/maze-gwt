package com.github.nskvortsov.maze.web.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface MazeGameServiceAsync {
  void getMessage(String msg, AsyncCallback<String> async);

  void startTheGame(List<String> playerNames, AsyncCallback<MoveResult> async);

  void tryToMove(String direction, AsyncCallback<MoveResult> async);

  void stopGame(AsyncCallback<Void> async);

  void getLastMove(AsyncCallback<MoveResult> async);
}
