import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;
import java.util.*;

// Represents the visualization of a maze
// constructed using Kruskal's algorithm.
class KruskalMaze extends World {

  // Board X and Y dimension sizes.
  int boardX;
  int boardY;

  // Changes positioning of edges based on scale.
  int allModifier;
  int upLeftModifier;
  int rightDownModifier;

  // Scale of an individual cell based on boardSize.
  int cellScale;

  // Work list of edges
  ArrayList<Edge> workList;

  // Edges with no cycles/repeats
  ArrayList<Edge> totalEdges;

  // All cells that comprise the maze.
  ArrayList<ArrayList<Cell>> cellBoard;

  // Hash map to store cell representatives
  HashMap<Cell, Cell> storage;

  // BFS/DFS path generated
  ArrayList<Cell> searchPath;

  // Graph to find different searches for this maze.
  Graph graph = new Graph();

  // General world scene that represents the visual of the maze.
  WorldScene scene;

  // Cell that is representative of being part of the solution
  // for either BFS/DFS.
  Cell goal;

  // Has the search completed?
  boolean traversed;

  // KruskalMaze constructor
  KruskalMaze(int boardX, int boardY) {

    this.boardX = boardX;
    this.boardY = boardY;

    this.cellBoard = new ArrayList<ArrayList<Cell>>();

    this.workList = new ArrayList<Edge>();
    this.totalEdges = new ArrayList<Edge>();

    this.storage = new HashMap<Cell, Cell>();
    this.searchPath = new ArrayList<Cell>();

    // Maze creation methods
    initializeCells();
    createWorklist();
    workListSort();
    mergeTrees();
    setEdgePresence();
    this.makeMaze();

    this.goal = this.cellBoard.get(boardX - 1).get(boardY - 1);
    this.traversed = false;
  }

  // Constructor for testing convenience, does not set up maze.
  KruskalMaze(int boardSize) {
    this.boardX = boardSize;
    this.boardY = boardSize;
    this.cellBoard = new ArrayList<ArrayList<Cell>>();
    this.workList = new ArrayList<Edge>();
    this.totalEdges = new ArrayList<Edge>();
    this.storage = new HashMap<Cell, Cell>();
    this.searchPath = new ArrayList<Cell>();
    this.traversed = false;
  }

  // Constructor for testing convenience, does not set up maze.
  // and takes in a given cellBoard.
  KruskalMaze(int boardSize, ArrayList<ArrayList<Cell>> board) {
    this.boardX = boardSize;
    this.boardY = boardSize;
    this.cellBoard = board;
    this.workList = new ArrayList<Edge>();
    this.totalEdges = new ArrayList<Edge>();
    this.storage = new HashMap<Cell, Cell>();
    this.searchPath = new ArrayList<Cell>();
    this.goal = this.cellBoard.get(boardX - 1).get(boardY - 1);
    this.traversed = false;
  }

  // Last constructor for testing convenience, gives a random seed.
  KruskalMaze(int boardSize, Random rand) {
    this.boardX = boardSize;
    this.boardY = boardSize;
    this.cellBoard = new ArrayList<ArrayList<Cell>>();
    this.workList = new ArrayList<Edge>();
    this.totalEdges = new ArrayList<Edge>();
    this.storage = new HashMap<Cell, Cell>();
    this.searchPath = new ArrayList<Cell>();
    this.traversed = false;
  }

  // Visual representation of the scene/maze.
  public WorldScene makeMaze() {

    this.scene = new WorldScene(1200, 800);

    for (int i = 0; i < this.boardX; i++) {
      for (int j = 0; j < this.boardY; j++) {

        scene.placeImageXY(this.cellBoard.get(i).get(j).draw(), this.cellScale * i + 12,
            this.cellScale * j + 12);

        if (!cellBoard.get(i).get(j).up) {

          scene.placeImageXY(new LineImage(new Posn(this.cellScale, 0), Color.BLACK),
              i * this.cellScale + this.allModifier, j * this.cellScale + this.upLeftModifier);
        }

        if (!this.cellBoard.get(i).get(j).right) {

          scene.placeImageXY(new LineImage(new Posn(0, this.cellScale), Color.BLACK),
              i * this.cellScale + this.rightDownModifier, j * this.cellScale + this.allModifier);
        }

        if (!this.cellBoard.get(i).get(j).down) {

          scene.placeImageXY(new LineImage(new Posn(this.cellScale, 0), Color.BLACK),
              i * this.cellScale + this.allModifier, j * this.cellScale + this.rightDownModifier);
        }

        if (!this.cellBoard.get(i).get(j).left) {

          scene.placeImageXY(new LineImage(new Posn(0, this.cellScale), Color.BLACK),
              i * this.cellScale + this.upLeftModifier, j * this.cellScale + this.allModifier);
        }
      }
    }

    return scene;
  }

