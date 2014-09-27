package com.sionsmith;

import java.util.Random;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Quick test class that is used to run a the calculator method for a
 * number of different durations to test out expiry time logic.
 *
 * Created by sionsmith on 27/09/2014.
 */
public class HighestValueDequeTest {

    public static void main(String[] args) {

        //very basic set of threads to test in parallel. ExecutorService & thread pools is over kill here.
        TestTask test1 = new TestTask(1L, 50000L, MILLISECONDS.convert(10, SECONDS));
        TestTask test2 = new TestTask(2342L, 2342342234L, MILLISECONDS.convert(1, MINUTES));

        //run the tests.
        test1.run();
        test2.run();
    }

    static class TestTask implements Runnable {

        private final long RUN_TIME;
        private final long MIN_RANGE;
        private final long MAX_RANGE;

        TestTask(long minRange, long maxRange, long runTime) {
            this.MIN_RANGE = minRange;
            this.MAX_RANGE = maxRange;
            this.RUN_TIME = runTime;
        }

        @Override
        public void run() {
            HighestValueDeque deque = new HighestValueDeque(RUN_TIME);

            long start = System.currentTimeMillis();
            long end = start + RUN_TIME;
            while (System.currentTimeMillis() < end){
                Random r = new Random();
                long number = ((long)(r.nextDouble() * (MAX_RANGE - MIN_RANGE)));
                deque.calculateLargestValue(number);
            }
            //print out the highest value.
            System.out.println("Highest Value in with expiry time: " + RUN_TIME / 1000 + "secs was: " + deque.calculateLargestValue(1L));
        }
    }
}
