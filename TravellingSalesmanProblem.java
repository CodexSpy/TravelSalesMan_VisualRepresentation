import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.*;

public class TravellingSalesmanProblem extends JPanel {

    private static final int INF = Integer.MAX_VALUE / 2;
    private static final int CITY_RADIUS = 15;
    private static final int PADDING = 60;
    private static final Font EDGE_FONT = new Font("SansSerif", Font.PLAIN, 12);
    private static final Font CITY_FONT = new Font("SansSerif", Font.BOLD, 14);
    private static final Font COST_FONT = new Font("SansSerif", Font.BOLD, 18);
    private static final int TEXT_PADDING = 4;

    private int[][] graph;
    private java.util.List<Integer> path;
    private int minCost;

    public TravellingSalesmanProblem(int[][] graph) {
        this.graph = graph;
        this.path = new ArrayList<>();
        this.minCost = INF;
        setPreferredSize(new Dimension(800, 800));
    }

    public static void main(String[] args) {
        int[][] graph = {
            { 0, 10, 15, 20 },
            { 10, 0, 35, 25 },
            { 15, 35, 0, 30 },
            { 20, 25, 30, 0 }
        };
        printGraphMatrix(graph);
        TravellingSalesmanProblem tspPanel = new TravellingSalesmanProblem(graph);
        tspPanel.solveTSP();

        JFrame frame = new JFrame("Traveling Salesman Problem");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new JScrollPane(tspPanel));
        frame.pack();
        frame.setVisible(true);
    }

    private static void printGraphMatrix(int[][] graph) {
        System.out.println("Graph Weight Matrix:");
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                System.out.print(String.format("%4d", graph[i][j]) + " ");
            }
            System.out.println();
        }
    }

    private void solveTSP() {
        int n = graph.length;
        int[][] dp = new int[1 << n][n];
        for (int[] row : dp) {
            Arrays.fill(row, INF);
        }
        dp[1][0] = 0;

        for (int mask = 1; mask < (1 << n); mask++) {
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    for (int j = 0; j < n; j++) {
                        if ((mask & (1 << j)) == 0) {
                            int nextMask = mask | (1 << j);
                            dp[nextMask][j] = Math.min(dp[nextMask][j], dp[mask][i] + graph[i][j]);
                        }
                    }
                }
            }
        }

        int lastCity = -1;
        for (int i = 1; i < n; i++) {
            int cost = dp[(1 << n) - 1][i] + graph[i][0];
            if (cost < minCost) {
                minCost = cost;
                lastCity = i;
            }
        }

        // find the path
        int mask = (1 << n) - 1;
        int pos = lastCity;
        while (mask != 1) {
            path.add(pos);
            int prevMask = mask ^ (1 << pos);
            for (int i = 0; i < n; i++) {
                if (dp[prevMask][i] + graph[i][pos] == dp[mask][pos]) {
                    pos = i;
                    break;
                }
            }
            mask = prevMask;
        }
        path.add(0);
        Collections.reverse(path);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int n = graph.length;
        int width = getWidth() - 2 * PADDING;
        int height = getHeight() - 2 * PADDING;
        int radius = Math.min(width, height) / 2 - CITY_RADIUS;
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        Point[] cityPositions = new Point[n];
        for (int i = 0; i < n; i++) {
            double angle = 2 * Math.PI * i / n;
            int x = (int) (centerX + radius * Math.cos(angle));
            int y = (int) (centerY + radius * Math.sin(angle));
            cityPositions[i] = new Point(x, y);
        }

        g2d.setColor(Color.LIGHT_GRAY);
        g2d.setFont(EDGE_FONT);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                g2d.draw(new Line2D.Double(cityPositions[i], cityPositions[j]));

                String weight = String.valueOf(graph[i][j]);
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(weight);
                int textHeight = fm.getAscent();
                int midX = (cityPositions[i].x + cityPositions[j].x) / 2;
                int midY = (cityPositions[i].y + cityPositions[j].y) / 2;

                g2d.setColor(Color.WHITE);
                g2d.fillRect(midX - textWidth / 2 - TEXT_PADDING, midY - textHeight / 2 - TEXT_PADDING,
                             textWidth + 2 * TEXT_PADDING, textHeight + 2 * TEXT_PADDING);

              
                g2d.setColor(Color.BLACK);
                g2d.drawString(weight, midX - textWidth / 2, midY + textHeight / 2);
            }
        }

        g2d.setColor(Color.GREEN);
        g2d.setStroke(new BasicStroke(2));
        for (int i = 0; i < path.size() - 1; i++) {
            g2d.draw(new Line2D.Double(cityPositions[path.get(i)], cityPositions[path.get(i + 1)]));
        }

       
        g2d.setColor(Color.RED);
        g2d.setFont(CITY_FONT);
        for (int i = 0; i < n; i++) {
            g2d.fillOval(cityPositions[i].x - CITY_RADIUS, cityPositions[i].y - CITY_RADIUS, 2 * CITY_RADIUS, 2 * CITY_RADIUS);
            g2d.setColor(Color.BLACK);
            String cityLabel = "City " + i;
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(cityLabel);
            int textHeight = fm.getAscent();
            g2d.drawString(cityLabel, cityPositions[i].x - textWidth / 2, cityPositions[i].y - CITY_RADIUS - 5);
        }

        g2d.setColor(Color.BLUE);
        g2d.setFont(COST_FONT);
        g2d.drawString("Minimum Cost: " + minCost, PADDING, getHeight() - PADDING / 2);
    }
}
