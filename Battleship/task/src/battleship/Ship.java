package battleship;

public class Ship {

  boolean horizontal;
  private int size;
  private int[] start;
  private int[] end;
  private String type;

  public Ship(int size, String type) {
    this.size = size;
    this.type = type;
    start = new int[2];
    end = new int[2];
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public boolean isHorizontal() {
    return horizontal;
  }

  public void setHorizontal(boolean horizontal) {
    this.horizontal = horizontal;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public int[] getStart() {
    return start;
  }

  public void setStart(int[] start) {
    this.start = start;
  }

  public int[] getEnd() {
    return end;
  }

  public void setEnd(int[] end) {
    this.end = end;
  }


  public boolean isSunk(Field playField) {
    String[][] playFieldArray = playField.getField();
    if (this.isHorizontal()) {
      for (int i = 0; i < size; i++) {
        if (playFieldArray[start[0]][start[1] + i].equals("O")) {
          return false;
        }
      }
    } else {
      for (int i = 0; i < size; i++) {
        if (playFieldArray[start[0] + i][start[1]].equals("O")) {
          return false;
        }
      }
    }
    return true;
  }

  public boolean checkHitBox(int[] target, Field playField) {
    String[][] playFieldArray = playField.getField();
    if (this.isHorizontal()) {
      for (int i = 0; i < size; i++) {
        if (start[0] == target[0] && start[1] + i == target[1]) {
          return true;
        }
      }

      // TODO: CHECK VERTICAL DETECTION BUGGED!
    } else {
      for (int i = 0; i < size; i++) {
        if (start[0] + i == target[0] && start[1] == target[1]) {
          return true;
        }
      }
    }
    return false;
  }
}

