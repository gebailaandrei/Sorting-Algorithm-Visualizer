public class GnomeSort implements SortAlgorithm {
    @Override
    public void sort(int[] array, SortVisualizer visualizer, SortController controller) {
        visualizer.setStatus("Running Gnome Sort...");
        int index = 0;
        int n = array.length;

        while (index < n) {
            if (index == 0) {
                index++;
                continue;
            }

            visualizer.highlightCompare(index, index - 1);
            visualizer.incrementAccesses(2); // reading array[index] and array[index - 1]
            visualizer.incrementComparisons(1);
            controller.waitForNext();

            if (array[index] >= array[index - 1]) {
                index++;
            } else {
                swap(array, index, index - 1, visualizer, controller);
                controller.saveState();
                index--;
            }
        }

        visualizer.clearHighlights();
        visualizer.setStatus("Done!");
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
        return "Gnome Sort";
    }
}
