public class BubbleSort implements SortAlgorithm {
    @Override
    public void sort(int[] array, SortVisualizer visualizer, SortController controller) {
        visualizer.setStatus("Running Bubble Sort...");
        controller.setCurrentArray(array);

        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                visualizer.highlightCompare(j, j + 1);
                visualizer.incrementComparisons(1);
                visualizer.incrementAccesses(2); // accessing array[j] and array[j+1]
                controller.waitForNext();

                if (array[j] > array[j + 1]) {
                    int tmp = array[j];
                    visualizer.incrementAccesses(1);
                    visualizer.incrementMoves(1);

                    array[j] = array[j + 1];
                    visualizer.incrementAccesses(1);
                    visualizer.incrementMoves(1);

                    array[j + 1] = tmp;
                    visualizer.incrementMoves(1);
                    visualizer.incrementAccesses(1);

                    controller.saveState();
                    visualizer.highlightMove(j, j + 1);
                    visualizer.repaint();
                    controller.waitForNext();
                }
            }
        }
        visualizer.setStatus("Done!");
        visualizer.clearHighlights();
    }

    @Override
    public String getName() {
        return "Bubble Sort";
    }
}
