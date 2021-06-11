package com.gs.dmn.generated.tck.cl3_0004_lending_singleton;

import java.util.*;
import java.util.stream.Collectors;

public class Test0004LendingSingleton extends AbstractTest0004Lending {
    protected Routing makeRouting() {
        return Routing.instance();
    }

    protected Strategy makeStrategy() {
        return Strategy.instance();
    }

    public static void main(String[] args) {
        new Test0004LendingSingleton().execute();
    }
}
