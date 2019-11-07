/*
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
package com.gs.dmn.signavio.rdf2dmn;

import com.gs.dmn.runtime.DMNRuntimeException;
import org.jdom2.Element;

import java.util.Comparator;

public class DMNNodeComparator implements Comparator<Element> {
    @Override
    public int compare(Element e1, Element e2) {
        String tag1 = e1.getName();
        String tag2 = e2.getName();
        String id1 = getAttribute(e1, tag1);
        String id2 = getAttribute(e2, tag2);
        if (tag1.equals(tag2)) {
            return id1.compareTo(id2);
        } else {
            return tag1.compareTo(tag2);
        }
    }

    private String getAttribute(Element e, String tag) {
        String id = e.getAttributeValue("id");
        if (id == null) {
            id = e.getAttributeValue("name");
        }
        if (tag.equals("informationRequirement")) {
            id = e.getChildren().get(0).getAttributeValue("href");
        }
        if (id == null) {
            throw new DMNRuntimeException("Cannot sort element " + tag);
        }
        return id;
    }
}
