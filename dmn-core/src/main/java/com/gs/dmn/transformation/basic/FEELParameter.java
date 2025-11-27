package com.gs.dmn.transformation.basic;

import com.gs.dmn.el.analysis.semantics.type.Type;

public class FEELParameter {
    private final String name;
    private final Type type;

    public FEELParameter(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }
}