  // Updates the world scene based on searches/resets.
  public WorldScene makeScene() {

    if (this.searchPath.size() > 1) {

      this.gotoEnd();
    }

    else if (this.searchPath.size() > 0) {

      this.reachedEnd();
    }

    else if (this.goal.previous != null && traversed) {
      this.traceback();
    }

    return this.scene;
  }

  // Creates cells and edge locations for the board and stores their
  // representative in the hash map
  void initializeCells() {

    ArrayList<Cell> currentColumn;

    // Sets scale of cells and positioning of edges
    // based on that scale.
    if (this.boardX > 80 || this.boardY > 80) {

      this.cellScale = 11;
      this.allModifier = 12;
      this.upLeftModifier = 6;
      this.rightDownModifier = 17;
    }

    else if (this.boardX > 40 || this.boardY > 40) {

      this.cellScale = 13;
      this.allModifier = 11;
      this.upLeftModifier = 4;
      this.rightDownModifier = 17;
    }

    else {

      this.cellScale = 19;
      this.allModifier = 11;
      this.upLeftModifier = 1;
      this.rightDownModifier = 20;
    }

    // Creates cells and appropriately sets their x and y
    // positions
    for (int i = 0; i < this.boardX; i++) {

      // Current column of cells in the maze.
      currentColumn = new ArrayList<Cell>();

      for (int j = 0; j < this.boardY; j++) {

        // Set the first cell to green and last target cell to purple.
        Cell input = new Cell(i, j, this.cellScale);
        if (i == 0 && j == 0) {

          input.color = Color.GREEN;
        }

        if (i == this.boardX - 1 && j == this.boardY - 1) {

          input.color = Color.MAGENTA;
        }

        currentColumn.add(input);
        this.storage.put(currentColumn.get(j), currentColumn.get(j));
      }

      this.cellBoard.add(currentColumn);
    }

  }

  // Creates a list of edges with random weight to be processed through.
  void createWorklist() {

    ArrayList<Cell> cur;
    int colCount = this.cellBoard.size();
    // For each column
    for (int i = 0; i < colCount; i++) {

      cur = this.cellBoard.get(i);
      int rowCount = cur.size();
      // For each row
      for (int j = 0; j < rowCount; j++) {

        // We have reached last cell, nothing required!
        if (i == colCount - 1 && j == rowCount - 1) {
          makeMaze();
        }

        // Final col
        else if (i == colCount - 1) {
          Edge e = new Edge(cur.get(j), cur.get(j + 1), this.boardX);
          workList.add(e);

        }

        // Final row
        else if (j == rowCount - 1) {
          Edge e = new Edge(cur.get(j), this.cellBoard.get(i + 1).get(j), this.boardX);
          workList.add(e);
        }

        // Standard edge connection
        else {
          Edge e = new Edge(this.cellBoard.get(i).get(j), this.cellBoard.get(i + 1).get(j),
              this.boardX);
          workList.add(e);

          Edge e1 = new Edge(this.cellBoard.get(i).get(j), this.cellBoard.get(i).get(j + 1),
              this.boardX);
          workList.add(e1);

        }

      }
    }

  }

  // Indicates whether an edge is present for a singular cell
  // based on up, down, left, and right coordinates.
  void setEdgePresence() {

    for (int i = 0; i < this.totalEdges.size(); i += 1) {

      Cell c1 = this.totalEdges.get(i).from;
      Cell c2 = this.totalEdges.get(i).to;

      if ((c2.x == c1.x) && (c2.y > c1.y)) {

        c2.up = true;
        c1.down = true;

      }

      if ((c2.x == c1.x) && (c2.y < c1.y)) {
        c1.up = true;
        c2.down = true;
      }

      if ((c2.y == c1.y) && (c2.x < c1.x)) {
        c2.right = true;
        c1.left = true;
      }

      if ((c2.y == c1.y) && (c2.x > c1.x)) {
        c1.right = true;
        c2.left = true;
      }

    }
  }

