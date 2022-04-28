// Examples & Tests for Kruskal's Algorithm maze
// and associated components 
// Multiple examples were created for each to avoid compiler confusion
// as tests would accidentally call on previously worked upon worlds.
class ExampleKruskal {

  // Example cells
  Cell cell1;
  Cell cell2;
  Cell cell3;
  Cell cell4;
  Cell cell5;
  Cell k1cell00;
  Cell k1cell01;
  Cell k1cell02;
  Cell k1cell10;
  Cell k1cell11;
  Cell k1cell12;
  Cell k1cell20;
  Cell k1cell21;
  Cell k1cell22;

  // Example edges
  Edge edge1;
  Edge edge2;
  Edge edge3;
  Edge edge4;
  Edge edge5;

  // Example Board
  ArrayList<Cell> k1col0;
  ArrayList<Cell> k1col1;
  ArrayList<Cell> k1col2;
  ArrayList<ArrayList<Cell>> k1board;
  KruskalMaze kruskal1;

  // Initializes test variables and world states
  void Init() {
    cell1 = new Cell(1, 1, 4);
    cell2 = new Cell(2, 2, 4);
    cell3 = new Cell(3, 3, 4);
    cell4 = new Cell(4, 4, 4);
    cell5 = new Cell(5, 5, 4);
    edge1 = new Edge(cell1, cell2, 80, new Random(3));
    edge2 = new Edge(cell2, cell3, 80, new Random(4));
    edge3 = new Edge(cell3, cell4, 80, new Random(5));
    edge4 = new Edge(cell4, cell5, 80, new Random(5));
    edge5 = new Edge(cell4, cell5, 80, new Random(6));

    k1cell00 = new Cell(0, 0, 19);
    k1cell01 = new Cell(0, 1, 19);
    k1cell02 = new Cell(0, 2, 19);
    k1cell10 = new Cell(1, 0, 19);
    k1cell11 = new Cell(1, 1, 19);
    k1cell12 = new Cell(1, 2, 19);
    k1cell20 = new Cell(2, 0, 19);
    k1cell21 = new Cell(2, 1, 19);
    k1cell22 = new Cell(2, 2, 19);

    k1col0 = new ArrayList<Cell>(Arrays.asList(k1cell00, k1cell01, k1cell02));
    k1col1 = new ArrayList<Cell>(Arrays.asList(k1cell10, k1cell11, k1cell12));
    k1col2 = new ArrayList<Cell>(Arrays.asList(k1cell20, k1cell21, k1cell22));
    k1board = new ArrayList<ArrayList<Cell>>(Arrays.asList(k1col0, k1col1, k1col2));

  }

  // testing the equals method for cells
  void testEquals(Tester t) {

    Init();
    // different type
    String hellostring = new String("hello");
    t.checkExpect(cell1.equals(hellostring), false);
    // different cell
    t.checkExpect(cell2.equals(cell1), false);
    // same cell different memory location
    Cell cell1b = new Cell(1, 1, 4);
    t.checkExpect(cell1.equals(cell1b), true);
    // same exact cell
    Cell cell1c = cell1;
    t.checkExpect(cell1.equals(cell1c), true);
  }

  // testing the hashCode method for cells
  void testHashCode(Tester t) {
    Init();
    // test calculating the hashcode of 5 example cells
    t.checkExpect(cell1.hashCode(), 10001);
    t.checkExpect(cell2.hashCode(), 20002);
    t.checkExpect(cell3.hashCode(), 30003);
    t.checkExpect(cell4.hashCode(), 40004);
    t.checkExpect(cell5.hashCode(), 50005);
  }

  // testing the draw method for cells
  void testDraw(Tester t) {
    Init();
    // test drawing 5 example cells
    t.checkExpect(cell1.draw(), new RectangleImage(4, 4, OutlineMode.SOLID, Color.GRAY));
    t.checkExpect(cell2.draw(), new RectangleImage(4, 4, OutlineMode.SOLID, Color.GRAY));
    t.checkExpect(cell3.draw(), new RectangleImage(4, 4, OutlineMode.SOLID, Color.GRAY));
    t.checkExpect(cell4.draw(), new RectangleImage(4, 4, OutlineMode.SOLID, Color.GRAY));
    t.checkExpect(cell5.draw(), new RectangleImage(4, 4, OutlineMode.SOLID, Color.GRAY));
  }

