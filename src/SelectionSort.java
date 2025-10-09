public class SelectionSort implements SortAlgorithm {
    @Override
    public void sort(int[] array, SortVisualizer visualizer, SortController controller) {
        visualizer.setStatus("Running Selection Sort...");
        controller.setCurrentArray(array);

        for (int i = 0; i < array.length - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < array.length; j++) {
                visualizer.highlightCompare(minIdx, j);
                visualizer.incrementAccesses(2);
                visualizer.incrementComparisons(1);
                controller.waitForNext();

                if (array[j] < array[minIdx]) {
                    minIdx = j;
                }
            }

            if (minIdx != i) {
                visualizer.highlightMove(i, minIdx);
                int temp = array[i];
                array[i] = array[minIdx];
                array[minIdx] = temp;
                visualizer.incrementAccesses(4);
                visualizer.incrementMoves(1);
                controller.saveState();
                controller.waitForNext();
            }
        }

        visualizer.setStatus("Done!");
        visualizer.clearHighlights();
    }

    @Override
    public String getName() {
        return "Selection Sort";
    }
}
