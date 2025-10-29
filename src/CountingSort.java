public class CountingSort implements SortAlgorithm {
    @Override
    public void sort(int[] array, SortVisualizer visualizer, SortController controller) {
        visualizer.setStatus("Running Counting Sort...");
        controller.setCurrentArray(array);

        int max = array[0];
        visualizer.incrementAccesses(1);
        for (int val : array) {
            visualizer.incrementAccesses(1);
            if (val > max) max = val;
        }

        int[] count = new int[max + 1];
        for (int val : array) {
            visualizer.incrementAccesses(1);
            count[val]++;
        }

        int index = 0;
        for (int i = 0; i < count.length; i++) {
            for (int j = 0; j < count[i]; j++) {
                array[index] = i;
                visualizer.incrementMoves(1);
                visualizer.incrementAccesses(1); // write access
                controller.saveState();
                visualizer.highlightMove(index, index);
                visualizer.repaint();
                controller.waitForNext();
                index++;
            }
        }

        visualizer.setStatus("Done!");
        visualizer.clearHighlights();
    }

    @Override
    public String getName() {
        return "Counting Sort";
    }
}
