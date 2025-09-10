public class MergeSort implements SortAlgorithm {
    private int[] array;
    private SortVisualizer visualizer;
    private SortController controller;

    @Override
    public void sort(int[] array, SortVisualizer visualizer, SortController controller) {
        visualizer.setStatus("Running Merge Sort...");
        controller.setCurrentArray(array);
        this.array = array;
        this.visualizer = visualizer;
        this.controller = controller;
        mergeSort(0, array.length - 1);
        visualizer.setStatus("Done!");
        visualizer.clearHighlights();
    }

    private void mergeSort(int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(left, mid);
            mergeSort(mid + 1, right);
            merge(left, mid, right);
        }
    }

    private void merge(int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;

        while (i <= mid && j <= right) {
            visualizer.highlightCompare(i, j);
            visualizer.incrementComparisons(1);
            visualizer.incrementAccesses(2); // access array[i] and array[j]
            controller.waitForNext();

            if (array[i] <= array[j]) {
                temp[k++] = array[i];
                visualizer.incrementAccesses(1);
                visualizer.incrementMoves(1);
                i++;
            } else {
                temp[k++] = array[j];
                visualizer.incrementAccesses(1);
                visualizer.incrementMoves(1);
                j++;
            }
        }

        while (i <= mid) {
            temp[k++] = array[i];
            visualizer.incrementAccesses(1);
            visualizer.incrementMoves(1);
            i++;
        }
        while (j <= right) {
            temp[k++] = array[j];
            visualizer.incrementAccesses(1);
            visualizer.incrementMoves(1);
            j++;
        }

        for (int t = 0; t < temp.length; t++) {
            array[left + t] = temp[t];
            visualizer.incrementAccesses(1);
            visualizer.incrementMoves(1);
            controller.saveState();
            visualizer.highlightMove(left + t, left + t);
            visualizer.repaint();
            controller.waitForNext();
        }
    }

    @Override
    public String getName() {
        return "Merge Sort";
    }
}
