package com.comp301.a09akari.view;

import com.comp301.a09akari.SamplePuzzles;
import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class AppLauncher extends Application {
  @Override
  public void start(Stage stage) {
    // puzzle instantiation
    Puzzle p1 = new PuzzleImpl(SamplePuzzles.PUZZLE_01);
    Puzzle p2 = new PuzzleImpl(SamplePuzzles.PUZZLE_02);
    Puzzle p3 = new PuzzleImpl(SamplePuzzles.PUZZLE_03);
    Puzzle p4 = new PuzzleImpl(SamplePuzzles.PUZZLE_04);
    Puzzle p5 = new PuzzleImpl(SamplePuzzles.PUZZLE_05);
    // lib instantation
    PuzzleLibrary lib = new PuzzleLibraryImpl();
    lib.addPuzzle(p1);
    lib.addPuzzle(p2);
    lib.addPuzzle(p3);
    lib.addPuzzle(p4);
    lib.addPuzzle(p5);
    // model & controller instantiation
    Model _model = new ModelImpl(lib);
    ClassicMvcController _controller = new ControllerImpl(_model);
    MainView view = new MainView(_model, _controller);
    // stage stuff
    stage.setTitle("Light up");
    // pane?
    // StackPane pane = new StackPane();
    // scene
    Scene scene = view.getScene();
    stage.setScene(scene);
    stage.setResizable(true);
    // display stage
    stage.show();
  }
}
