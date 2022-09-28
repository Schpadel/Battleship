package battleship;

import java.util.Arrays;
import java.util.Scanner;

public class Field {

  private final String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
  private String[][] playField;

  public void initializeField(int x, int y) {
    playField = new String[x][y];
    for (String[] strings : playField) {
      Arrays.fill(strings, "~");
    }
  }

  public void printField() {
    System.out.print("  1 2 3 4 5 6 7 8 9 10\n");

    for (int row = 0; row < playField.length; row++) {
      System.out.print(alphabet[row] + " ");
      for (int col = 0; col < playField[row].length; col++) {
        System.out.print(playField[row][col] + " ");
      }
      System.out.println();

    }
    System.out.println();
  }

  public void placeShip(Ship ship) {
    System.out.printf("Enter the coordinates of the %s (%d cells):%n", ship.getType(),
        ship.getSize());
    Scanner input = new Scanner(System.in);

    boolean placed = false;
    while (true) {
      String userInput = input.nextLine();
      System.out.println();

      // Split coordinates and implement ship placement in play field
      String[] coords = userInput.split(" ");

      int[] start = new int[2];
      int[] end = new int[2];
      start[0] = Arrays.asList(alphabet).indexOf(String.valueOf(coords[0].charAt(0)));
      if (coords[0].length() == 3) {
        start[1] = Integer.parseInt(coords[0].substring(1, 3)) - 1;
      } else {
        start[1] = Integer.parseInt(String.valueOf(coords[0].charAt(1))) - 1;
      }

      end[0] = Arrays.asList(alphabet).indexOf(String.valueOf(coords[1].charAt(0)));
      if (coords[1].length() == 3) {
        end[1] = Integer.parseInt(coords[1].substring(1, 3)) - 1;
      } else {
        end[1] = Integer.parseInt(String.valueOf(coords[1].charAt(1))) - 1;
      }

      // execute 2 times switch start and end at the end to check for both combinations
      for (int x = 0; x < 2; x++) {

        try {
          placed = placeShipAt(ship, start, end);
        } catch (Exception e) {
          System.out.println("Error! You placed it too close to another one. Try again:");
        }

        // exit loop is ship was successfully placed!
        if (placed) {
          return;
        }

        // swap start and end array to check both combinations
        int[] temp;
        temp = start;
        start = end;
        end = temp;
      }
      System.out.println("Error! Wrong ship location! Try again:");
    }
  }

  private boolean placeShipAt(Ship ship, int[] start, int[] end) throws Exception {
    boolean placed = false;
    int size;

    if (start[0] == end[0] && (end[1] - start[1]) + 1 == ship.getSize()) {
      size = (end[1] - start[1]) + 1;

      /* check if field is empty horizontal
         check if neighbors are empty as well
       */
      for (int i = 0; i < size; i++) {

        for (int dr = -1; dr <= 1; dr++) {
          for (int dc = -1; dc <= 1; dc++) {
            int nr = start[0] + dr;
            int nc = start[1] + i + dc;

            if (accessInvalid(nr, nc)) {
              continue;
            }
            if (playField[nr][nc].equals("O")) {
              throw new Exception("Place occupied!");
            }
          }
        }
      }

      // place ship horizontal
      for (int i = 0; i < size; i++) {
        playField[start[0]][start[1] + i] = "O";
        placed = true;
      }
      ship.setStart(start);
      ship.setEnd(end);
      ship.setHorizontal(true);


    } else if (start[1] == end[1] && (end[0] - start[0]) + 1 == ship.getSize()) {
      size = Math.abs(end[0] - start[0]) + 1;
      // check if field is empty vertical
      for (int i = 0; i < size; i++) {
        for (int dr = -1; dr <= 1; dr++) {
          for (int dc = -1; dc <= 1; dc++) {
            int nr = start[0] + i + dr;
            int nc = start[1] + dc;

            if (accessInvalid(nr, nc)) {
              continue;
            }
            if (playField[nr][nc].equals("O")) {
              throw new Exception("Place occupied!");
            }
          }
        }
      }

      for (int i = 0; i < size; i++) {
        playField[start[0] + i][start[1]] = "O";
        placed = true;
      }
      ship.setStart(start);
      ship.setEnd(end);
      ship.setHorizontal(false);


    }
    return placed;
  }

  private boolean accessInvalid(int nr, int nc) {
    return nr < 0 || nc < 0 || nr >= playField.length || nc >= playField.length;
  }

  public String[][] getField() {
    return this.playField;
  }

  public boolean shoot(Player targetPlayer, Field outputField) {
    String[][] outputFieldArray = outputField.getField();
    Scanner input = new Scanner(System.in);
    System.out.println("Take a shot!");

    String userInput = input.nextLine();

    int[] target = new int[2];
    target[0] = Arrays.asList(alphabet).indexOf(String.valueOf(userInput.charAt(0)));

    if (userInput.length() == 3) {
      target[1] = Integer.parseInt(userInput.substring(1, 3)) - 1;
    } else {
      target[1] = Integer.parseInt(String.valueOf(userInput.charAt(1))) - 1;
    }

    try {

      if (playField[target[0]][target[1]].equals("O") || playField[target[0]][target[1]].equals(
          "X")) {
        playField[target[0]][target[1]] = "X";
        outputFieldArray[target[0]][target[1]] = "X";
        //outputField.printField();

        Ship hitShip = Game.getHitShip(targetPlayer, target);
        if (hitShip != null && hitShip.isSunk(this) && targetPlayer.shipsAlive()) {
          System.out.println("You sank a ship!");
          System.out.println("Please press enter.");
          input.nextLine();
          return true;
        } else {
          System.out.println("You hit a ship!");
        }
        //printField();
      } else {
        playField[target[0]][target[1]] = "M";
        outputFieldArray[target[0]][target[1]] = "M";
        //outputField.printField();
        System.out.println("You missed!");
        //printField();
      }

    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println("Error! You entered the wrong coordinates! Try again:");
    }

    return false;


  }
}
