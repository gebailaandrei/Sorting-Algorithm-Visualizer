import java.util.Random;

public class BogoSort implements SortAlgorithm {
    @Override
    public void sort(int[] array, SortVisualizer visualizer, SortController controller) {
        Random rand = new Random();
        visualizer.setStatus("Running Bogo Sort...");
        controller.setCurrentArray(array);

        while (!isSorted(array, visualizer, controller)) {
            for (int i = 0; i < array.length; i++) {
                int j = rand.nextInt(array.length);

                // Accesses for reading array[i] and array[j] during swap
                visualizer.incrementAccesses(2);

                swap(array, i, j, visualizer, controller);
                controller.saveState();
                controller.waitForNext();
            }
        }

        visualizer.setStatus("Done!");
        visualizer.clearHighlights();
    }

    private boolean isSorted(int[] arr, SortVisualizer visualizer, SortController controller) {
        for (int i = 0; i < arr.length - 1; i++) {
            visualizer.highlightCompare(i, i + 1);
            visualizer.incrementComparisons(1);

            // Accesses for reading arr[i] and arr[i+1]
            visualizer.incrementAccesses(2);

            controller.waitForNext();

            if (arr[i] > arr[i + 1]) return false;
        }
        return true;
    }

    private void swap(int[] arr, int i, int j, SortVisualizer visualizer, SortController controller) {
        int temp = arr[i];
        visualizer.incrementAccesses(1);

        arr[i] = arr[j];
        visualizer.incrementAccesses(1);
        visualizer.incrementMoves(1);

        arr[j] = temp;
        visualizer.incrementAccesses(1);
        visualizer.incrementMoves(1);

        visualizer.highlightMove(i, j);
        visualizer.repaint();
        controller.waitForNext();
    }

    @Override
    public String getName() {
        return "Bogo Sort";
    }
}
