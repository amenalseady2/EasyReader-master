package com.BlackDiamond2010.hzs.bean.ProductDetails;

import com.BlackDiamond2010.hzs.ui.activity.lives.bean.BaseBean;

import java.util.List;

public class Product extends BaseBean {
    @Override
    public String toString() {
        return "Product{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    /**
     * code : 200
     * msg : success
     * data : {"detail":{"id":1,"name":"路演神器","price":"398.00","local_price":"499.00","vip_price":"198.00","stock":111,"freight":"10.00","slider":"http://cdn.kanjian2020.com/image/2017/12/5/o_1c0ivqtaa18dm1d92dp7erump81t","share":"http://www.hzs2010.com/list/30/9.htm","spec":[{"name":"尺码","item":"X,XL,XXL"},{"name":"颜色","item":"红,绿,蓝"}],"info1":"咨询电话;400-178-2998 咨询时间:9:00-18:00","info2":"全国(除港澳台)配送范围你按市价收取 配送时间段3天内寄出","info3":"本产品使用顺丰快递","info4":"如需退货,请进入我的订单操作(到货七天系统自动确认)","detail":"http://api.kanjian2020.com/template?type=1&id=1","sale_num":2},"is_collection":0}
     */

    private int code;
    private String msg;
    private DataBean data;

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
                    "detail=" + detail +
                    ", is_collection=" + is_collection +
                    '}';
        }

        /**
         * detail : {"id":1,"name":"路演神器","price":"398.00","local_price":"499.00","vip_price":"198.00","stock":111,"freight":"10.00","slider":"http://cdn.kanjian2020.com/image/2017/12/5/o_1c0ivqtaa18dm1d92dp7erump81t","share":"http://www.hzs2010.com/list/30/9.htm","spec":[{"name":"尺码","item":"X,XL,XXL"},{"name":"颜色","item":"红,绿,蓝"}],"info1":"咨询电话;400-178-2998 咨询时间:9:00-18:00","info2":"全国(除港澳台)配送范围你按市价收取 配送时间段3天内寄出","info3":"本产品使用顺丰快递","info4":"如需退货,请进入我的订单操作(到货七天系统自动确认)","detail":"http://api.kanjian2020.com/template?type=1&id=1","sale_num":2}
         * is_collection : 0
         */

        private DetailBean detail;
        private int is_collection;

        public DetailBean getDetail() {
            return detail;
        }

        public void setDetail(DetailBean detail) {
            this.detail = detail;
        }

        public int getIs_collection() {
            return is_collection;
        }

        public void setIs_collection(int is_collection) {
            this.is_collection = is_collection;
        }

        public static class DetailBean {
            @Override
            public String toString() {
                return "DetailBean{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        ", price='" + price + '\'' +
                        ", local_price='" + local_price + '\'' +
                        ", vip_price='" + vip_price + '\'' +
                        ", stock=" + stock +
                        ", freight='" + freight + '\'' +
                        ", slider='" + slider + '\'' +
                        ", share='" + share + '\'' +
                        ", info1='" + info1 + '\'' +
                        ", info2='" + info2 + '\'' +
                        ", info3='" + info3 + '\'' +
                        ", info4='" + info4 + '\'' +
                        ", detail='" + detail + '\'' +
                        ", sale_num=" + sale_num +
                        ", spec=" + spec +
                        '}';
            }

            /**
             * id : 1
             * name : 路演神器
             * price : 398.00
             * local_price : 499.00
             * vip_price : 198.00
             * stock : 111
             * freight : 10.00
             * slider : http://cdn.kanjian2020.com/image/2017/12/5/o_1c0ivqtaa18dm1d92dp7erump81t
             * share : http://www.hzs2010.com/list/30/9.htm
             * spec : [{"name":"尺码","item":"X,XL,XXL"},{"name":"颜色","item":"红,绿,蓝"}]
             * info1 : 咨询电话;400-178-2998 咨询时间:9:00-18:00
             * info2 : 全国(除港澳台)配送范围你按市价收取 配送时间段3天内寄出
             * info3 : 本产品使用顺丰快递
             * info4 : 如需退货,请进入我的订单操作(到货七天系统自动确认)
             * detail : http://api.kanjian2020.com/template?type=1&id=1
             * sale_num : 2
             */

            private int id;
            private String name;
            private String price;
            private String local_price;
            private String vip_price;
            private int stock;
            private String freight;
            private String slider;
            private String share;
            private String info1;
            private String info2;
            private String info3;
            private String info4;
            private String detail;
            private int sale_num;
            private List<SpecBean> spec;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getLocal_price() {
                return local_price;
            }

            public void setLocal_price(String local_price) {
                this.local_price = local_price;
            }

            public String getVip_price() {
                return vip_price;
            }

            public void setVip_price(String vip_price) {
                this.vip_price = vip_price;
            }

            public int getStock() {
                return stock;
            }

            public void setStock(int stock) {
                this.stock = stock;
            }

            public String getFreight() {
                return freight;
            }

            public void setFreight(String freight) {
                this.freight = freight;
            }

            public String getSlider() {
                return slider;
            }

            public void setSlider(String slider) {
                this.slider = slider;
            }

            public String getShare() {
                return share;
            }

            public void setShare(String share) {
                this.share = share;
            }

            public String getInfo1() {
                return info1;
            }

            public void setInfo1(String info1) {
                this.info1 = info1;
            }

            public String getInfo2() {
                return info2;
            }

            public void setInfo2(String info2) {
                this.info2 = info2;
            }

            public String getInfo3() {
                return info3;
            }

            public void setInfo3(String info3) {
                this.info3 = info3;
            }

            public String getInfo4() {
                return info4;
            }

            public void setInfo4(String info4) {
                this.info4 = info4;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public int getSale_num() {
                return sale_num;
            }

            public void setSale_num(int sale_num) {
                this.sale_num = sale_num;
            }

            public List<SpecBean> getSpec() {
                return spec;
            }

            public void setSpec(List<SpecBean> spec) {
                this.spec = spec;
            }

            public static class SpecBean {
                @Override
                public String toString() {
                    return "SpecBean{" +
                            "name='" + name + '\'' +
                            ", item='" + item + '\'' +
                            '}';
                }

                /**
                 * name : 尺码
                 * item : X,XL,XXL
                 */

                private String name;
                private String item;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getItem() {
                    return item;
                }

                public void setItem(String item) {
                    this.item = item;
                }
            }
        }
    }
}
