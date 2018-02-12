/**
 * Copyright 2016 Goldman Sachs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.gs.dmn.runtime.listener;

import java.util.ArrayList;
import java.util.List;

public class CompositeListener implements EventListener {
    private final List<EventListener> eventListeners = new ArrayList<>();

    public CompositeListener(EventListener... eventListeners) {
        if (eventListeners != null) {
            for (EventListener eventListener : eventListeners) {
                this.eventListeners.add(eventListener);
            }
        }
    }


    @Override
    public void startDRGElement(DRGElement element, Arguments arguments) {
        eventListeners.forEach(el -> el.startDRGElement(element, arguments));
    }

    @Override
    public void endDRGElement(DRGElement element, Arguments arguments, Object output, long duration) {
        eventListeners.forEach(el -> el.endDRGElement(element, arguments, output, duration));
    }

    @Override
    public void startRule(DRGElement element, Rule rule) {
        eventListeners.forEach(el -> el.startRule(element, rule));
    }

    @Override
    public void matchRule(DRGElement element, Rule rule) {
        eventListeners.forEach(el -> el.matchRule(element, rule));
    }

    @Override
    public void endRule(DRGElement element, Rule rule, Object output) {
        eventListeners.forEach(el -> el.endRule(element, rule, output));
    }
}