  // testing the compare method for edges
  void testCompare(Tester t) {
    Init();
    EdgeDifference edgediff = new EdgeDifference();
    t.checkExpect(edgediff.compare(edge1, edge2), 1);
    t.checkExpect(edgediff.compare(edge2, edge3), -1);
    t.checkExpect(edgediff.compare(edge3, edge4), 0);
    t.checkExpect(edgediff.compare(edge4, edge5), -1);
  }

  // Tests for class Queue
  void testQueueAddRemoveSize(Tester t) {
    Queue<Integer> queue1 = new Queue<Integer>();
    t.checkExpect(queue1.items.size(), 0);
    queue1.add(3);
    queue1.add(4);
    queue1.add(5);
    queue1.add(6);
    t.checkExpect(queue1.items.size(), 4);
    t.checkExpect(queue1.remove(), 3);
    t.checkExpect(queue1.items.get(0), 4);
    t.checkExpect(queue1.items.size(), 3);
    t.checkExpect(queue1.remove(), 4);
    t.checkExpect(queue1.items.get(0), 5);
    t.checkExpect(queue1.items.size(), 2);
    t.checkExpect(queue1.size(), 2);
    t.checkExpect(queue1.items.size(), 2);
    queue1.add(7);
    t.checkExpect(queue1.size(), 3);
    t.checkExpect(queue1.items.size(), 3);
  }

  // Tests for class Stack
  void testStackAddRemoveSize(Tester t) {
    Stack<Integer> stack1 = new Stack<Integer>();
    t.checkExpect(stack1.items.size(), 0);
    stack1.add(6);
    stack1.add(5);
    stack1.add(4);
    stack1.add(3);
    t.checkExpect(stack1.items.size(), 4);
    t.checkExpect(stack1.remove(), 3);
    t.checkExpect(stack1.items.get(0), 4);
    t.checkExpect(stack1.items.size(), 3);
    t.checkExpect(stack1.remove(), 4);
    t.checkExpect(stack1.items.get(0), 5);
    t.checkExpect(stack1.items.size(), 2);
    t.checkExpect(stack1.size(), 2);
    t.checkExpect(stack1.items.size(), 2);
    stack1.add(7);
    t.checkExpect(stack1.size(), 3);
    t.checkExpect(stack1.items.size(), 3);
  }

  // Tests for class Graph
  void testGraph(Tester t) {
    // Edge(Cell from, Cell to, int boardX, int weight)

    Cell c1 = new Cell(0, 0, 4);
    Cell c2 = new Cell(0, 1, 4);
    Cell c3 = new Cell(1, 0, 4);
    Cell c4 = new Cell(1, 1, 4);
    Graph g1 = new Graph();
    t.checkExpect(g1.createPath(c1, c4, new Stack<Cell>()), new ArrayList<Cell>(Arrays.asList(c1)));
    t.checkExpect(g1.breadthFirstSearch(c1, c4), new ArrayList<Cell>(Arrays.asList(c1)));
    t.checkExpect(g1.createPath(c1, c4, new Queue<Cell>()), new ArrayList<Cell>(Arrays.asList(c1)));
    t.checkExpect(g1.depthFirstSearch(c1, c4), new ArrayList<Cell>(Arrays.asList(c1)));
    t.checkExpect(g1.createPath(c2, c3, new Stack<Cell>()), new ArrayList<Cell>(Arrays.asList(c2)));
    t.checkExpect(g1.breadthFirstSearch(c2, c3), new ArrayList<Cell>(Arrays.asList(c2)));
    t.checkExpect(g1.createPath(c2, c3, new Queue<Cell>()), new ArrayList<Cell>(Arrays.asList(c2)));
    t.checkExpect(g1.depthFirstSearch(c2, c3), new ArrayList<Cell>(Arrays.asList(c2)));
  }

