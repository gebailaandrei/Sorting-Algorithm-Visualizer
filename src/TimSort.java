public class TimSort implements SortAlgorithm {
    private static final int RUN = 32;

    @Override
    public void sort(int[] array, SortVisualizer visualizer, SortController controller) {
        visualizer.setStatus("Running Tim Sort...");
        controller.setCurrentArray(array);

        int n = array.length;

        for (int i = 0; i < n; i += RUN) {
            insertionSort(array, i, Math.min((i + RUN - 1), (n - 1)), visualizer, controller);
        }

        for (int size = RUN; size < n; size = 2 * size) {
            for (int left = 0; left < n; left += 2 * size) {
                int mid = left + size - 1;
                int right = Math.min((left + 2 * size - 1), (n - 1));

                if (mid < right) {
                    merge(array, left, mid, right, visualizer, controller);
                }
            }
        }

        visualizer.setStatus("Done!");
        visualizer.clearHighlights();
    }

    private void insertionSort(int[] array, int left, int right, SortVisualizer visualizer, SortController controller) {
        for (int i = left + 1; i <= right; i++) {
            int temp = array[i];
            visualizer.incrementAccesses(1);
            int j = i - 1;

            while (j >= left && array[j] > temp) {
                visualizer.highlightCompare(j, i);
                visualizer.incrementComparisons(1);
                visualizer.incrementAccesses(1);
                controller.waitForNext();

                array[j + 1] = array[j];
                visualizer.incrementAccesses(2);
                visualizer.incrementMoves(1);
                visualizer.highlightMove(j + 1, j);
                controller.saveState();
                controller.waitForNext();
                j--;
            }

            array[j + 1] = temp;
            visualizer.incrementAccesses(1);
            visualizer.incrementMoves(1);
            visualizer.highlightMove(j + 1, i);
            controller.saveState();
            controller.waitForNext();
        }
    }

    private void merge(int[] array, int l, int m, int r, SortVisualizer visualizer, SortController controller) {
        int len1 = m - l + 1, len2 = r - m;
        int[] left = new int[len1];
        int[] right = new int[len2];

        for (int i = 0; i < len1; i++) {
            left[i] = array[l + i];
            visualizer.incrementAccesses(2);
        }
        for (int i = 0; i < len2; i++) {
            right[i] = array[m + 1 + i];
            visualizer.incrementAccesses(2);
        }

        int i = 0, j = 0, k = l;

        while (i < len1 && j < len2) {
            visualizer.highlightCompare(l + i, m + 1 + j);
            visualizer.incrementComparisons(1);
            visualizer.incrementAccesses(2);
            controller.waitForNext();

            if (left[i] <= right[j]) {
                array[k] = left[i];
                visualizer.incrementAccesses(2);
                visualizer.incrementMoves(1);
                visualizer.highlightMove(k, l + i);
                i++;
            } else {
                array[k] = right[j];
                visualizer.incrementAccesses(2);
                visualizer.incrementMoves(1);
                visualizer.highlightMove(k, m + 1 + j);
                j++;
            }

            controller.saveState();
            controller.waitForNext();
            k++;
        }

        while (i < len1) {
            array[k] = left[i];
            visualizer.incrementAccesses(2);
            visualizer.incrementMoves(1);
            visualizer.highlightMove(k, l + i);
            controller.saveState();
            controller.waitForNext();
            i++;
            k++;
        }

        while (j < len2) {
            array[k] = right[j];
            visualizer.incrementAccesses(2);
            visualizer.incrementMoves(1);
            visualizer.highlightMove(k, m + 1 + j);
            controller.saveState();
            controller.waitForNext();
            j++;
            k++;
        }
    }

    @Override
    public String getName() {
        return "Tim Sort";
    }
}
