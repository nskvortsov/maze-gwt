package com.github.nskvortsov.maze.web.server;

import com.github.nskvortsov.maze.Direction;
import com.github.nskvortsov.maze.Game;
import com.github.nskvortsov.maze.Maze;
import com.github.nskvortsov.maze.Result;
import com.github.nskvortsov.maze.web.client.MazeGameService;
import com.github.nskvortsov.maze.web.client.MoveResult;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import kotlin.Pair;

import java.util.List;

public class MazeGameServiceImpl extends RemoteServiceServlet implements MazeGameService {

  private static final String CURRENT_GAME_ATTR = "currentGame";
  private static final String LAST_MOVE_ATTR = "lastMove";
  // Implementation of sample interface method
  public String getMessage(String msg) {
    return "Client said: \"" + msg + "\"<br>Server answered: \"Hi!\"";
  }

  @Override
  public MoveResult startTheGame(List<String> playerNames) {
    System.out.println("Starting the games");
    Maze maze = new Maze(3, new Pair<>(0, 3));
    maze.putTreasure(2, 2);

    String[] others = new String[0];
    if (playerNames.size() == 3) {
      others = new String[]{ playerNames.get(2) };
    } else if (playerNames.size() > 3) {
      others = (String[]) playerNames.subList(2, playerNames.size() - 1).toArray();
    }

    Game game = new Game(maze, playerNames.get(0), playerNames.get(1), others).start();
    getThreadLocalRequest().getSession().setAttribute(CURRENT_GAME_ATTR, game);

    final MoveResult moveResult = new MoveResult(null, null, null, game.currentPlayer.getName());
    getThreadLocalRequest().getSession().setAttribute(LAST_MOVE_ATTR, moveResult);

    return moveResult;
  }

  @Override
  public MoveResult tryToMove(String d) {
    System.out.println("try to move in direction = " + d);
    Game game = (Game) getThreadLocalRequest().getSession().getAttribute(CURRENT_GAME_ATTR);
    if (game == null) {
      throw new IllegalStateException("Game was not started!");
    }
    final String movedPlayer = game.currentPlayer.getName();
    final Result result = game.tryMove(Direction.valueOf(d));
    final String nextPlayer = game.currentPlayer.getName();

    return new MoveResult(d, result.toString(), movedPlayer, nextPlayer);
  }

  @Override
  public void stopGame() {
    getThreadLocalRequest().getSession().removeAttribute(CURRENT_GAME_ATTR);
    getThreadLocalRequest().getSession().removeAttribute(LAST_MOVE_ATTR);
  }

  @Override
  public MoveResult getLastMove() {
    return (MoveResult) getThreadLocalRequest().getSession().getAttribute(LAST_MOVE_ATTR);
  }
}