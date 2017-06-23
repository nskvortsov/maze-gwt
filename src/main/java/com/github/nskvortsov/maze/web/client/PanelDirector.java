package com.github.nskvortsov.maze.web.client;

import com.google.gwt.user.client.ui.Panel;

/**
 * Created by Nikita.Skvortsov
 * date: 22.06.2017.
 */
interface PanelDirector {
 void setNewGamePanel(Panel p);
 void setPlayPanel(Panel p);

 void showNewGame();
 void showPlay();
}
