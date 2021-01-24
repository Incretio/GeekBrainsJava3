package lessonfour;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        runThreads();
    }

    static class PrintLetter implements Runnable {
        private final String letter;
        private final Object ownMonitor;
        private final Object nextMonitor;

        public PrintLetter(String letter, Object ownMonitor, Object nextMonitor) {
            this.letter = letter;
            this.ownMonitor = ownMonitor;
            this.nextMonitor = nextMonitor;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 5; i++) {
                    synchronized (ownMonitor) {
                        synchronized (nextMonitor) {
                            System.out.println(letter);
                            nextMonitor.notify();
                        }
                        if (i < 4) {
                            ownMonitor.wait();
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void runThreads() {
        Object monitorA = new Object();
        Object monitorB = new Object();
        Object monitorC = new Object();

        Thread thread1 = new Thread(new PrintLetter("A", monitorA, monitorB));
        Thread thread2 = new Thread(new PrintLetter("B", monitorB, monitorC));
        Thread thread3 = new Thread(new PrintLetter("C", monitorC, monitorA));

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
