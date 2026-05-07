package com.hooniegit.Xtream.Tools;

import java.util.List;
import java.lang.reflect.Array;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Configuration for Xtream
 * @param <T>
 */
@Configuration
public class StreamAutoConfiguration<T> {

    /**
     * StreamBuilder Bean
     * @param handlers
     * @param bufferMultiplier
     * @return
     */
    @Bean
    public StreamBuilder<T> streamBuilder(List<Handler<T>> handlers, @Value("${LMAX.buffer.multiplier}") int bufferMultiplier) {
        return new StreamBuilder<>(handlers, bufferMultiplier);
    }

    /**
     * Stream Array Bean
     * @param streamBuilder
     * @param streamSize
     * @return
     */
    @Bean
    public Stream<T>[] streamArray(StreamBuilder<T> streamBuilder, @Value("${LMAX.list.size}") int streamSize) {
        // Use reflection to create an array of a generic type
        @SuppressWarnings("unchecked")
        Stream<T>[] streamArray = (Stream<T>[]) Array.newInstance(Stream.class, streamSize);
        for (int i = 0; i < streamSize; i++) {
            streamArray[i] = streamBuilder.build();
        }
        return streamArray;
    }

    /**
     * StreamManager Bean
     * @param streamArray
     * @return
     */
    @Bean
    public StreamManager<T> streamManager(Stream<T>[] streamArray) {
        return new StreamManager<>(streamArray);
    }
    
}
