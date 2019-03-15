package com.lx.pk.core.validator;

import java.io.Serializable;
import java.util.List;

public class ValidationResult implements Serializable {
    private static final long serialVersionUID = 9034912753064967249L;
    private boolean passed;
    private List<String> failedReasonList;

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public List<String> getFailedReasonList() {
        return failedReasonList;
    }

    public void setFailedReasonList(List<String> failedReasonList) {
        this.failedReasonList = failedReasonList;
    }

    public String getFirstFailedReason() {
        return (failedReasonList != null && !failedReasonList.isEmpty()) ? failedReasonList.get(0) : null;
    }

}