  // Sorts the work list of randomized weighted edges
  // using the sort of a comparator/collections.
  void workListSort() {

    Comparator<Edge> comp = new EdgeDifference();

    Collections.sort(this.workList, comp);
  }

  // Goes through work list to remove already present
  // connections & combines disjointed sets
  void mergeTrees() {

    for (int i = 0; i < this.workList.size(); i++) {

      if (!(getRep(this.workList.get(i).to).equals(getRep(this.workList.get(i).from)))) {

        this.storage.put(getRep(this.workList.get(i).to), getRep(this.workList.get(i).from));

        this.totalEdges.add(this.workList.get(i));

      }

    }

    // Adds all the connections for each cell
    for (int y = 0; y < this.boardX; y += 1) {
      for (int x = 0; x < this.boardY; x += 1) {
        for (Edge e : this.totalEdges) {
          if (this.cellBoard.get(y).get(x).equals(e.from)
              || this.cellBoard.get(y).get(x).equals(e.to)) {
            this.cellBoard.get(y).get(x).connections.add(e);

          }
        }
      }
    }

  }

  // Helper designed to get the representative of a
  // singular cell
  public Cell getRep(Cell c) {

    while (!storage.get(c).equals(c)) {
      c = storage.get(c);
    }
    return c;

  }

  // Updates the maze depending on various
  // actions using the tickrate.
  public void onTick() {

    makeMaze();
  }

  // Traces to the end cell in the maze.
  void gotoEnd() {
    Cell cur = this.searchPath.remove(0);

    if (!cur.equals(this.cellBoard.get(0).get(0))) {

      cur.color = Color.cyan;
    }

    makeMaze();
  }

   // We have reached the end, signal to trace back.
  void reachedEnd() {
    Cell cur = this.searchPath.remove(0);
    cur.color = Color.cyan;

    if (this.goal.left && this.cellBoard.get(goal.x - 1).get(goal.y) != null
        && this.cellBoard.get(goal.x - 1).get(goal.y).color == Color.cyan) {
      this.goal.previous = this.cellBoard.get(goal.x - 1).get(goal.y);
    }

    else if (this.goal.up && this.cellBoard.get(goal.x).get(goal.y - 1) != null
        && this.cellBoard.get(goal.x).get(goal.y - 1).color == Color.cyan) {

      this.goal.previous = this.cellBoard.get(goal.x).get(goal.y - 1);
    }
    else {

      this.goal.previous = cur;
    }

    this.traversed = true;
    makeMaze();
  }

  // Handles key events for various searches/resets for the maze.
  public void onKeyEvent(String inp) {

    if (inp.equals("b")) {
      this.goal = this.cellBoard.get(this.boardX - 1).get(this.boardY - 1);
      this.searchPath = this.graph.breadthFirstSearch(this.cellBoard.get(0).get(0),
          this.cellBoard.get(this.boardX - 1).get(this.boardY - 1));
    }

    else if (inp.equals("d")) {
      this.searchPath = this.graph.depthFirstSearch(this.cellBoard.get(0).get(0),
          this.cellBoard.get(this.boardX - 1).get(this.boardY - 1));
    }

    else if (inp.equals("r")) {
      this.cellBoard = new ArrayList<ArrayList<Cell>>();
      this.workList = new ArrayList<Edge>();
      this.totalEdges = new ArrayList<Edge>();
      this.storage = new HashMap<Cell, Cell>();
      this.searchPath = new ArrayList<Cell>();
      initializeCells();
      createWorklist();
      workListSort();
      mergeTrees();
      setEdgePresence();
      this.makeMaze();
      this.goal = this.cellBoard.get(boardX - 1).get(boardY - 1);
      this.traversed = false;
    }

  }

  // Traces the direct path from the end, back to the start
  // of the maze.
  void traceback() {

    this.goal = this.goal.previous;

    if (!this.goal.equals(this.cellBoard.get(0).get(0))) {

      this.goal.color = Color.yellow;
    }
    makeMaze();
  }

}
