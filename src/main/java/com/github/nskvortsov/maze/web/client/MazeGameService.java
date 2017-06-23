package com.github.nskvortsov.maze.web.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("MazeGameService")
public interface MazeGameService extends RemoteService {
  // Sample interface method of remote interface
  String getMessage(String msg);

  MoveResult startTheGame(List<String> playerNames);

  MoveResult tryToMove(String direction);

  void stopGame();

  MoveResult getLastMove();

  /**
   * Utility/Convenience class.
   * Use MazeGameService.App.getInstance() to access static instance of MazeGameServiceAsync
   */
  public static class App {
    private static MazeGameServiceAsync ourInstance = GWT.create(MazeGameService.class);

    public static synchronized MazeGameServiceAsync getInstance() {
      return ourInstance;
    }
  }
}
