package com.github.nskvortsov.maze.web.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class App implements EntryPoint {

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {

    final RootPanel slot = RootPanel.get("slot2");

    final PanelDirector director = new PanelDirector() {

      Panel myNewGamePanel;
      Panel myPlayPanel;

      @Override
      public void setNewGamePanel(Panel p) {
        myNewGamePanel = p;
      }

      @Override
      public void setPlayPanel(Panel p) {
        myPlayPanel = p;
      }

      @Override
      public void showNewGame() {
        slot.clear();
        slot.add(myNewGamePanel);
      }

      @Override
      public void showPlay() {
        slot.clear();
        slot.add(myPlayPanel);
      }
    };

    final PlayWidget playWidget = new PlayWidget(director);
    new NewGameWidget(director, playWidget);

    MazeGameService.App.getInstance().getLastMove(new AsyncCallback<MoveResult>() {
      @Override
      public void onFailure(Throwable caught) {
        Window.alert("Something wend wrong: " + caught.getClass().getName() + " - " + caught.getMessage());
      }

      @Override
      public void onSuccess(MoveResult result) {
        if (result == null) {
          director.showNewGame();
        } else {
          playWidget.setLastMove(result);
          director.showPlay();
        }
      }
    });
  }
}

