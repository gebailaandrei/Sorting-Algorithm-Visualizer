import javax.swing.*;
import java.awt.*;

public class SortVisualizer extends JPanel {

    private int[] array;
    private int compareLow = -1, compareHigh = -1;
    private int moveLow = -1, moveHigh = -1;
    private final int barWidth;
    private final int height;
    private String status = "";

    // New counters
    private int accesses = 0;
    private int comparisons = 0;
    private int moves = 0;

    public SortVisualizer(int[] array, int width, int height) {
        this.array = array;
        this.height = height;
        this.barWidth = width / array.length;
        setPreferredSize(new Dimension(width, height));
    }

    public void updateArray(int[] array) {
        this.array = array;
        resetCounters();
    }

    public void highlightCompare(int low, int high) {
        this.compareLow = low;
        this.compareHigh = high;
        this.moveLow = -1;
        this.moveHigh = -1;
        repaint();
    }

    public void highlightMove(int low, int high) {
        this.moveLow = low;
        this.moveHigh = high;
        this.compareLow = -1;
        this.compareHigh = -1;
        repaint();
    }

    public void clearHighlights() {
        compareLow = compareHigh = -1;
        moveLow = moveHigh = -1;
        repaint();
    }

    public void setStatus(String text) {
        this.status = text;
        repaint();
    }

    // New methods to update counters
    public void incrementAccesses(int count) {
        this.accesses += count;
        repaint();
    }

    public void incrementComparisons(int count) {
        this.comparisons += count;
        repaint();
    }

    public void incrementMoves(int count) {
        this.moves += count;
        repaint();
    }

    public void resetCounters() {
        this.accesses = 0;
        this.comparisons = 0;
        this.moves = 0;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(new GradientPaint(0, 0, new Color(20, 20, 40), 0, height, new Color(10, 10, 30)));
        g2.fillRect(0, 0, getWidth(), getHeight());

        for (int i = 0; i < array.length; i++) {
            int val = array[i];
            int x = i * barWidth;
            int y = height - val;

            Color baseColor = new Color(100, 149, 237);
            if (i == moveLow || i == moveHigh)
                baseColor = new Color(60, 179, 113); // green for move
            else if (i == compareLow || i == compareHigh)
                baseColor = new Color(255, 165, 0); // orange for compare

            g2.setPaint(new GradientPaint(x, y, baseColor.brighter(), x, height, baseColor.darker()));
            g2.fillRoundRect(x, y, barWidth - 2, val, 6, 6);
        }

        g2.setFont(new Font("SansSerif", Font.BOLD, 18));
        g2.setColor(Color.WHITE);
        g2.drawString(status, 20, 30);

        // Draw legend
        g2.setFont(new Font("SansSerif", Font.PLAIN, 14));
        g2.setColor(Color.ORANGE);
        g2.drawString("Comparing", getWidth() - 110, 30);
        g2.setColor(new Color(60, 179, 113));
        g2.drawString("Moving", getWidth() - 110, 50);

        // Draw stats below the legend
        g2.setColor(Color.WHITE);
        g2.drawString("Accesses: " + accesses, getWidth() - 130, 75);
        g2.drawString("Comparisons: " + comparisons, getWidth() - 130, 95);
        g2.drawString("Moves: " + moves, getWidth() - 130, 115);
    }
}
