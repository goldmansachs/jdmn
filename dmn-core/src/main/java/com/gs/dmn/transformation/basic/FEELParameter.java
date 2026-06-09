package com.gs.dmn.transformation.basic;

import com.gs.dmn.el.analysis.semantics.type.Type;

public class FEELParameter {
    // InformationItem or DRGElementReference
    private final Object modelElement;
    private final Type type;

    public FEELParameter(Object modelElement, Type type) {
        this.modelElement = modelElement;
        this.type = type;
    }

    public Object getModelElement() {
        return modelElement;
    }

    public Type getType() {
        return type;
    }
}
