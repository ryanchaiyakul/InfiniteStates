package com.team2568.frc2020.registers;

import java.util.ArrayList;

public class FiFoRegister<T> extends Register<T> {
    private ArrayList<T> cache = new ArrayList<T>();
    private int depth;

    public FiFoRegister(int depth) {
        this.depth = depth;
    }

    @Override
    public void set(T value) throws RuntimeException {
        if (cache.size() < depth) {
            cache.add(value);
        } else {
            throw new RuntimeException("FiFo of depth " + depth + " is full");
        }
    }

    @Override
    public T get() {
        if (cache.size() > 0) {
            return cache.remove(0);
        }
        return null;
    }
}   
