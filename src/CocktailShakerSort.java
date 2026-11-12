public class CocktailShakerSort implements SortAlgorithm {
    @Override
    public void sort(int[] array, SortVisualizer visualizer, SortController controller) {
        visualizer.setStatus("Running Cocktail Shaker Sort...");
        controller.setCurrentArray(array);

        boolean swapped = true;
        int start = 0, end = array.length - 1;

        while (swapped) {
            swapped = false;

            for (int i = start; i < end; ++i) {
                visualizer.highlightCompare(i, i + 1);
                visualizer.incrementComparisons(1);
                visualizer.incrementAccesses(2); // accessing array[i] and array[i+1]
                controller.waitForNext();

                if (array[i] > array[i + 1]) {
                    swap(array, i, i + 1, visualizer, controller);
                    swapped = true;
                }
            }

            if (!swapped) break;

            swapped = false;
            end--;

            for (int i = end - 1; i >= start; --i) {
                visualizer.highlightCompare(i, i + 1);
                visualizer.incrementComparisons(1);
                visualizer.incrementAccesses(2); // accessing array[i] and array[i+1]
                controller.waitForNext();

                if (array[i] > array[i + 1]) {
                    swap(array, i, i + 1, visualizer, controller);
                    swapped = true;
                }
            }
            start++;
        }

        visualizer.setStatus("Done!");
        visualizer.clearHighlights();
    }

    private void swap(int[] arr, int i, int j, SortVisualizer visualizer, SortController controller) {
        int tmp = arr[i];
        visualizer.incrementAccesses(1);

        arr[i] = arr[j];
        visualizer.incrementAccesses(1);
        visualizer.incrementMoves(1);

        arr[j] = tmp;
        visualizer.incrementAccesses(1);
        visualizer.incrementMoves(1);

        controller.saveState();
        visualizer.highlightMove(i, j);
        visualizer.repaint();

        controller.waitForNext();
    }

    @Override
    public String getName() {
        return "Cocktail Shaker Sort";
    }
}
