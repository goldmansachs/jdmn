package com.gs.dmn.transformation.basic;

import com.gs.dmn.DRGElementReference;
import com.gs.dmn.ast.TInformationItem;
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

    public String getName() {
        if (modelElement instanceof DRGElementReference) {
            return ((DRGElementReference<?>) modelElement).getElementName();
        } else if (modelElement instanceof TInformationItem) {
            return ((TInformationItem) modelElement).getName();
        } else {
            throw new IllegalArgumentException(String.format("Illegal model element '%s'", modelElement.getClass().getSimpleName()));
        }
    }

    public Type getType() {
        return type;
    }
}
