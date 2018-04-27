package com.BlackDiamond2010.hzs.bean.chat;

import java.util.List;

public class Chat {

    /**
     * code : 200
     * msg : success
     * data : {"list":[{"id":34,"nickname":"bilibili","avatar":"https://cdn.duitang.com/uploads/item/201408/04/20140804081104_uZZSh.thumb.700_0.jpeg","content":"说了有用吗？我知道今日的你可以给予一切：权势、地位、金钱\u2014\u2014但是，你能给我幸福吗？"},{"id":35,"nickname":"bilibili","avatar":"https://cdn.duitang.com/uploads/item/201408/04/20140804081104_uZZSh.thumb.700_0.jpeg","content":"其实你根本不需要道歉，因为道歉根本就没有用，失去的东西失去了，伤害了还是伤害，道歉并不能让时间倒转，也不能让发生的事情过去。"},{"id":36,"nickname":"bilibili","avatar":"https://cdn.duitang.com/uploads/item/201408/04/20140804081104_uZZSh.thumb.700_0.jpeg","content":"从小我就懂得保护自己，我知道要想不被人拒绝，最好的办法就是先拒绝别人。"}],"mark_id":36}
     */

    private int code;
    private String msg;
    private DataBean data;

    @Override
    public String toString() {
        return "Chat{" +
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
                    "mark_id=" + mark_id +
                    ", list=" + list +
                    '}';
        }

        /**
         * list : [{"id":34,"nickname":"bilibili","avatar":"https://cdn.duitang.com/uploads/item/201408/04/20140804081104_uZZSh.thumb.700_0.jpeg","content":"说了有用吗？我知道今日的你可以给予一切：权势、地位、金钱\u2014\u2014但是，你能给我幸福吗？"},{"id":35,"nickname":"bilibili","avatar":"https://cdn.duitang.com/uploads/item/201408/04/20140804081104_uZZSh.thumb.700_0.jpeg","content":"其实你根本不需要道歉，因为道歉根本就没有用，失去的东西失去了，伤害了还是伤害，道歉并不能让时间倒转，也不能让发生的事情过去。"},{"id":36,"nickname":"bilibili","avatar":"https://cdn.duitang.com/uploads/item/201408/04/20140804081104_uZZSh.thumb.700_0.jpeg","content":"从小我就懂得保护自己，我知道要想不被人拒绝，最好的办法就是先拒绝别人。"}]
         * mark_id : 36
         */

        private int mark_id;
        private List<ListBean> list;

        public int getMark_id() {
            return mark_id;
        }

        public void setMark_id(int mark_id) {
            this.mark_id = mark_id;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            @Override
            public String toString() {
                return "ListBean{" +
                        "id=" + id +
                        ", nickname='" + nickname + '\'' +
                        ", avatar='" + avatar + '\'' +
                        ", content='" + content + '\'' +
                        '}';
            }

            /**
             * id : 34
             * nickname : bilibili
             * avatar : https://cdn.duitang.com/uploads/item/201408/04/20140804081104_uZZSh.thumb.700_0.jpeg
             * content : 说了有用吗？我知道今日的你可以给予一切：权势、地位、金钱——但是，你能给我幸福吗？
             */

            private int id;
            private String nickname;
            private String avatar;
            private String content;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
