public class Barrier implements IBarrier {
    private final int numThreads;
    private int arrivedThreads = 0;
    private int useCounter = 0;

    public Barrier(int numThreads) {
        this.numThreads = numThreads;
    }

    @Override
    public void await() throws InterruptedException {
        if (arrivedThreads == numThreads - 1) {
            freeAll(); // we are the last arriver
        } else {
            this.arrivedThreads++;
            // wait for the last arriver
            int counter = this.useCounter;
            while (counter == this.useCounter) {
                this.wait();
            }
        }
    }

    @Override
    public void freeAll() {
        this.useCounter++;
        this.arrivedThreads = 0;
        this.notifyAll();
    }
}
