package com.comp301.a09akari.controller;

import com.comp301.a09akari.model.Model;

import java.util.Random;

public class ControllerImpl implements ClassicMvcController {
  private Model _model;

  public ControllerImpl(Model model) {
    if (model != null) {
      _model = model;
    }
  }

  @Override
  public void clickNextPuzzle() {
    // if next is null -> keep the same index
    int index = _model.getActivePuzzleIndex();
    if (index + 1 > _model.getPuzzleLibrarySize()) {
      _model.setActivePuzzleIndex(index);
    } else {
      _model.setActivePuzzleIndex(_model.getActivePuzzleIndex() + 1);
    }
  }

  @Override
  public void clickPrevPuzzle() {
    // if prev is null -> keep the same index
    int index = _model.getActivePuzzleIndex();
    if (index - 1 < 0) {
      _model.setActivePuzzleIndex(index);
    } else {
      _model.setActivePuzzleIndex(_model.getActivePuzzleIndex() - 1);
    }
  }

  @Override
  public void clickRandPuzzle() {
    // tried to give random number
    Random rand = new Random();
    int randint = rand.nextInt(_model.getPuzzleLibrarySize());
    while (randint == _model.getActivePuzzleIndex()) {
      randint = rand.nextInt(_model.getPuzzleLibrarySize());
    }
    _model.setActivePuzzleIndex(randint);
  }

  @Override
  public void clickResetPuzzle() {
    _model.resetPuzzle();
  }

  @Override
  public void clickCell(int r, int c) {
    if (_model.isLamp(r, c)) {
      _model.removeLamp(r, c);
    } else {
      _model.addLamp(r, c);
    }
  }
}
