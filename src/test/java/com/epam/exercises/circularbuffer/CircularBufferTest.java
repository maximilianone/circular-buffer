package com.epam.exercises.circularbuffer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

class CircularBufferTest {
    private static final Integer ELEMENT = 1;
    private static final Integer OTHER_ELEMENT = 2;

    private Integer[] fullBufferArray = new Integer[]{1, 2, 3, 100, -1, 51, 15, 12, 0, 8};
    private Integer[] sortedArray = new Integer[]{-1, 0, 1, 2, 3, 8, 12, 15, 51, 100};
    private List<Integer> fullBufferList = Arrays.asList(fullBufferArray);
    private CircularBuffer<Integer> circularBuffer;

    @BeforeEach
    void setUp() {
        circularBuffer = new CircularBuffer<>(10);
    }

    @Test
    void shouldThrowRuntimeExceptionOnFullBuffer() {
        circularBuffer.addAll(fullBufferList);

        Assertions.assertThrows(RuntimeException.class, () -> circularBuffer.put(2));
    }

    @Test
    void shouldAddElementToCircularBuffer() {
        circularBuffer.put(ELEMENT);

        Assertions.assertEquals(ELEMENT, circularBuffer.get());
    }

    @Test
    void shouldThrowRuntimeExceptionOnEmptyBuffer() {
        Assertions.assertThrows(RuntimeException.class, () -> circularBuffer.get());
    }

    @Test
    void shouldGetFirstAddedValue() {
        circularBuffer.put(ELEMENT);
        circularBuffer.put(OTHER_ELEMENT);

        Assertions.assertEquals(ELEMENT, circularBuffer.get());
    }

    @Test
    void shouldReturnTrueWhenBufferIsEmpty() {
        Assertions.assertTrue(circularBuffer.isEmpty());
    }

    @Test
    void shouldReturnFalseWhenBufferIsNotEmpty() {
        circularBuffer.put(ELEMENT);

        Assertions.assertFalse(circularBuffer.isEmpty());
    }

    @Test
    void shouldReturnObjectArray() {
        circularBuffer.addAll(fullBufferList);

        Object[] objectArray = circularBuffer.toObjectArray();

        Assertions.assertArrayEquals(fullBufferArray, objectArray);
    }

    @Test
    void shouldReturnArrayOfType() {
        circularBuffer.addAll(fullBufferList);

        Integer[] array = circularBuffer.toArray(Integer[].class);

        Assertions.assertArrayEquals(fullBufferArray, array);
    }

    @Test
    void shouldReturnListOfType() {
        circularBuffer.addAll(fullBufferList);

        List<Integer> list = circularBuffer.asList(Integer[].class);

        Assertions.assertEquals(list, fullBufferList);
    }

    @Test
    void shouldAddAllElementsToBuffer() {
        circularBuffer.addAll(fullBufferList);

        Assertions.assertArrayEquals(circularBuffer.toObjectArray(), fullBufferArray);
    }

    @Test
    void shouldThrowRuntimeExceptionWhenNotEnoughSize() {
        circularBuffer.put(ELEMENT);

        Assertions.assertThrows(RuntimeException.class, () -> circularBuffer.addAll(fullBufferList));
    }

    @Test
    void shouldSortCircularBuffer() {
        circularBuffer.addAll(fullBufferList);

        circularBuffer.sort(Comparator.comparingInt(a -> a));

        Assertions.assertArrayEquals(circularBuffer.toObjectArray(), sortedArray);
    }
}