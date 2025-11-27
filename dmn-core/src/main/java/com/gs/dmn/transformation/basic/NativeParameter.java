package com.gs.dmn.transformation.basic;

public class NativeParameter {
    private final String name;
    private final String type;

    public NativeParameter(String type, String name) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
