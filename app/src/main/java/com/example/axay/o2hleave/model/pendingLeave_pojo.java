package com.example.axay.o2hleave.model;

import java.util.List;

/**
 * Created by axay on 25/08/17.
 */

public class pendingLeave_pojo {




        private String type;
        private String leave_reason;
        private String from;
        private String to;

    public pendingLeave_pojo(String type, String leave_reason, String from, String to) {
        this.type = type;
        this.leave_reason = leave_reason;
        this.from = from;
        this.to = to;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLeave_reason() {
        return leave_reason;
    }

    public void setLeave_reason(String leave_reason) {
        this.leave_reason = leave_reason;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}