  // Tests for the makeScene method of a KruskalMaze
  void testMakeMaze(Tester t) {

    WorldScene testScene = new WorldScene(1200, 800);

    Cell c1 = new Cell(0, 0, 19, true);
    c1.color = Color.PINK;

    Cell c2 = new Cell(0, 1, 19, true);
    Cell c3 = new Cell(1, 0, 19, true);

    Cell c4 = new Cell(1, 1, 19, true);
    c4.color = Color.MAGENTA;

    ArrayList<Cell> col1 = new ArrayList<Cell>();
    ArrayList<Cell> col2 = new ArrayList<Cell>();
    col1.add(c1);
    col1.add(c2);
    col2.add(c3);
    col2.add(c4);
    ArrayList<ArrayList<Cell>> tBoard = new ArrayList<ArrayList<Cell>>();
    tBoard.add(col1);
    tBoard.add(col2);

    KruskalMaze kMaze = new KruskalMaze(2, tBoard);
    kMaze.cellScale = 19;

    testScene.placeImageXY(c1.draw(), 19 * 0 + 12, 19 * 0 + 12);
    testScene.placeImageXY(c2.draw(), 19 * 0 + 12, 19 * 1 + 12);
    testScene.placeImageXY(c3.draw(), 19 * 1 + 12, 19 * 0 + 12);
    testScene.placeImageXY(c4.draw(), 19 * 1 + 12, 19 * 1 + 12);

    t.checkExpect(kMaze.makeMaze(), testScene);
  }

  // Tests for the initialization of cells.
  void testInitializeCells(Tester t) {

    KruskalMaze tKrusk1 = new KruskalMaze(100, 60);
    t.checkExpect(tKrusk1.cellScale, 11);
    t.checkExpect(tKrusk1.allModifier, 12);
    t.checkExpect(tKrusk1.upLeftModifier, 6);
    t.checkExpect(tKrusk1.rightDownModifier, 17);

    KruskalMaze tKrusk2 = new KruskalMaze(45, 45);
    t.checkExpect(tKrusk2.cellScale, 13);
    t.checkExpect(tKrusk2.allModifier, 11);
    t.checkExpect(tKrusk2.upLeftModifier, 4);
    t.checkExpect(tKrusk2.rightDownModifier, 17);

    KruskalMaze tKrusk3 = new KruskalMaze(10, 10);
    t.checkExpect(tKrusk3.cellScale, 19);
    t.checkExpect(tKrusk3.allModifier, 11);
    t.checkExpect(tKrusk3.upLeftModifier, 1);
    t.checkExpect(tKrusk3.rightDownModifier, 20);

    KruskalMaze tKrusk4 = new KruskalMaze(5);
    tKrusk4.cellScale = 19;

    // Check if each cell was colored correctly
    for (int i = 0; i < 10; i++) {

      for (int j = 0; j < 10; j++) {

        if (i == 0 && j == 0) {

          t.checkExpect(tKrusk3.cellBoard.get(i).get(j).color, Color.GREEN);
        }

        else if (i == 9 && j == 9) {
          t.checkExpect(tKrusk3.cellBoard.get(i).get(j).color, Color.MAGENTA);
        }

        else {

          t.checkExpect(tKrusk3.cellBoard.get(i).get(j).color, Color.GRAY);
        }
      }
    }
    // Ensures correct cellBoard dimensions
    t.checkExpect(tKrusk1.cellBoard.size(), 100);
    t.checkExpect(tKrusk1.cellBoard.get(1).size(), 60);
    t.checkExpect(tKrusk2.cellBoard.size(), 45);
    t.checkExpect(tKrusk2.cellBoard.get(1).size(), 45);

    // Checks if a cell was correctly placed in the storage hash
    // as a representative for itself.
    tKrusk4.initializeCells();
    t.checkExpect(tKrusk4.cellBoard.get(0).get(0), tKrusk4.cellBoard.get(0).get(0));
  }

  // Tests for createWorklist
  void testCreateWorklist(Tester t) {

    KruskalMaze kruskal12 = new KruskalMaze(5);
    kruskal12.initializeCells();
    kruskal12.createWorklist();

    t.checkExpect(kruskal12.workList.get(0).from, kruskal12.cellBoard.get(0).get(0));
    t.checkExpect(kruskal12.workList.get(0).to, kruskal12.cellBoard.get(1).get(0));

    t.checkExpect(kruskal12.workList.get(4).to, kruskal12.cellBoard.get(1).get(2));

  }

