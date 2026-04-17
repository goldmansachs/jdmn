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
package com.gs.dmn.runtime.compiler.listener;

/**
 * Listener for various execution lifecycle events.
 *
 * <p>Events are expected to be invoked in the following order:
 * <ol>
 *   <li>{@link #startDMNTranslation()}</li>
 *   <li>{@link #endDMNTranslation()}</li>
 *   <li>{@link #startTestCasesTranslation()}</li>
 *   <li>{@link #endTestCasesTranslation()}</li>
 *   <li>{@link #startCompilation()}</li>
 *   <li>{@link #endCompilation()}</li>
 *   <li>{@link #startTestExecution()}</li>
 *   <li>{@link #endTestExecution()}</li>
 * </ol>
 */
public interface ExecutionListener {
    void startDMNTranslation();
    void endDMNTranslation();

    void startTestCasesTranslation();
    void endTestCasesTranslation();

    void startCompilation();
    void endCompilation();

    void startTestExecution();
    void endTestExecution();
}
