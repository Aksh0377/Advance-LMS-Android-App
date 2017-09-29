package com.example.axay.o2hleave.model;

import java.util.List;

/**
 * Created by axay on 01/08/17.
 */

public class profile_pojo {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * identity : 1
         * first_name : akshay
         * email : akshay@o2h.com
         akshay@o2h.com
         * user_designation : dev
         * avatar : null
         * employee_code : 348
         */

        private String identity;
        private String first_name;
        private String email;
        private String user_designation;
        private Object avatar;
        private String employee_code;

        public String getIdentity() {
            return identity;
        }

        public void setIdentity(String identity) {
            this.identity = identity;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUser_designation() {
            return user_designation;
        }

        public void setUser_designation(String user_designation) {
            this.user_designation = user_designation;
        }

        public Object getAvatar() {
            return avatar;
        }

        public void setAvatar(Object avatar) {
            this.avatar = avatar;
        }

        public int getEmployee_code() {
            return Integer.parseInt(employee_code);
        }

        public void setEmployee_code(String employee_code) {
            this.employee_code = employee_code;
        }
    }
}