  // Tests for setEdgePresence
  void testSetEdgePresence(Tester t) {

    Init();

    KruskalMaze tKrusk8 = new KruskalMaze(8);
    Edge e1 = new Edge(cell1, new Cell(1, 2, 4), 6);

    tKrusk8.totalEdges.add(edge1);
    tKrusk8.totalEdges.add(edge2);
    tKrusk8.totalEdges.add(e1);
    tKrusk8.totalEdges.add(edge3);

    tKrusk8.setEdgePresence();

    t.checkExpect(tKrusk8.totalEdges.get(0).from.up, false);
    t.checkExpect(tKrusk8.totalEdges.get(0).from.down, true);
    t.checkExpect(e1.to.up, true);
  }

  // Sorts the work list based on weight.
  void workListSort(Tester t) {

    Init();

    KruskalMaze tKrusk9 = new KruskalMaze(10);
    Edge tEdge1 = new Edge(cell1, cell2, 10, 5);
    Edge tEdge2 = new Edge(cell2, cell3, 10, 8);
    Edge tEdge3 = new Edge(cell3, cell4, 10, 1);
    Edge tEdge4 = new Edge(cell4, cell5, 10, 4);

    tKrusk9.workList.add(tEdge1);
    tKrusk9.workList.add(tEdge2);
    tKrusk9.workList.add(tEdge3);
    tKrusk9.workList.add(tEdge4);

    tKrusk9.workListSort();

    t.checkExpect(tKrusk9.workList.get(0), tEdge3);
    t.checkExpect(tKrusk9.workList.get(3), tEdge2);
  }

  // Tests for getRep and mergeTrees
  void testGetRepMerge(Tester t) {

    Init();
    KruskalMaze kruskal10 = new KruskalMaze(20);
    kruskal10.initializeCells();

    kruskal10.workList.add(edge1);
    kruskal10.workList.add(edge2);
    kruskal10.workList.add(edge3);
    kruskal10.workList.add(edge4);
    kruskal10.workList.add(edge5);

    t.checkExpect(kruskal10.getRep(kruskal10.workList.get(0).to), cell2);

    kruskal10.mergeTrees();

    t.checkExpect(kruskal10.getRep(kruskal10.workList.get(0).to), kruskal10.workList.get(0).from);
  }

  // Tests for the makeScene() method.
  void testMakeScene(Tester t) {

    KruskalMaze kruskal11 = new KruskalMaze(2, 2);
    KruskalMaze kruskal12 = new KruskalMaze(2, 2);

    kruskal11.searchPath.add(kruskal11.cellBoard.get(1).get(1));

    kruskal11.makeScene();

    // Check if properly checked for reachedEnd
    t.checkExpect(kruskal11.traversed, true);

    // Check if traversing to end, but haven't reached.
    kruskal12.searchPath.add(kruskal12.cellBoard.get(1).get(0));
    kruskal12.searchPath.add(kruskal12.cellBoard.get(1).get(1));

    kruskal12.makeScene();

    t.checkExpect(kruskal12.traversed, false);
  }

  // Tests for reaching the end of the maze.
  void testGotoEnd(Tester t) {

    KruskalMaze kruskal12 = new KruskalMaze(2);
    kruskal12.initializeCells();

    WorldScene scene1 = kruskal12.makeMaze();

    Cell cell1 = kruskal12.cellBoard.get(0).get(1);

    // Before cell was added to search pathway.
    t.checkExpect(cell1.color, Color.GRAY);

    kruskal12.searchPath.add(cell1);

    kruskal12.gotoEnd();
    WorldScene scene2 = kruskal12.makeMaze();

    // After cell was added to search pathway.
    t.checkExpect(scene1 != scene2, true);
    t.checkExpect(cell1.color, Color.cyan);

  }

