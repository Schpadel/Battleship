package battleship;

import java.io.IOException;
import java.util.ArrayList;

public class Game {

  private Player player1;
  private Player player2;

  public Game() {

  }

  public static void promptEnterKey() {
    System.out.println("Press Enter and pass the move to another player");
    try {
      System.in.read();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static Ship getHitShip(Player player, int[] target) {
    for (Ship current : player.getShips()) {
      if (current.checkHitBox(target, player.getField())) {
        return current;
      }
    }
    return null;
  }

  public void initializeGame() {
    ArrayList<Ship> player1Ships = new ArrayList<>();
    ArrayList<Ship> player2Ships = new ArrayList<>();
    player1 = new Player("Player 1", new Field(), new Field());
    player2 = new Player("Player 2", new Field(), new Field());

    Ship carrier = new Ship(5, "Aircraft Carrier");
    player1Ships.add(carrier);
    Ship battleship = new Ship(4, "Battleship");
    player1Ships.add(battleship);
    Ship submarine = new Ship(3, "Submarine");
    player1Ships.add(submarine);
    Ship cruiser = new Ship(3, "Cruiser");
    player1Ships.add(cruiser);
    Ship destroyer = new Ship(2, "Destroyer");
    player1Ships.add(destroyer);

    player2Ships.add(new Ship(5, "Aircraft Carrier"));
    player2Ships.add(new Ship(4, "Battleship"));
    player2Ships.add(new Ship(3, "Submarine"));
    player2Ships.add(new Ship(3, "Cruiser"));
    player2Ships.add(new Ship(2, "Destroyer"));

    player1.setShips(player1Ships);
    player2.setShips(player2Ships);

  }

  public void play() {
    player1.placeShips();

    promptEnterKey();
    player2.placeShips();
    promptEnterKey();

    while (player1.shipsAlive() || player2.shipsAlive()) {
      takeTurn(player1, player2);
      takeTurn(player2, player1);
    }
  }
  private void takeTurn(Player player, Player target) {
    boolean sankAShip;

    player.printFields();
    System.out.printf("%s, it's your turn", player.getName());
    sankAShip = player.shoot(target);

    if (!target.shipsAlive()) {
      System.out.println("You sank the last ship. You won. Congratulations!");
    }

    if (!sankAShip) {
      promptEnterKey();
    }
  }

}
