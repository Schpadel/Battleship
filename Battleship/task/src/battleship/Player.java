package battleship;

import java.util.ArrayList;

public class Player {

  private String name;
  private Field ownField;
  private Field knownEnemyField;

  private ArrayList<Ship> ships;

  public ArrayList<Ship> getShips() {
    return ships;
  }

  public void setShips(ArrayList<Ship> ships) {
    this.ships = ships;
  }

  public Player(String name, Field ownField, Field knownEnemyField) {
    this.name = name;
    this.ownField = ownField;
    this.knownEnemyField = knownEnemyField;
    ownField.initializeField(10, 10);
    knownEnemyField.initializeField(10, 10);
  }

  public void printFields() {
    knownEnemyField.printField();
    System.out.println("---------------------------------");
    ownField.printField();
  }

  public void printOwnField() {
    ownField.printField();
  }

  public void placeShips() {
    System.out.println("%s, place your ships on the game field".formatted(this.name));
    ownField.printField();
    for (Ship ship : ships) {
      ownField.placeShip(ship);
      ownField.printField();

    }
  }

  public Field getField() {
    return this.ownField;
  }

  public boolean shoot(Player targetPlayer) {

    // check logic to shoot at enemy field instead of own field lol
    return targetPlayer.getField().shoot(targetPlayer, knownEnemyField);
  }


  public String getName() {
    return this.name;
  }


  public boolean shipsAlive() {
    for (Ship current : this.getShips()) {
      if (!current.isSunk(this.getField())) {
        return true;
      }
    }
    return false;
  }
}
