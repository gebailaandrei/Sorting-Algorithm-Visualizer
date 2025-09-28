public class InsertionSort implements SortAlgorithm {
    @Override
    public void sort(int[] array, SortVisualizer visualizer, SortController controller) {
        visualizer.setStatus("Running Insertion Sort...");
        controller.setCurrentArray(array);

        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            visualizer.incrementAccesses(1);
            int j = i - 1;

            while (j >= 0) {
                visualizer.highlightCompare(j, j + 1);
                visualizer.incrementComparisons(1);
                visualizer.incrementAccesses(2); // access array[j] and key
                controller.waitForNext();

                if (array[j] > key) {
                    array[j + 1] = array[j];
                    visualizer.incrementAccesses(1);
                    visualizer.incrementMoves(1);

                    controller.saveState();
                    visualizer.highlightMove(j, j + 1);
                    visualizer.repaint();
                    controller.waitForNext();
                    j--;
                } else {
                    break;
                }
            }
            array[j + 1] = key;
            visualizer.incrementMoves(1);
            visualizer.incrementAccesses(1);
            controller.saveState();
            visualizer.highlightMove(j + 1, j + 1);
            controller.waitForNext();
        }
        visualizer.setStatus("Done!");
        visualizer.clearHighlights();
    }

    @Override
    public String getName() {
        return "Insertion Sort";
    }
}
