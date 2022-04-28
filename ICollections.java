// Represents a collection of objects (queue/stack)
interface ICollection<T> {

  // Adds an item
  void add(T item);

  // Removes an item
  T remove();

  // Returns the size
  int size();
}

// Queue used in Breadth-first Search (Array list Implementation)
class Queue<T> implements ICollection<T> {
  ArrayList<T> items;

  // Queue Constructor
  Queue() {
    this.items = new ArrayList<T>();
  }

  // Adds an item to the end of the queue.
  public void add(T item) {
    this.items.add(item);
  }

  // Removes the first item in the queue.
  public T remove() {
    return this.items.remove(0);
  }

  // Returns the size
  public int size() {
    return this.items.size();
  }
}

// Stack used in Depth-first Search (Array list Implementation)
class Stack<T> implements ICollection<T> {
  ArrayList<T> items;

  // Stack constructor
  Stack() {
    this.items = new ArrayList<T>();
  }

  // Adds an item to the front of the stack.
  public void add(T item) {
    this.items.add(0, item);
  }

  // Removes the first item.
  public T remove() {
    return this.items.remove(0);
  }

  // Returns the size
  public int size() {
    return this.items.size();
  }
}
