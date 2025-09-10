public interface SortAlgorithm {
    void sort(int[] array, SortVisualizer visualizer, SortController controller);
    String getName();
}