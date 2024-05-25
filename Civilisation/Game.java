package Civilisation;

import java.util.ArrayList;

public class Game {
  private ArrayList<Player> players;

  public Game() {
    players = new ArrayList<Player>();
    players.add(new Player("Player 1"));
    players.add(new Player("Player 2"));
  }

  public void start() {
  System.out.println("Le jeu commence!");
  boolean gameOver = false;
  while (!gameOver) {
    for (Player player : players) {
      player.playTurn();
      if (player.hasWon()) {
        System.out.println("Le joueur " + player.getName() + " a gagné!");
        gameOver = true;
        break;
      }
    }
  }
}
}