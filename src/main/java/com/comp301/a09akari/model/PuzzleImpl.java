package com.comp301.a09akari.model;

public class PuzzleImpl implements Puzzle {
  int[][] _board;

  public PuzzleImpl(int[][] board) {
    if (board == null) {
      throw new IllegalArgumentException();
    }
    _board = board;
  }

  @Override
  public int getWidth() {
    return _board[0].length;
  }

  @Override
  public int getHeight() {
    return _board.length;
  }

  @Override
  public CellType getCellType(int r, int c) {
    if (r > this.getHeight() || r < 0 || c > this.getWidth() || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    int cellT = _board[r][c];
    if (cellT == 0 || cellT == 1 || cellT == 2 || cellT == 3 || cellT == 4) {
      return CellType.CLUE;
    }
    if (cellT == 5) {
      return CellType.WALL;
    }
    // cellT == 6
    return CellType.CORRIDOR;
  }

  @Override
  public int getClue(int r, int c) {
    if (r > this.getHeight() || r < 0 || c > this.getWidth() || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    int cellT = _board[r][c];
    if (cellT == 0) {
      return 0;
    }
    if (cellT == 1) {
      return 1;
    }
    if (cellT == 2) {
      return 2;
    }
    if (cellT == 3) {
      return 3;
    }
    if (cellT == 4) {
      return 4;
    }
    throw new IllegalArgumentException();
  }
}
