package com.gs.dmn.el.analysis.syntax.ast.expression.function;

public interface Conversion<T, K> {
    T getTargetType();
    K getKind();
}
