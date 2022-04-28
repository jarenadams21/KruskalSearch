// Graph to find different searches on two given 
// cells.
class Graph {

  // Graph Constructor.
  Graph() {
  }

  // Find a path between two cells using DFS
  ArrayList<Cell> depthFirstSearch(Cell from, Cell to) {
    return this.createPath(from, to, new Stack<Cell>());
  }

  // Find a path between two cells using BFS.
  ArrayList<Cell> breadthFirstSearch(Cell from, Cell to) {
    return this.createPath(from, to, new Queue<Cell>());
  }

  // Finds the path using either a Stack or Queue.
  ArrayList<Cell> createPath(Cell from, Cell to, ICollection<Cell> worklist) {

    ArrayList<Cell> path = new ArrayList<Cell>();

    // Initially add the first cell on the board.
    worklist.add(from);

    while (worklist.size() > 0) {
      Cell next = worklist.remove();
      if (next == to) {
        return path;
      }
      else if (path.contains(next)) {
        // Cell is already in path/searched, skip.
      }
      else {
        for (Edge e : next.connections) {
          worklist.add(e.from);
          worklist.add(e.to);
          if (path.contains(e.from)) {
            // Indicates where I came from for back tracing.
            next.previous = e.from;
          }
          else if (path.contains(e.to)) {
            // Indicates where I came from for back tracing.
            next.previous = e.to;
          }
        }

        path.add(next);
      }
    }
    return path;
  }

}
