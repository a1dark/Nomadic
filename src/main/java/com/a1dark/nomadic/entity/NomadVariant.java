package com.a1dark.nomadic.entity;

import java.util.Arrays;
import java.util.Comparator;

public enum NomadVariant {
    WARRIOR(0),
    ARCHER(1);
    private static final NomadVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(NomadVariant::getId)).toArray(NomadVariant[]::new);
    private final int id;

    NomadVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static NomadVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}