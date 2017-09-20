package com.example.axay.o2hleave.model;

import java.util.List;

/**
 * Created by axay on 29/07/17.
 */

public class LeaveHistory_pojo {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * identity : null
         * type : sick
         * leave_reason : null
         * from : 2017-07-12
         * to : 2017-07-13
         * LeaveTaggedUsers : {"data":[]}
         */

        private Object identity;
        private String type;
        private Object leave_reason;
        private String from;
        private String to;
        private LeaveTaggedUsersBean LeaveTaggedUsers;

        public DataBean( String type, String leave_reason, String from, String to) {
            this.type = type;
            this.leave_reason = leave_reason;
            this.from = from;
            this.to = to;
        }

        public Object getIdentity() {
            return identity;
        }

        public void setIdentity(Object identity) {
            this.identity = identity;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getLeave_reason() {
            return (String) leave_reason;
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

        public LeaveTaggedUsersBean getLeaveTaggedUsers() {
            return LeaveTaggedUsers;
        }

        public void setLeaveTaggedUsers(LeaveTaggedUsersBean LeaveTaggedUsers) {
            this.LeaveTaggedUsers = LeaveTaggedUsers;
        }

        public static class LeaveTaggedUsersBean {
            private List<?> data;

            public List<?> getData() {
                return data;
            }

            public void setData(List<?> data) {
                this.data = data;
            }
        }
    }
}
