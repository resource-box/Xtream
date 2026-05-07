package com.hooniegit.Xtream.Tools;

/**
 * StreamManager Class for Managing Stream Array and Distributing Streams
 * Uses ThreadLocal to provide Round-Robin distribution per thread without contention.
 * If a stream is busy (RingBuffer is full), it will try to find an available stream.
 * Optimized for single stream usage to bypass ThreadLocal overhead.
 * @param <T>
 */
public class StreamManager<T> {

    private final Stream<T>[] streamList;
    private final boolean isSingleStream;
    
    // Each thread maintains its own counter to avoid contention (only used if size > 1)
    private final ThreadLocal<Integer> threadLocalIndex;

    public StreamManager(Stream<T>[] streamList) {
        this.streamList = streamList;
        this.isSingleStream = (streamList != null && streamList.length == 1);
        this.threadLocalIndex = this.isSingleStream ? null : ThreadLocal.withInitial(() -> 0);
    }

    /**
     * Provide Stream. Attempts to find a stream with available capacity.
     * If all are busy, it falls back to the original Round-Robin stream to apply backpressure.
     * Returns null if the stream list is not initialized or empty.
     */
    public Stream<T> getNextStream() {
        if (streamList == null || streamList.length == 0) {
            return null;
        }

        // Fast-path for single stream
        if (isSingleStream) {
            return streamList[0];
        }
        
        // Get the current index for this specific thread
        int startIndex = threadLocalIndex.get();
        int currentIndex = startIndex;
        
        // Loop through the streams to find one with available capacity
        do {
            Stream<T> stream = streamList[currentIndex];
            if (stream.hasAvailableCapacity(1)) {
                // Found an available stream. Update index for next call and return.
                threadLocalIndex.set((currentIndex + 1) % streamList.length);
                return stream;
            }
            currentIndex = (currentIndex + 1) % streamList.length;
        } while (currentIndex != startIndex);
        
        // If we reach here, ALL streams are full (Backpressure scenario).
        // Fall back to the original target stream. The publishing thread will block 
        // until capacity becomes available, which is the standard Disruptor behavior.
        Stream<T> fallbackStream = streamList[startIndex];
        threadLocalIndex.set((startIndex + 1) % streamList.length);
        
        return fallbackStream;
    }
}
