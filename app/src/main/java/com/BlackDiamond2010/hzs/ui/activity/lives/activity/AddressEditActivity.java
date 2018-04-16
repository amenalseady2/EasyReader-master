package com.BlackDiamond2010.hzs.ui.activity.lives.activity;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.BlackDiamond2010.hzs.R;
import com.BlackDiamond2010.hzs.app.MyApplication;
import com.BlackDiamond2010.hzs.ui.activity.base.BaseActivity;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.AddressModel;
import com.BlackDiamond2010.hzs.ui.activity.lives.bean.HttpResult;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpResultCall;
import com.BlackDiamond2010.hzs.ui.activity.lives.network.HttpUtil;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.AndroidUtils;
import com.BlackDiamond2010.hzs.ui.activity.lives.util.CheckStringUtil;
import com.bigkoo.pickerview.OptionsPickerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AddressEditActivity extends BaseActivity {

    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.address_detail)
    EditText addressDetail;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    private OptionsPickerView pvOptions;//三级滚轮
    private String province;
    private String city;
    private String addressArea;
    //  省份
    ArrayList<String> provinceBeanList = new ArrayList<>();
    //  城市
    ArrayList<String> cities;
    ArrayList<List<String>> cityList = new ArrayList<>();
    //  区/县
    ArrayList<String> district;
    ArrayList<List<String>> districts;
    ArrayList<List<List<String>>> districtList = new ArrayList<>();
    public String district1;
    public int moren = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address_edit;
    }

    AddressModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = (AddressModel) getIntent().getSerializableExtra("model");
        if (model == null) {
            setTitle("新增地址");
        } else {
            setTitle("编辑地址");
            setData(model);
        }

        tvRight.setText("保存");
        tvRight.setVisibility(View.VISIBLE);


        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked = true) {
                    moren = 1;
                } else {
                    moren = 0;
                }
            }
        });

    }

    public void setData(AddressModel model) {
        name.setText(model.name);
        phone.setText(model.phone);
        address.setText(model.address);
        addressDetail.setText(model.info);
        if (model.type == 1) {
            checkbox.setChecked(true);
        } else {
            checkbox.setChecked(false);
        }
    }


    @OnClick({R.id.tv_right, R.id.address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                String nameStr = name.getText().toString();
                if ("".equals(nameStr)) {
                    mackToastLONG("请输入姓名", getApplicationContext());
                    return;
                }

                String phoneStr = phone.getText().toString();
                if ("".equals(phoneStr)) {
                    mackToastLONG("请输入手机号", getApplicationContext());
                    return;
                }
                if (!CheckStringUtil.checkPhone(phoneStr)) {
                    mackToastLONG("手机号有误", getApplicationContext());
                    return;
                }
                String addressStr = address.getText().toString();
                if ("".equals(addressStr)) {
                    mackToastLONG("请选择省市区", getApplicationContext());
                    return;
                }
                String detail = addressDetail.getText().toString();
                if ("".equals(detail)) {
                    mackToastLONG("请输入详细地址", getApplicationContext());
                    return;
                }
                if (model  == null){
                    toSave(nameStr, phoneStr, addressStr, detail, checkbox.isChecked());
                }else {
                    toAlter(nameStr, phoneStr, addressStr, detail, checkbox.isChecked());
                }


                break;
            case R.id.address:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

// 获取软键盘的显示状态
                boolean isOpen = imm.isActive();

// 如果软键盘已经显示，则隐藏，反之则显示
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

// 隐藏软键盘

                initOptions();
                pvOptions.show();
                break;
        }
    }

    private void toSave(String nameStr, String phoneStr, String addressStr, String detail, boolean checked) {
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                        addAddress(nameStr, phoneStr, addressStr, detail, moren, AndroidUtils.getAndroidId(this)),
                new HttpResultCall<HttpResult<Object>, Object>() {


                    @Override
                    public void onResponse(Object shopBeen, String msg) {
                        mackToastLONG(msg, getApplicationContext());
                        finish();

                    }

                    @Override
                    public void onErr(String err, int status) {
                        super.onErr(err, status);
                    }

                    @Override
                    public void onCompleted() {
                    }
                });
    }
    private void toAlter(String nameStr, String phoneStr, String addressStr, String detail, boolean checked) {
        addMainSubscription(HttpUtil.getInstance(MyApplication.instance.getApplicationContext()).sendRequest().
                       alterAddress(model.id,nameStr, phoneStr, addressStr, detail, moren, AndroidUtils.getAndroidId(this)),
                new HttpResultCall<HttpResult<Object>, Object>() {


                    @Override
                    public void onResponse(Object shopBeen, String msg) {
                        mackToastLONG(msg, getApplicationContext());
                        finish();

                    }

                    @Override
                    public void onErr(String err, int status) {
                        super.onErr(err, status);
                    }

                    @Override
                    public void onCompleted() {
                    }
                });
    }


    private void initOptions() {
        pvOptions = new OptionsPickerView(this);
        //  获取json数据
        String province_data_json = getJson(this, "province_data.json");
        //  解析json数据
        parseJson(province_data_json);
        //  设置三级联动效果
        pvOptions.setPicker(provinceBeanList, cityList, districtList, true);

//        pvOptions.
        //  设置选择的三级单位
        //pvOptions.setLabels("省", "市", "区");
        //pvOptions.setTitle("选择城市");
        //  设置是否循环滚动
        pvOptions.setCyclic(false, false, false);
        // 设置默认选中的三级项目
        pvOptions.setSelectOptions(0, 0, 0);
        //  监听确定选择按钮
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String citys = provinceBeanList.get(options1);
                //  如果是直辖市或者特别行政区只设置市和区/县
                if ("北京市".equals(citys) || "上海市".equals(citys) || "天津市".equals(citys) || "重庆市"
                        .equals(citys) || "澳门".equals(citys) || "香港".equals(citys)) {
                    province = provinceBeanList.get(options1);
                    city = province;
                    district1 = districtList.get(options1).get(option2).get(options3);
                    addressArea = province
                            + " " + district1;
                } else {
                    province = provinceBeanList.get(options1);
                    city = cityList.get(options1).get(option2);
                    district1 = districtList.get(options1).get(option2).get(options3);
                    addressArea = province
                            + " " + city
                            + " " + district1;
                }
                address.setText(addressArea);
            }
        });
    }

    public String getJson(Context context, String fileName) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(fileName);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bufferedInputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                baos.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return baos.toString();
    }

    //  解析json填充集合
    public void parseJson(String str) {
        try {
            //  获取json中的数组
            JSONArray jsonArray = new JSONArray(str);
            //  遍历数据组
            for (int i = 0; i < jsonArray.length(); i++) {
                //  获取省份的对象
                JSONObject provinceObject = jsonArray.optJSONObject(i);
                //  获取省份名称放入集合
                String provinceName = provinceObject.getString("name");
                provinceBeanList.add(provinceName);
                //  获取城市数组
                JSONArray cityArray = provinceObject.optJSONArray("city");
                cities = new ArrayList<>();//   声明存放城市的集合
                districts = new ArrayList<>();//声明存放区县集合的集合
                //  遍历城市数组
                for (int j = 0; j < cityArray.length(); j++) {
                    //  获取城市对象
                    JSONObject cityObject = cityArray.optJSONObject(j);
                    //  将城市放入集合
                    String cityName = cityObject.optString("name");
                    cities.add(cityName);
                    district = new ArrayList<>();// 声明存放区县的集合
                    //  获取区县的数组
                    JSONArray areaArray = cityObject.optJSONArray("area");
                    //  遍历区县数组，获取到区县名称并放入集合
                    for (int k = 0; k < areaArray.length(); k++) {
                        String areaName = areaArray.getString(k);
                        district.add(areaName);
                    }
                    //  将区县的集合放入集合
                    districts.add(district);
                }
                //  将存放区县集合的集合放入集合
                districtList.add(districts);
                //  将存放城市的集合放入集合
                cityList.add(cities);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
