package com.github.nskvortsov.maze.web.client;


import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Nikita.Skvortsov
 * date: 22.06.2017.
 */
class PlayWidget {

  private Label moveLabel = new Label("Move info will go here");
  private Label nextPlayerPanel = new Label("next player name");
  private Panel buttonsPanel = new HorizontalPanel();

  public PlayWidget(final PanelDirector director) {
    Panel playPanel = new VerticalPanel();
    director.setPlayPanel(playPanel);
    Stream.of("UP","DOWN", "LEFT", "RIGHT").forEach ((String name) -> {
      final Button directionButton = new Button(name);
      directionButton.addClickHandler(event -> MazeGameService.App.getInstance().tryToMove(directionButton.getText(),
          new AsyncCallback<MoveResult>() {
            @Override
            public void onFailure(Throwable caught) {
              Window.alert("Error from server: " + caught.getClass().getName() + ": " + caught.getMessage());
            }

            @Override
            public void onSuccess(MoveResult result) {
              setLastMove(result);
            }
          }));
      buttonsPanel.add(directionButton);
    });
    playPanel.add(moveLabel);
    playPanel.add(nextPlayerPanel);
    playPanel.add(buttonsPanel);
    final Button stop = new Button("Stop");
    playPanel.add(stop);
    stop.addClickHandler(event -> MazeGameService.App.getInstance().stopGame(new AsyncCallback<Void>(){
      @Override
      public void onFailure(Throwable caught) {
        Window.alert("Error from server: " + caught.getClass().getName() + ": " + caught.getMessage());
      }

      @Override
      public void onSuccess(Void result) {
        director.showNewGame();
      }
    }));
  }

  public void setLastMove(MoveResult result) {
    if (result.getLastMove() == null) {
      moveLabel.setText("");
    } else {
      final String lastMove = result.getLastMove() == null ? "no move" : result.getLastMove();
      final String previousPlayerName = result.getMovedPlayerName() == null ? "no previous player" : result.getMovedPlayerName();
      final String moveResult = result.getLastMoveResult() == null ? "no result" : result.getLastMoveResult();

      final String newLabel = Stream.of(
          "Last move ", lastMove,
          " by ", previousPlayerName,
          " resulted in ", moveResult)
          .collect(Collectors.joining());
      moveLabel.setText(newLabel);
    }

    final String currentPlayerName = result.getNextPlayerName() == null ? "no current player" : result.getNextPlayerName();
    nextPlayerPanel.setText(currentPlayerName + ", make your move please.");
  }
}
