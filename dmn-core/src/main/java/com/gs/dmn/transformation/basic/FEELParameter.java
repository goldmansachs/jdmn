package com.gs.dmn.transformation.basic;

import com.gs.dmn.el.analysis.semantics.type.Type;

public class FEELParameter {
    private final String displayName;
    private final String nativeName;
    private final Type type;

    public FEELParameter(String displayName, String nativeName, Type type) {
        this.displayName = displayName;
        this.nativeName = nativeName;
        this.type = type;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getNativeName() {
        return nativeName;
    }

    public Type getType() {
        return type;
    }
}
