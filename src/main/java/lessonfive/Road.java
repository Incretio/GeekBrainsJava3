package lessonfive;

import java.util.concurrent.locks.ReentrantLock;

public class Road extends Stage {

    private final ReentrantLock finishStageLock = new ReentrantLock();
    private final boolean lastStage;
    private boolean hasFinisher = false;

    public Road(int length) {
        this(length, false);
    }

    public Road(int length, boolean lastStage) {
        this.lastStage = lastStage;
        this.length = length;
        this.description = "Дорога " + length + " метров";
    }
    @Override
    public void go(Car c) {
        try {
            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000);
            finishStageLock.lock();
            try {
                System.out.println(c.getName() + " закончил этап: " + description);
            } finally {
                if (lastStage && !hasFinisher) {
                    hasFinisher = true;
                    System.out.println(c.getName() + " - WIN");
                }
                finishStageLock.unlock();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
