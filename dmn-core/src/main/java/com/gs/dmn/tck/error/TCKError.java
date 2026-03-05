package com.gs.dmn.tck.error;

import com.gs.dmn.error.SeverityLevel;

public class TCKError {
    private final SeverityLevel severityLevel;
    private final TestLocation locationInfo;
    private final String errorMessage;

    public TCKError(SeverityLevel severityLevel, TestLocation locationInfo, String errorMessage) {
        this.severityLevel = severityLevel;
        this.locationInfo = locationInfo;
        this.errorMessage = errorMessage;
    }

    public String toText() {
        if (locationInfo == null || locationInfo.toText().isEmpty()) {
            return String.format("[%s] %s", severityLevel.name(), errorMessage);
        } else {
            return String.format("[%s] %s: %s", severityLevel.name(), locationInfo.toText(), errorMessage);
        }
    }
}
