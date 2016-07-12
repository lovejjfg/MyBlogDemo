package com.lovejjfg.blogdemo.ui.indicator;

import java.util.List;

/**
 * Created by Joe on 2016-07-07
 * Email: zhangjun166@pingan.com.cn
 */
public class LockEntity {

    /**
     * code : 0
     * data : {"lock_entity":{"lock_list":[{"bedroom_des":"","bedroom_id":777,"bedroom_state":1,"house_addr":"浦东软件园-过颐景园-A栋-606","house_id":478,"maindoor_des":"密码输错十次，已锁定","maindoor_id":666,"maindoor_state":3,"tel_num":"400000000"},{"bedroom_des":"","bedroom_id":999,"bedroom_state":1,"house_addr":"浦东软件园-过颐景园-C栋-606","house_id":784,"maindoor_des":"","maindoor_id":888,"maindoor_state":1,"tel_num":"400000001"}]}}
     * message : ok
     */

    private int code;
    /**
     * lock_entity : {"lock_list":[{"bedroom_des":"","bedroom_id":777,"bedroom_state":1,"house_addr":"浦东软件园-过颐景园-A栋-606","house_id":478,"maindoor_des":"密码输错十次，已锁定","maindoor_id":666,"maindoor_state":3,"tel_num":"400000000"},{"bedroom_des":"","bedroom_id":999,"bedroom_state":1,"house_addr":"浦东软件园-过颐景园-C栋-606","house_id":784,"maindoor_des":"","maindoor_id":888,"maindoor_state":1,"tel_num":"400000001"}]}
     */

    private DataEntity data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataEntity {
        private LockEntityEntity lock_entity;

        public LockEntityEntity getLock_entity() {
            return lock_entity;
        }

        public void setLock_entity(LockEntityEntity lock_entity) {
            this.lock_entity = lock_entity;
        }

        public static class LockEntityEntity {
            /**
             * bedroom_des :
             * bedroom_id : 777
             * bedroom_state : 1
             * house_addr : 浦东软件园-过颐景园-A栋-606
             * house_id : 478
             * maindoor_des : 密码输错十次，已锁定
             * maindoor_id : 666
             * maindoor_state : 3
             * tel_num : 400000000
             */

            private List<LockListEntity> lock_list;

            public List<LockListEntity> getLock_list() {
                return lock_list;
            }

            public void setLock_list(List<LockListEntity> lock_list) {
                this.lock_list = lock_list;
            }

            public static class LockListEntity {
                private String bedroom_des;
                private int bedroom_id;
                private int bedroom_state;
                private String house_addr;
                private int house_id;
                private String maindoor_des;
                private int maindoor_id;
                private int maindoor_state;
                private String tel_num;

                public String getBedroom_des() {
                    return bedroom_des;
                }

                public void setBedroom_des(String bedroom_des) {
                    this.bedroom_des = bedroom_des;
                }

                public int getBedroom_id() {
                    return bedroom_id;
                }

                public void setBedroom_id(int bedroom_id) {
                    this.bedroom_id = bedroom_id;
                }

                public int getBedroom_state() {
                    return bedroom_state;
                }

                public void setBedroom_state(int bedroom_state) {
                    this.bedroom_state = bedroom_state;
                }

                public String getHouse_addr() {
                    return house_addr;
                }

                public void setHouse_addr(String house_addr) {
                    this.house_addr = house_addr;
                }

                public int getHouse_id() {
                    return house_id;
                }

                public void setHouse_id(int house_id) {
                    this.house_id = house_id;
                }

                public String getMaindoor_des() {
                    return maindoor_des;
                }

                public void setMaindoor_des(String maindoor_des) {
                    this.maindoor_des = maindoor_des;
                }

                public int getMaindoor_id() {
                    return maindoor_id;
                }

                public void setMaindoor_id(int maindoor_id) {
                    this.maindoor_id = maindoor_id;
                }

                public int getMaindoor_state() {
                    return maindoor_state;
                }

                public void setMaindoor_state(int maindoor_state) {
                    this.maindoor_state = maindoor_state;
                }

                public String getTel_num() {
                    return tel_num;
                }

                public void setTel_num(String tel_num) {
                    this.tel_num = tel_num;
                }
            }
        }
    }
}
