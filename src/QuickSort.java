public class QuickSort implements SortAlgorithm {
    private SortVisualizer visualizer;
    private SortController controller;
    private int[] array;

    @Override
    public void sort(int[] array, SortVisualizer visualizer, SortController controller) {
        this.array = array;
        this.visualizer = visualizer;
        this.controller = controller;
        visualizer.setStatus("Running Quick Sort...");
        controller.setCurrentArray(array);
        quickSort(0, array.length - 1);
        visualizer.setStatus("Done!");
        visualizer.clearHighlights();
    }

    private void quickSort(int low, int high) {
        if (low < high) {
            int pi = partition(low, high);
            quickSort(low, pi - 1);
            quickSort(pi + 1, high);
        }
    }

    private int partition(int low, int high) {
        int pivot = array[high];
        visualizer.incrementAccesses(1);

        int i = low - 1;
        for (int j = low; j < high; j++) {
            visualizer.highlightCompare(j, high);
            visualizer.incrementComparisons(1);
            visualizer.incrementAccesses(1);
            controller.waitForNext();

            if (array[j] < pivot) {
                i++;
                swap(i, j);
            }
        }
        swap(i + 1, high);
        return i + 1;
    }

    private void swap(int i, int j) {
        visualizer.highlightMove(i, j);
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
        visualizer.incrementAccesses(4);
        visualizer.incrementMoves(1);
        controller.saveState();
        controller.waitForNext();
    }

    @Override
    public String getName() {
        return "Quick Sort";
    }
}
