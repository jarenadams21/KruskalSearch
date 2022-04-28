// Represents an edge from one cell to another,
// with an associated weight.
// N-1 edges are needed to connect entire graph.
class Edge {
  Cell from;
  Cell to;
  int weight;
  Random rand;

  // Constructor for an Edge.
  Edge(Cell from, Cell to, int boardX) {

    this.from = from;
    this.to = to;

    // Randomized weight given the board X size.
    rand = new Random();
    this.weight = rand.nextInt(boardX);
  }

  // Convenience constructor for an Edge to set weight.
  Edge(Cell from, Cell to, int boardX, int weight) {

    this.from = from;
    this.to = to;

    this.weight = weight;
  }

  // Convenience constructor for an Edge that takes in a random object.
  Edge(Cell from, Cell to, int boardX, Random rand) {

    this.from = from;
    this.to = to;

    // Randomized weight given the board X size.
    this.rand = rand;
    this.weight = rand.nextInt(boardX);
  }

}

//Function Object to compare edge weights.
class EdgeDifference implements Comparator<Edge> {

  // Compares two edges by their weight, returning
  // a relevant indicator value based on the evaluation
  public int compare(Edge e1, Edge e2) {
    if (e1.weight < e2.weight) {
      return -1;
    }

    else if (e1.weight == e2.weight) {
      return 0;
    }

    else {
      return 1;
    }
  }
}
