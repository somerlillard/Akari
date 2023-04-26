package com.comp301.a09akari.model;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {
  private PuzzleLibrary _library;
  private int[][] _lamps;
  private List<ModelObserver> _observers;
  private int _activePuzz;

  public ModelImpl(PuzzleLibrary library) {
    if (library == null) {
      throw new IllegalArgumentException();
    }
    _library = library;
    _observers = new ArrayList<ModelObserver>();
    _activePuzz = 0;
    int r = _library.getPuzzle(_activePuzz).getHeight();
    int c = _library.getPuzzle(_activePuzz).getWidth();
    _lamps = new int[r][c];
    for (int i = 0; i < r; i++) {
      for (int j = 0; j < c; j++) {
        _lamps[i][j] = 0;
      }
    }
    // ^initializing lamp cells to 0
  }

  @Override
  public void addLamp(int r, int c) {
    Puzzle p = _library.getPuzzle(_activePuzz);
    if (r > p.getHeight() || r < 0 || c > p.getWidth() || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (p.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    // _lamps is a 2d array list, anywhere with a 1 has a lamp, 0 does not
    _lamps[r][c] = 1;
    for (ModelObserver m : _observers) {
      m.update(this);
    }
  }

  @Override
  public void removeLamp(int r, int c) {
    Puzzle p = _library.getPuzzle(_activePuzz);
    if (r > p.getHeight() || r < 0 || c > p.getWidth() || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (p.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    _lamps[r][c] = 0;
    for (ModelObserver m : _observers) {
      m.update(this);
    }
  }

  @Override
  public boolean isLit(int r, int c) {
    Puzzle p = _library.getPuzzle(_activePuzz);
    if (r > p.getHeight() || r < 0 || c > p.getWidth() || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (p.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    boolean isLit = false;
    // checking same row cells
    // top to mid
    for (int i = 0; i <= r; i++) {
      // if corridor- lamp, no lamp ... if lamp -> lit
      if (p.getCellType(i, c) == CellType.CORRIDOR) {
        if (_lamps[i][c] == 1) {
          isLit = true;
        }
      }
      // if wall/clue -> not lit
      if (p.getCellType(i, c) == CellType.WALL || p.getCellType(i, c) == CellType.CLUE) {
        isLit = false;
      }
      // if index==actual r & islit then we can say the cell islit
      if (i == r && isLit) {
        return true;
      }
    }
    // bott to mid
    for (int i = p.getHeight() - 1; i > r; i--) {
      if (p.getCellType(i, c) == CellType.CORRIDOR) {
        if (_lamps[i][c] == 1) {
          isLit = true;
        }
      }
      // if wall/clue -> not lit
      if (p.getCellType(i, c) == CellType.WALL || p.getCellType(i, c) == CellType.CLUE) {
        isLit = false;
      }
    }
    // what if there's a wall at the last spot and I switch isLit it false -> only index inward
    // left to mid
    for (int j = 0; j <= c; j++) {
      if (p.getCellType(j, c) == CellType.CORRIDOR) {
        if (_lamps[r][j] == 1) {
          isLit = true;
        }
      }
      if (p.getCellType(j, c) == CellType.WALL || p.getCellType(j, c) == CellType.CLUE) {
        isLit = false;
      }
      if (j == c && isLit) {
        return true;
      }
    }
    // right to mid
    for (int j = p.getWidth() - 1; j > c; j--) {
      if (p.getCellType(j, c) == CellType.CORRIDOR) {
        if (_lamps[r][j] == 1) {
          isLit = true;
        }
      }
      if (p.getCellType(j, c) == CellType.WALL || p.getCellType(j, c) == CellType.CLUE) {
        isLit = false;
      }
    }
    return isLit;
  }

  @Override
  public boolean isLamp(int r, int c) {
    Puzzle p = _library.getPuzzle(_activePuzz);
    if (r > p.getHeight() || r < 0 || c > p.getWidth() || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (p.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    return _lamps[r][c] == 1;
  }

  // direct view only
  @Override
  public boolean isLampIllegal(int r, int c) {
    Puzzle p = _library.getPuzzle(_activePuzz);
    boolean illegal = false;
    if (r > p.getHeight() || r < 0 || c > p.getWidth() || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (_lamps[r][c] != 1) {
      throw new IllegalArgumentException();
    }
    // sameish code as isLit
    // not checking r bc it's a lamp! -> column top to mid
    for (int i = 0; i < r; i++) {
      // if corridor- lamp, no lamp ... if lamp -> lit
      if (p.getCellType(i, c) == CellType.CORRIDOR) {
        if (_lamps[i][c] == 1) {
          illegal = true;
        }
      }
      // if wall/clue -> not lit
      if (p.getCellType(i, c) == CellType.WALL || p.getCellType(i, c) == CellType.CLUE) {
        illegal = false;
      }
    }
    if (illegal) {
      return true;
    }
    // col bottom to mid
    for (int i = p.getWidth() - 1; i > r; i--) {
      if (p.getCellType(i, c) == CellType.CORRIDOR) {
        if (_lamps[i][c] == 1) {
          illegal = true;
        }
      }
      // if wall/clue -> not lit
      if (p.getCellType(i, c) == CellType.WALL || p.getCellType(i, c) == CellType.CLUE) {
        illegal = false;
      }
    }
    if (illegal) {
      return true;
    }
    // what if there's a wall at the last spot and I switch isLit it false -> only index inward
    // checking row left to mid
    for (int j = 0; j < c; j++) {
      if (p.getCellType(j, c) == CellType.CORRIDOR) {
        if (_lamps[r][j] == 1) {
          illegal = true;
        }
      }
      if (p.getCellType(j, c) == CellType.WALL || p.getCellType(j, c) == CellType.CLUE) {
        illegal = false;
      }
    }
    if (illegal) {
      return true;
    }
    // checking row right to mid
    for (int j = p.getHeight() - 1; j > c; j--) {
      if (p.getCellType(j, c) == CellType.CORRIDOR) {
        if (_lamps[r][j] == 1) {
          illegal = true;
        }
      }
      if (p.getCellType(j, c) == CellType.WALL || p.getCellType(j, c) == CellType.CLUE) {
        illegal = false;
      }
    }
    return illegal;
  }

  @Override
  public Puzzle getActivePuzzle() {
    return _library.getPuzzle(_activePuzz);
  }

  @Override
  public int getActivePuzzleIndex() {
    return _activePuzz;
  }

  @Override
  public void setActivePuzzleIndex(int index) {
    if (index >= _library.size() || index < 0) {
      throw new IndexOutOfBoundsException();
    }
    _activePuzz = index;
    for (ModelObserver m : _observers) {
      m.update(this);
    }
  }

  @Override
  public int getPuzzleLibrarySize() {
    return _library.size();
  }

  @Override
  public void resetPuzzle() {
    Puzzle p = _library.getPuzzle(_activePuzz);
    for (int c = 0; c < p.getWidth(); c++) {
      for (int r = 0; r < p.getHeight(); r++) {
        _lamps[r][c] = 0;
      }
    }
    for (ModelObserver m : _observers) {
      m.update(this);
    }
  }

  @Override
  public boolean isSolved() {
    Puzzle p = _library.getPuzzle(_activePuzz);
    for (int r = 0; r < p.getHeight(); r++) {
      for (int c = 0; c < p.getWidth(); c++) {
        switch (p.getCellType(r, c)) {
          case WALL:
            break;
          case CORRIDOR:
            if (this.isLamp(r, c)) {
              if (this.isLampIllegal(r, c)) {
                return false;
              }
            }
            if (!this.isLit(r, c)) {
              return false;
            }
          case CLUE:
            if (!this.isClueSatisfied(r, c)) {
              return false;
            }
        }
      }
    }
    return true;
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    Puzzle p = _library.getPuzzle(_activePuzz);
    if (r > p.getHeight() || r < 0 || c > p.getWidth() || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (p.getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException();
    }
    int numLamps = 0;
    boolean ri = true;
    boolean l = true;
    boolean top = true;
    boolean bott = true;
    if (r - 1 < 0) {
      top = false;
    }
    if (r + 1 > p.getHeight()) {
      bott = false;
    }
    if (c - 1 < 0) {
      l = false;
    }
    if (c + 1 > p.getWidth()) {
      ri = false;
    }
    // if 0 lamps allowed
    if (p.getClue(r, c) == 0) {
      if (top) {
        if (this.isLamp(r - 1, c)) {
          return false;
        }
      }
      if (bott) {
        if (this.isLamp(r + 1, c)) {
          return false;
        }
      }
      if (l) {
        if (this.isLamp(r, c - 1)) {
          return false;
        }
      }
      if (ri) {
        if (this.isLamp(r, c + 1)) {
          return false;
        }
      }
      return true;
    }
    // if 1 lamp allowed
    if (p.getClue(r, c) == 1) {
      if (top) {
        if (this.isLamp(r - 1, c)) {
          numLamps++;
        }
      }
      if (bott) {
        if (this.isLamp(r + 1, c)) {
          numLamps++;
        }
      }
      if (l) {
        if (this.isLamp(r, c - 1)) {
          numLamps++;
        }
      }
      if (ri) {
        if (this.isLamp(r, c + 1)) {
          numLamps++;
        }
      }
      if (numLamps == 1) {
        return true;
      }
    }
    // if 2 lamps allowed
    if (p.getClue(r, c) == 2) {
      if (top) {
        if (this.isLamp(r - 1, c)) {
          numLamps++;
        }
      }
      if (bott) {
        if (this.isLamp(r + 1, c)) {
          numLamps++;
        }
      }
      // up - what if no cell above
      if (l) {
        if (this.isLamp(r, c - 1)) {
          numLamps++;
        }
      }
      if (ri) {
        if (this.isLamp(r, c + 1)) {
          numLamps++;
        }
      }
      if (numLamps == 2) {
        return true;
      }
    }
    // if 3 lamps
    if (p.getClue(r, c) == 3) {
      if (top) {
        if (this.isLamp(r - 1, c)) {
          numLamps++;
        }
      }
      if (bott) {
        if (this.isLamp(r + 1, c)) {
          numLamps++;
        }
      }
      if (l) {
        if (this.isLamp(r, c - 1)) {
          numLamps++;
        }
      }
      if (ri) {
        if (this.isLamp(r, c + 1)) {
          numLamps++;
        }
      }
      if (numLamps == 3) {
        return true;
      }
    }
    // if 4 lamps
    if (p.getClue(r, c) == 4) {
      if (top) {
        if (this.isLamp(r - 1, c)) {
          numLamps++;
        }
      }
      if (bott) {
        if (this.isLamp(r + 1, c)) {
          numLamps++;
        }
      }
      if (l) {
        if (this.isLamp(r, c - 1)) {
          numLamps++;
        }
      }
      if (ri) {
        if (this.isLamp(r, c + 1)) {
          numLamps++;
        }
      }
      if (numLamps == 4) {
        return true;
      }
      return false;
    }
    return false;
  }

  @Override
  public void addObserver(ModelObserver observer) {
    _observers.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    _observers.remove(observer);
  }
}
