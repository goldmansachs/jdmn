package com.gs.dmn.generated.tck.cl3_0004_lending_singleton;

public class Test0004LendingNoSingleton extends AbstractTest0004Lending {
    @Override
    protected Routing makeRouting() {
        return new Routing();
    }

    @Override
    protected Strategy makeStrategy() {
        return new Strategy();
    }

    public static void main(String[] args) {
        new Test0004LendingSingleton().execute();
    }
}