  // Tests for when we have reached the end of the search.
  void testReachedEnd(Tester t) {

    ArrayList<ArrayList<Cell>> reachedBoard = new ArrayList<ArrayList<Cell>>();

    // Cells to test for the reachedEnd method.
    Cell c1 = new Cell(0, 0, 19);
    Cell c2 = new Cell(0, 1, 19);
    Cell c3 = new Cell(1, 0, 19);
    Cell c4 = new Cell(1, 1, 19);
    ArrayList<Cell> c1c2holder = new ArrayList<Cell>();
    ArrayList<Cell> c3c4holder = new ArrayList<Cell>();

    c1c2holder.add(c1);
    c1c2holder.add(c2);

    c3c4holder.add(c3);
    c3c4holder.add(c4);

    reachedBoard.add(c1c2holder);
    reachedBoard.add(c3c4holder);

    // Access to last cell from top right
    c1.right = true;
    c1.down = true;
    c2.down = true;

    c1.previous = null;
    c2.previous = c1;
    c3.previous = c1;
    c4.previous = c3;

    KruskalMaze kruskal13 = new KruskalMaze(2, reachedBoard);

    kruskal13.searchPath.add(c3);
    kruskal13.reachedEnd();

    t.checkExpect(kruskal13.searchPath.size(), 0);

    t.checkExpect(kruskal13.goal.previous, c3);
    t.checkExpect(kruskal13.goal.previous.previous, c1);
    t.checkExpect(kruskal13.traversed, true);

    // Access to last cell from bottom left
    kruskal13.searchPath.add(c2);
    c2.down = false;
    c2.right = true;
    kruskal13.traversed = false;
    kruskal13.reachedEnd();

    t.checkExpect(kruskal13.goal.previous, c2);
    t.checkExpect(kruskal13.goal.previous.previous, c1);
    t.checkExpect(kruskal13.traversed, true);
    t.checkExpect(c2.color, Color.cyan);

  }

  // Tests that the state has changed after an onTick dependent function
  void testOnTick(Tester t) {

    KruskalMaze kruskal15 = new KruskalMaze(15, 15);
    WorldScene sample1 = kruskal15.makeScene();

    kruskal15.onKeyEvent("b");
    kruskal15.onTick();

    t.checkExpect(sample1 != kruskal15.makeMaze(), true);
  }

  // Tests to trace back the direct solution for the maze.
  void testTraceback(Tester t) {

    ArrayList<ArrayList<Cell>> traceback = new ArrayList<ArrayList<Cell>>();

    // Cells to test for the reachedEnd method.
    Cell c1 = new Cell(0, 0, 19);
    Cell c2 = new Cell(0, 1, 19);
    Cell c3 = new Cell(1, 0, 19);
    Cell c4 = new Cell(1, 1, 19);
    ArrayList<Cell> c1c2holder = new ArrayList<Cell>();
    ArrayList<Cell> c3c4holder = new ArrayList<Cell>();

    c1c2holder.add(c1);
    c1c2holder.add(c2);

    c3c4holder.add(c3);
    c3c4holder.add(c4);

    traceback.add(c1c2holder);
    traceback.add(c3c4holder);

    // Access to last cell from top right
    c1.right = true;
    c1.down = true;
    c2.down = true;

    c1.previous = null;
    c2.previous = c1;
    c3.previous = c1;
    c4.previous = c3;

    KruskalMaze kruskal20 = new KruskalMaze(2, traceback);

    t.checkExpect(c3.color, Color.GRAY);

    kruskal20.traceback();

    t.checkExpect(c3.color, Color.yellow);
  }

  // Tests for handling various key events on the maze.
  void testOnKey(Tester t) {
    kruskal1 = new KruskalMaze(40, 40);
    kruskal1.onKeyEvent("d");
    kruskal1.reachedEnd();
    t.checkExpect(kruskal1.traversed, true);
    kruskal1.onKeyEvent("r");
    t.checkExpect(kruskal1.traversed, false);
    kruskal1.onKeyEvent("b");
    kruskal1.reachedEnd();
    t.checkExpect(kruskal1.traversed, true);
  }

  void testGame(Tester t) {
    KruskalMaze km = new KruskalMaze(100, 60);
    km.bigBang(1200, 800, 0.01);
  }
}
