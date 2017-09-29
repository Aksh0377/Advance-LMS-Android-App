package com.example.axay.o2hleave.model;

import java.util.List;

/**
 * Created by axay on 31/08/17.
 */

public class leave_request_pojo {


    private List<DataBeanXX> data;

    public List<DataBeanXX> getData() {
        return data;
    }

    public void setData(List<DataBeanXX> data) {
        this.data = data;
    }

    public static class DataBeanXX {
        /**
         * leave : {"data":[{"identity":134,"type":"sick","leave_reason":"nothing","from":"2017-07-23","to":"2017-07-23","users":{"data":{"identity":17,"first_name":"def","email":"jaya@gmail.com","avatar":"http://192.168.1.104/leave/public/avatar/archa.png\n"}}}]}
         */

        private LeaveBean leave;

        public LeaveBean getLeave() {
            return leave;
        }

        public void setLeave(LeaveBean leave) {
            this.leave = leave;
        }

        public static class LeaveBean {
            private List<DataBeanX> data;

            public List<DataBeanX> getData() {
                return data;
            }

            public void setData(List<DataBeanX> data) {
                this.data = data;
            }

            public static class DataBeanX {
                /**
                 * identity : 134
                 * type : sick
                 * leave_reason : nothing
                 * from : 2017-07-23
                 * to : 2017-07-23
                 * users : {"data":{"identity":17,"first_name":"def","email":"jaya@gmail.com","avatar":"http://192.168.1.104/leave/public/avatar/archa.png\n"}}
                 */


                private String identity;
                private String type;
                private String leave_reason;
                private String from;
                private String to;
                private String users;
                private String first_name;
                private String avatar;



                public DataBeanX(String first_name, String identity, String type, String leave_reason, String from, String to, String users) {
                    this.identity = identity;
                    this.type = type;
                    this.leave_reason = leave_reason;
                    this.from = from;
                    this.to = to;
                    this.users = users;
                    this.first_name = first_name;
                }


                public String getIdentity() {
                    return identity;
                }

                public void setIdentity(String identity) {
                    this.identity = identity;
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

                public String getUsers() {
                    return String.valueOf(users);
                }
                public String getFirst_name() {
                    return first_name;
                }

                public void setFirst_name(String first_name) {
                    this.first_name = first_name;
                }

                public void setUsers(UsersBean users) {
                    this.users = String.valueOf(users);
                }
                public String getAvatar() {
                    return avatar;
                }

                public void setAvatar(String avatar) {
                    this.avatar = avatar;
                }

                public static class UsersBean {
                    /**
                     * data : {"identity":17,"first_name":"def","email":"jaya@gmail.com","avatar":"http://192.168.1.104/leave/public/avatar/archa.png\n"}
                     */

                    private DataBean data;

                    public DataBean getData() {
                        return data;
                    }

                    public void setData(DataBean data) {
                        this.data = data;
                    }

                    public static class DataBean {
                        /**
                         * identity : 17
                         * first_name : def
                         * email : jaya@gmail.com
                         * avatar : http://192.168.1.104/leave/public/avatar/archa.png

                         */

                        private int identity;
                        private String first_name;
                        private String email;
                        private String avatar;

                        public int getIdentity() {
                            return identity;
                        }

                        public void setIdentity(int identity) {
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

                        public String getAvatar() {
                            return avatar;
                        }

                        public void setAvatar(String avatar) {
                            this.avatar = avatar;
                        }
                    }
                }
            }
        }
    }
}
