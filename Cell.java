// Represents an individual cell
class Cell {
  int x;
  int y;
  int scale;

  Color color;

  // Potential edge directions for a cell / Boundaries
  boolean up;
  boolean down;
  boolean left;
  boolean right;

  // Has this cell already been visited by the search?
  boolean hasReached;

  // List of edge connections for this cell.
  ArrayList<Edge> connections;

  // Stores the previously visited cell in a traversal.
  Cell previous;

  // Cell constructor
  Cell(int x, int y, int scale) {
    this.x = x;
    this.y = y;
    this.scale = scale;
    this.color = Color.GRAY;

    this.up = false;
    this.down = false;
    this.left = false;
    this.right = false;
    this.hasReached = false;
    this.previous = null;

    this.connections = new ArrayList<Edge>();
  }

  // Convenience constructor that sets all edges to true.
  Cell(int x, int y, int scale, boolean edge) {
    this.x = x;
    this.y = y;
    this.scale = scale;
    this.color = Color.GRAY;

    this.up = edge;
    this.down = edge;
    this.left = edge;
    this.right = edge;
    this.hasReached = false;
    this.connections = new ArrayList<Edge>();
  }

  // Overridden equality method for Cells
  public boolean equals(Object o) {

    if (!(o instanceof Cell)) {
      return false;
    }
    else {

      Cell inp = (Cell) o;
      return inp.x == this.x && inp.y == this.y && inp.hashCode() == this.hashCode();
    }
  }

  // Overridden hashCode for a Cell
  public int hashCode() {

    return this.x + this.y * 10000;
  }

  // Visually represents the cell as a WorldImage.
  public WorldImage draw() {

    if (!this.hasReached) {
      return new RectangleImage(this.scale, this.scale, OutlineMode.SOLID, this.color);
    }

    else {

      return new RectangleImage(this.scale - 1, this.scale - 1, OutlineMode.SOLID, Color.CYAN);
    }

  }

}
