import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class Main extends JFrame {
    private final SortVisualizer visualizer;
    private final SortController controller;
    private SortAlgorithm currentAlgorithm;
    private int[] array;
    private Thread sortingThread;

    public Main() {
        setTitle("Sorting Visualizer");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        int size = 100;
        int width = 800;
        int height = 400;

        array = generateRandomArray(size, height - 20);
        visualizer = new SortVisualizer(array, width, height);
        controller = new SortController();

        add(visualizer, BorderLayout.CENTER);
        add(createControlPanel(), BorderLayout.SOUTH);

        setSize(width + 20, height + 90);
        setLocationRelativeTo(null);
        setVisible(true);

        // Default algorithm
        currentAlgorithm = new BubbleSort();
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel();

        JComboBox<String> algoSelector = new JComboBox<>(new String[]{
                "Bubble Sort", "Merge Sort", "Insertion Sort", "Selection Sort", "Quick Sort",
                "Heap Sort", "Shell Sort", "Counting Sort", "Radix Sort", "Tim Sort",
                "Cocktail Shaker Sort", "Gnome Sort", "Bogo Sort"
        });

        JButton startBtn = new JButton("Start");
        JButton pauseBtn = new JButton("Pause");
        JButton resumeBtn = new JButton("Resume");
        JButton speedDownBtn = new JButton("Speed -");
        JButton speedUpBtn = new JButton("Speed +");
        JButton stepBackBtn = new JButton("Step Back");
        JButton stepForwardBtn = new JButton("Step Forward");

        stepForwardBtn.setEnabled(false);
        stepBackBtn.setEnabled(false);

        panel.add(algoSelector);
        panel.add(startBtn);
        panel.add(pauseBtn);
        panel.add(resumeBtn);
        panel.add(speedDownBtn);
        panel.add(speedUpBtn);
        panel.add(stepBackBtn);
        panel.add(stepForwardBtn);

        algoSelector.addActionListener(e -> {
            String sel = (String) algoSelector.getSelectedItem();
            switch (sel) {
                case "Bubble Sort" -> currentAlgorithm = new BubbleSort();
                case "Merge Sort" -> currentAlgorithm = new MergeSort();
                case "Insertion Sort" -> currentAlgorithm = new InsertionSort();
                case "Selection Sort" -> currentAlgorithm = new SelectionSort();
                case "Quick Sort" -> currentAlgorithm = new QuickSort();
                case "Heap Sort" -> currentAlgorithm = new HeapSort();
                case "Shell Sort" -> currentAlgorithm = new ShellSort();
                // case "Counting Sort" -> currentAlgorithm = new CountingSort();
                // case "Radix Sort" -> currentAlgorithm = new RadixSort();
                // case "Tim Sort" -> currentAlgorithm = new TimSort();
                // case "Cocktail Shaker Sort" -> currentAlgorithm = new CocktailShakerSort();
                // case "Gnome Sort" -> currentAlgorithm = new GnomeSort();
                // case "Bogo Sort" -> currentAlgorithm = new BogoSort();
            }
        });

        startBtn.addActionListener(e -> {
            if (sortingThread != null && sortingThread.isAlive()) return;
            array = generateRandomArray(array.length, visualizer.getHeight() - 20);
            visualizer.updateArray(array);
            visualizer.repaint();
            controller.setCurrentArray(array);

            stepForwardBtn.setEnabled(false);
            stepBackBtn.setEnabled(false);
            controller.resume();

            sortingThread = new Thread(() -> {
                currentAlgorithm.sort(array, visualizer, controller);
                stepForwardBtn.setEnabled(false);
                stepBackBtn.setEnabled(false);
            });
            sortingThread.start();
        });

        pauseBtn.addActionListener(e -> {
            controller.pause();
            stepForwardBtn.setEnabled(true);
            stepBackBtn.setEnabled(true);
        });

        resumeBtn.addActionListener(e -> {
            controller.resume();
            stepForwardBtn.setEnabled(false);
            stepBackBtn.setEnabled(false);
        });

        speedUpBtn.addActionListener(e -> controller.increaseSpeed());
        speedDownBtn.addActionListener(e -> controller.decreaseSpeed());

        stepForwardBtn.addActionListener(e -> controller.requestStep());
        stepBackBtn.addActionListener(e -> controller.requestStepBack());

        return panel;
    }

    private int[] generateRandomArray(int size, int maxValue) {
        Random rand = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(maxValue / 2) + 20;
        }
        return arr;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}