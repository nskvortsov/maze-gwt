package com.github.nskvortsov.maze.web.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Nikita.Skvortsov
 * date: 22.06.2017.
 */
class NewGameWidget {
  public NewGameWidget(PanelDirector director, PlayWidget playWidget) {
    final Panel newGamePanel = new VerticalPanel();
    final TextArea names = new TextArea();
    final Button start = new Button("Start");
    director.setNewGamePanel(newGamePanel);

    start.addClickHandler(event -> {
      final List<String> namesList = Arrays.stream(names.getValue().split("\n"))
          .map(String::trim)
          .filter(s -> s.length() > 0)
          .collect(Collectors.toList());

      if (namesList.size() < 2) {
        Window.alert("Need more names!");
      } else {
        MazeGameService.App.getInstance().startTheGame(namesList, new AsyncCallback<MoveResult>() {
          @Override
          public void onFailure(Throwable caught) {
            Window.alert("Error from server: " + caught.getClass().getName() + ": " + caught.getMessage());
          }

          @Override
          public void onSuccess(MoveResult result) {
            playWidget.setLastMove(result);
            director.showPlay();
          }
        });
      }
    });

    newGamePanel.add(new Label("There is no game.\nSpecify two or more player names and press Start"));
    newGamePanel.add(names);
    newGamePanel.add(start);
  }
}
