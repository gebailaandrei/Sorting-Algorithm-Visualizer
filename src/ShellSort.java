public class ShellSort implements SortAlgorithm {
    @Override
    public void sort(int[] array, SortVisualizer visualizer, SortController controller) {
        visualizer.setStatus("Running Shell Sort...");
        controller.setCurrentArray(array);

        int n = array.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int temp = array[i];
                visualizer.incrementAccesses(1);
                int j = i;

                while (j >= gap && array[j - gap] > temp) {
                    visualizer.highlightCompare(j - gap, j);
                    visualizer.incrementComparisons(1);
                    visualizer.incrementAccesses(2);
                    controller.waitForNext();

                    array[j] = array[j - gap];
                    visualizer.incrementAccesses(2);
                    visualizer.incrementMoves(1);
                    visualizer.highlightMove(j, j - gap);
                    controller.saveState();
                    controller.waitForNext();
                    j -= gap;
                }

                array[j] = temp;
                visualizer.incrementAccesses(1);
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
        return "Shell Sort";
    }
}
