package com.epam.exercises.circularbuffer;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class CircularBuffer<T> {
    private T[] elementArray;
    private int head;
    private int tail;
    private int size;

    @SuppressWarnings("unchecked")
    public CircularBuffer(int capacity) {
        if (capacity <= 0) {
            throw new RuntimeException("Illegal capacity");
        }
        this.elementArray = (T[]) new Object[capacity];
        this.head = 0;
        this.tail = 0;
        this.size = 0;
    }

    public void put(T t) {
        if (size == elementArray.length) {
            throw new RuntimeException("Buffer is full");
        } else {
            elementArray[head] = t;
            head = movePointer(head);
            ++size;
        }
    }

    public T get() {
        if (size == 0) {
            throw new RuntimeException("Buffer is empty");
        } else {
            T t = elementArray[tail];
            tail = movePointer(tail);
            --size;
            return t;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public Object[] toObjectArray() {
        Object[] array = new Object[size];
        for (int i = 0, index = tail; i < size; i++, index = movePointer(index)) {
            array[i] = elementArray[index];
        }
        return array;
    }

    @SuppressWarnings("unchecked")
    public T[] toArray(Class<T[]> clazz) {
        return Arrays.copyOf(toObjectArray(), size, clazz);
    }

    public List<T> asList(Class<T[]> clazz) {
        return Arrays.asList(toArray(clazz));
    }

    public void addAll(List<? extends T> toAdd) {
        if (toAdd.size() > elementArray.length - size) {
            throw new RuntimeException("Not enough space");
        }
        for (T t : toAdd) {
            put(t);
        }
    }

    public void sort(Comparator<? super T> comparator) {
        for (int i = tail; i != movePointer(head, false); i = movePointer(i)) {
            for (int j = tail; j != movePointer(head, false); j = movePointer(j)) {
                if (comparator.compare(elementArray[j], elementArray[movePointer(j)]) > 0) {
                    T temp = elementArray[movePointer(j)];
                    elementArray[movePointer(j)] = elementArray[j];
                    elementArray[j] = temp;
                }
            }
        }
    }

    private int movePointer(int pointer) {
        return movePointer(pointer, true);
    }

    private int movePointer(int pointer, boolean direction) {
        if (direction) {
            pointer = pointer + 1 == elementArray.length ? 0 : ++pointer;
        } else {
            pointer = pointer - 1 < 0 ? elementArray.length - 1 : --pointer;
        }
        return pointer;
    }
}
