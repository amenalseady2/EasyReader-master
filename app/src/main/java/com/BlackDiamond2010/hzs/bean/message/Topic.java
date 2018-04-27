package com.BlackDiamond2010.hzs.bean.message;

import java.util.List;

public class Topic {

    /**
     * code : 200
     * msg : success
     * data : {"list":[{"id":5,"url":"https://gitee.com/","title":"放码过来","info":"码云专为开发者提供稳定、高效、安全的云端软件开发协作平台\n\n无论是个人、团队、或是企业，都能够用码云实现代码托管、项目管理、协作开发"},{"id":4,"url":"https://www.qiniu.com/","title":"持续挖掘海量数据的无限价值","info":"所有数据都有有效期，面对不断膨胀的数据领域，数据如何管理？七牛云完全自主知识产权的对象存储，已经过最严酷的考验，为 EB 规模的数据存储做好充分准备。"}],"next":0}
     */

    private int code;
    private String msg;
    private DataBean data;

    @Override
    public String toString() {
        return "Topic{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        @Override
        public String toString() {
            return "DataBean{" +
                    "next=" + next +
                    ", list=" + list +
                    '}';
        }

        /**
         * list : [{"id":5,"url":"https://gitee.com/","title":"放码过来","info":"码云专为开发者提供稳定、高效、安全的云端软件开发协作平台\n\n无论是个人、团队、或是企业，都能够用码云实现代码托管、项目管理、协作开发"},{"id":4,"url":"https://www.qiniu.com/","title":"持续挖掘海量数据的无限价值","info":"所有数据都有有效期，面对不断膨胀的数据领域，数据如何管理？七牛云完全自主知识产权的对象存储，已经过最严酷的考验，为 EB 规模的数据存储做好充分准备。"}]
         * next : 0
         */

        private int next;
        private List<ListBean> list;

        public int getNext() {
            return next;
        }

        public void setNext(int next) {
            this.next = next;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {

            /**
             * id : 5
             * url : https://gitee.com/
             * title : 放码过来
             * info : 码云专为开发者提供稳定、高效、安全的云端软件开发协作平台

             无论是个人、团队、或是企业，都能够用码云实现代码托管、项目管理、协作开发
             */

            private int id;
            private String url;
            private String title;
            private String info;
            private String create_at;

            public String getCreate_at() {
                return create_at;
            }

            public void setCreate_at(String create_at) {
                this.create_at = create_at;
            }

            @Override
            public String toString() {
                return "ListBean{" +
                        "id=" + id +
                        ", url='" + url + '\'' +
                        ", title='" + title + '\'' +
                        ", info='" + info + '\'' +
                        ", create_at='" + create_at + '\'' +
                        '}';
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getInfo() {
                return info;
            }

            public void setInfo(String info) {
                this.info = info;
            }
        }
    }
}
