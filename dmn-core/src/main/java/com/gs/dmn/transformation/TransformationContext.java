package com.gs.dmn.transformation;

import com.gs.dmn.DMNModelRepository;

public class TransformationContext {
    private final DMNModelRepository repository;

    public TransformationContext(DMNModelRepository repository) {
        this.repository = repository;
    }

    public DMNModelRepository getRepository() {
        return repository;
    }
}
