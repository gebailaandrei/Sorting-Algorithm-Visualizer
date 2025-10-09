public class HeapSort implements SortAlgorithm {
    @Override
    public void sort(int[] array, SortVisualizer visualizer, SortController controller) {
        visualizer.setStatus("Running Heap Sort...");
        controller.setCurrentArray(array);

        int n = array.length;

        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(array, n, i, visualizer, controller);

        for (int i = n - 1; i >= 0; i--) {
            // swap array[0] and array[i]
            int temp = array[0];
            visualizer.incrementAccesses(1);
            visualizer.incrementMoves(1);

            array[0] = array[i];
            visualizer.incrementAccesses(1);
            visualizer.incrementMoves(1);

            array[i] = temp;
            visualizer.incrementAccesses(1);
            visualizer.incrementMoves(1);

            controller.saveState();
            visualizer.highlightMove(0, i);
            visualizer.repaint();
            controller.waitForNext();
            heapify(array, i, 0, visualizer, controller);
        }

        visualizer.setStatus("Done!");
        visualizer.clearHighlights();
    }

    private void heapify(int[] array, int n, int i, SortVisualizer visualizer, SortController controller) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n) {
            visualizer.highlightCompare(largest, left);
            visualizer.incrementComparisons(1);
            visualizer.incrementAccesses(2); // access array[left] and array[largest]
            controller.waitForNext();
            if (array[left] > array[largest]) largest = left;
        }

        if (right < n) {
            visualizer.highlightCompare(largest, right);
            visualizer.incrementComparisons(1);
            visualizer.incrementAccesses(2); // access array[right] and array[largest]
            controller.waitForNext();
            if (array[right] > array[largest]) largest = right;
        }

        if (largest != i) {
            int swap = array[i];
            visualizer.incrementAccesses(1);
            visualizer.incrementMoves(1);

            array[i] = array[largest];
            visualizer.incrementAccesses(1);
            visualizer.incrementMoves(1);

            array[largest] = swap;
            visualizer.incrementAccesses(1);
            visualizer.incrementMoves(1);

            controller.saveState();
            visualizer.highlightMove(i, largest);
            visualizer.repaint();
            controller.waitForNext();
            heapify(array, n, largest, visualizer, controller);
        }
    }

    @Override
    public String getName() {
        return "Heap Sort";
    }
}
