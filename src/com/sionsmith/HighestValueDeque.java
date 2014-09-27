package com.sionsmith;

import java.util.Date;
import java.util.Deque;
import java.util.LinkedList;

import static java.util.concurrent.TimeUnit.*;

/**
 * Simple example of using a Deque Queue to run an in memory store of the largest number (long)
 * within a given time period. This defaults to 10 minutes but can be overridden on instantiation.
 *
 * Created by sionsmith on 27/09/2014.
 */
public class HighestValueDeque {

    //Deque used as it provides assors to both sides of the Queue easily.
    private final Deque<Entry> inMemoryStore = new LinkedList<Entry>();

    //Default Memory expiry time set to 10 minutes
    private long EXPIRY_TIME = MILLISECONDS.convert(10, MINUTES);

    //default constructor
    public HighestValueDeque() {
    }

    public HighestValueDeque(long expiryTime) {
        this.EXPIRY_TIME = expiryTime;
    }

    /**
     * Adds the passed in value to the deck and pulls back the largest
     * based on the sorted linked list.
     * @param param
     * @return
     */
    public long calculateLargestValue(long param) {
        addValue(param);
        return getLargest();
    }

    /**
     * Removes all entries that are older than the expiry time.
     */
    private void pruneDeque(){
        while(new Date().getTime() - inMemoryStore.getFirst().getTimeStamp() >= EXPIRY_TIME)
            inMemoryStore.pollFirst();
    }

    /**
     * Creates Entry holder class with timestamp, used for pruning older objects.
     * Places Entry at the front if it is higher, else adds to to the back as we
     * are only interested in the highest value.
     * @param newValue
     */
    private void addValue(long newValue){
        Entry e = new Entry(newValue, new Date().getTime());
        while(!inMemoryStore.isEmpty() && newValue > inMemoryStore.getLast().getValue())
            inMemoryStore.pollLast();

        inMemoryStore.addLast(e);
    }

    /**
     * Cleans the expired values off the deck and pulls the first (highest value)
     * NOTE: For maximum efficiency only prune on retrieval.
     * @return
     */
    private Long getLargest(){
        pruneDeque();
        return inMemoryStore.getFirst().getValue();
    }

    /**
     * Simple inner class to bind value with a inserted timestamp.
     * Cannot be instantiate outside of the outer class.
     */
    private class Entry {

        private final long value;

        private final long timeStamp;

        Entry(long value, long timeStamp) {
            this.value = value;
            this.timeStamp = timeStamp;
        }

        public long getValue() {
            return value;
        }

        public long getTimeStamp() {
            return timeStamp;
        }
    }
}
