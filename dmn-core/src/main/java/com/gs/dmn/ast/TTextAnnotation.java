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
package com.gs.dmn.ast;

import com.gs.dmn.runtime.DMNContext;

public class TTextAnnotation extends TArtifact implements Visitable {
    private String text;
    private String textFormat;

    public String getText() {
        return text;
    }

    public void setText(String value) {
        this.text = value;
    }

    public String getTextFormat() {
        if (textFormat == null) {
            return "text/plain";
        } else {
            return textFormat;
        }
    }

    public void setTextFormat(String value) {
        this.textFormat = value;
    }

    @Override
    public Object accept(Visitor visitor, DMNContext context) {
        return visitor.visit(this, context);
    }
}
