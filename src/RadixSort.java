public class RadixSort implements SortAlgorithm {
    @Override
    public void sort(int[] array, SortVisualizer visualizer, SortController controller) {
        visualizer.setStatus("Running Radix Sort...");
        controller.setCurrentArray(array);

        int max = array[0];
        visualizer.incrementAccesses(1);
        for (int i = 1; i < array.length; i++) {
            visualizer.incrementAccesses(1);
            visualizer.incrementComparisons(1);
            if (array[i] > max) {
                max = array[i];
            }
        }

        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSortByDigit(array, exp, visualizer, controller);
        }

        visualizer.setStatus("Done!");
        visualizer.clearHighlights();
    }

    private void countingSortByDigit(int[] array, int exp, SortVisualizer visualizer, SortController controller) {
        int[] output = new int[array.length];
        int[] count = new int[10];

        for (int val : array) {
            count[(val / exp) % 10]++;
            visualizer.incrementAccesses(1);
        }

        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
            visualizer.incrementAccesses(2);
        }

        for (int i = array.length - 1; i >= 0; i--) {
            int digit = (array[i] / exp) % 10;
            output[--count[digit]] = array[i];
            visualizer.incrementAccesses(2);
            visualizer.incrementMoves(1);
        }

        for (int i = 0; i < array.length; i++) {
            array[i] = output[i];
            visualizer.incrementAccesses(2);
            visualizer.incrementMoves(1);
            visualizer.highlightMove(i, i);
            controller.saveState();
            controller.waitForNext();
        }
    }

    @Override
    public String getName() {
        return "Radix Sort";
    }
}
