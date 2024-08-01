# TravelSalesMan_VisualRepresentation

```markdown
# Traveling Salesman Problem Visualization

This project provides a graphical representation of the Traveling Salesman Problem (TSP) using Java Swing. It visualizes the solution to the TSP, including the minimum cost path and edge weights, in a graphical user interface.

## Project Overview

The Traveling Salesman Problem is a classic optimization problem where the goal is to find the shortest possible route that visits a set of cities and returns to the origin city. This project implements a solution using dynamic programming and displays the results using Java Swing.

## Features

- **Graph Visualization**: Displays a graph of cities and the edges connecting them.
- **Shortest Path Highlighting**: Shows the shortest path connecting all cities with a green line.
- **Edge Weights Display**: Annotates edges with their weights.
- **Dynamic Programming Solution**: Solves the TSP using dynamic programming.
- **Cost Display**: Displays the minimum cost of the solution.

## How to Run

1. **Clone the Repository**

   ```bash
   git clone https://github.com/yourusername/traveling-salesman-visualization.git
   cd traveling-salesman-visualization
   ```

2. Compile and Run

   Ensure you have JDK installed. Compile and run the project using the following commands:

   ```bash
   javac TravelingSalesmanProblem.java
   java TravelingSalesmanProblem
   ```

3. View the Application

   The application will open a window displaying the graph, shortest path, and minimum cost.

## Code Explanation

- **`TravelingSalesmanProblem.java`**: Contains the main logic for solving the TSP and rendering the graphical representation.
  - `solveTSP()`: Uses dynamic programming to compute the shortest path.
  - `paintComponent(Graphics g)`: Handles the drawing of the graph, edges, and shortest path.

## Dependencies

- Java Development Kit (JDK) 8 or higher.

## Acknowledgments

- Inspired by classical algorithms for solving the Traveling Salesman Problem.
- Based on Java Swing for graphical user interface creation.

## Contributing

Contributions are welcome! Please open an issue or submit a pull request to suggest improvements or fix bugs.
