package lessonfive;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable {
    private static int CARS_COUNT;

    static {
        CARS_COUNT = 0;
    }

    private Race race;
    private int speed;
    private final CyclicBarrier cyclicBarrier;
    private final CyclicBarrier finishBarrier;
    private final CountDownLatch countDownLatch;
    private String name;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed, CyclicBarrier cyclicBarrier, CyclicBarrier finishBarrier,
               CountDownLatch countDownLatch) {
        this.race = race;
        this.speed = speed;
        this.cyclicBarrier = cyclicBarrier;
        this.finishBarrier = finishBarrier;
        this.countDownLatch = countDownLatch;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");

            cyclicBarrier.await();
            countDownLatch.await();

            for (int i = 0; i < race.getStages().size(); i++) {
                race.getStages().get(i).go(this);
            }

            finishBarrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
