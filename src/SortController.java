import java.util.ArrayList;
import java.util.List;

public class SortController {
    private volatile boolean paused = false;
    private volatile boolean stepRequested = false;
    private volatile boolean stepBackRequested = false;
    private int delay = 50;
    private final List<int[]> history = new ArrayList<>();
    private int[] currentArray;

    public void setCurrentArray(int[] array) {
        this.currentArray = array;
        history.clear();
        saveState();
    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        paused = false;
    }

    public boolean isPaused() {
        return paused;
    }

    public void increaseSpeed() {
        delay = Math.max(1, delay - 10);
    }

    public void decreaseSpeed() {
        delay += 10;
    }

    public void requestStep() {
        stepRequested = true;
    }

    public void requestStepBack() {
        if (currentArray != null && history.size() > 1) {
            stepBackRequested = true;
        }
    }

    public void waitForNext() {
        try {
            while (paused && !stepRequested && !stepBackRequested) {
                Thread.sleep(10);
            }
            if (stepRequested) stepRequested = false;
            else if (stepBackRequested) {
                stepBackRequested = false;
                restorePreviousState();
            }
            Thread.sleep(delay);
        } catch (InterruptedException ignored) {}
    }

    public void saveState() {
        if (currentArray != null) {
            history.add(currentArray.clone());
        }
    }

    public void restorePreviousState() {
        if (currentArray == null || history.size() <= 1) {
            return;
        }
        history.remove(history.size() - 1);
        int[] last = history.get(history.size() - 1);
        System.arraycopy(last, 0, currentArray, 0, currentArray.length);
    }

    public int getDelay() {
        return delay;
    }
}
