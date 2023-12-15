package com.woayli1.pickerviewdemo;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.woayli1.pickerview.builder.OptionsPickerBuilder;
import com.woayli1.pickerview.builder.TimePickerBuilder;
import com.woayli1.pickerview.listener.CustomListener;
import com.woayli1.pickerview.listener.OnOptionsSelectListener;
import com.woayli1.pickerview.listener.OnTimeSelectChangeListener;
import com.woayli1.pickerview.listener.OnTimeSelectListener;
import com.woayli1.pickerview.view.OptionsPickerView;
import com.woayli1.pickerview.view.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TestFragment extends Fragment {
    private View mView;
    private Button btnShow, btnTest, btnTest2;
    private TimePickerView pvTime, pvTest2;
    private FrameLayout mFrameLayout;

    private List<String> list = new ArrayList<>();

    //当前时间
    private Calendar selectedDate = Calendar.getInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_test, null);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnShow = (Button) mView.findViewById(R.id.btn_show);
        btnTest = (Button) mView.findViewById(R.id.btn_test);
        btnTest2 = (Button) mView.findViewById(R.id.btn_test2);

        mFrameLayout = (FrameLayout) mView.findViewById(R.id.fragmen_fragment);

        initData();

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvTime.show(v, false);//弹出时间选择器，传递参数过去，回调的时候则可以绑定此view
            }
        });

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPickerView(getContext(),
                        -1,
                        list,
                        (TextView) view,
                        "请选择应急事件");
            }
        });

        btnTest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pvTest2.show(view);
            }
        });

    }

    private void initData() {
        initTimePicker();
        initBtnTest2PickerView();
        list.add("火灾事件");
        list.add("应急事件03");
        list.add("火灾事件");
        list.add("应急事件02");
        list.add("名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字名字");
        list.add("火灾事件");
        list.add("案例测试");
    }

    private void initTimePicker() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();

        Calendar startDate = Calendar.getInstance();
        startDate.set(2013, 0, 23);

        Calendar endDate = Calendar.getInstance();
        endDate.set(2019, 11, 28);
        //时间选择器
        pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                /*btn_Time.setText(getTime(date));*/
                Button btn = (Button) v;
                btn.setText(getTime(date));
            }
        })
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime.returnData();
                                /*pvTime.dismiss();*/
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                /*pvTime.dismiss();*/
                            }
                        });
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("", "", "", "", "", "") //设置空字符串以隐藏单位提示   hide label
                .setDividerColor(Color.DKGRAY)
                .setContentTextSize(20)
                .setDate(selectedDate)
                .setRangDate(startDate, selectedDate)
                .setDecorView(mFrameLayout)//非dialog模式下,设置ViewGroup, pickerView将会添加到这个ViewGroup中
                .setOutSideColor(0x00000000)
                .setOutSideCancelable(false)
                .build();

        pvTime.setKeyBackCancelable(false);//系统返回键监听屏蔽掉
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    /**
     * 选择器
     *
     * @param options       : 上次选择的标记
     * @param options1Items : 需要单选的 集合
     * @param view          : 触发选择控件
     * @param title         : 标题
     */
    public static void showPickerView(Context context,
                                      final int options,
                                      final List<String> options1Items,
                                      final TextView view,
                                      String title) {
        OptionsPickerView<String> pvOptions = new OptionsPickerBuilder(context,
                new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        if (options1Items == null || options1Items.size() == 0) {
                            return;
                        }
                        String text = options1Items.get(options1);
                        if (options == options1) {
                            return;
                        }
                        view.setText(text);
                        view.setTag(options1);

                    }
                })
                .setTitleText(title)
                .setTitleSize(15)
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK)
                .setContentTextSize(13)
                .setSelectOptions(options)
                .build();
        pvOptions.setPicker(options1Items);
        pvOptions.show(view);
    }

    public void initBtnTest2PickerView() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11

        Calendar startDate = Calendar.getInstance();
        startDate.set(2020, 0, 23);

        Calendar endDate = Calendar.getInstance();
        endDate.set(2040, 11, 28);
        //时间选择器
        pvTest2 = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                /*btn_Time.setText(getTime(date));*/
                Button btn = (Button) v;
                btn.setText(getTime(date));
            }
        })
                .setLayoutRes(R.layout.pickerview_test2, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        TextView tvTitle = (TextView) v.findViewById(R.id.tvTitle);
                        TextView tvClear = (TextView) v.findViewById(R.id.tv_clear);
                        TextView tvCancel = (TextView) v.findViewById(R.id.tv_cancel);
                        TextView tvConfirm = (TextView) v.findViewById(R.id.tv_confirm);
                        ImageView ivClose = (ImageView) v.findViewById(R.id.iv_close);

                        tvTitle.setText(getTimeNoSS(selectedDate.getTime()));

                        tvClear.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                pvTest2.setDate(selectedDate);
                                pvTest2.setTitleText(getTimeNoSS(selectedDate.getTime()));
                            }
                        });

                        tvCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTest2.dismiss();
                            }
                        });
                        ivClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTest2.dismiss();
                            }
                        });

                        tvConfirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTest2.returnData();
                            }
                        });
                    }
                })
                .setType(new boolean[]{false, false, true, true, true, false})
                .setLabel("", "", "", "", "", "") //设置空字符串以隐藏单位提示   hide label
                .setDividerColor(Color.parseColor("#aaaaaa"))
                .setContentTextSize(18)
                .setItemVisibleCount(7)
                .setDate(selectedDate)
                .isCyclic(true)
                .setLineSpacingMultiplier(3f)
                .setAfterDay(30)
                .setBeforeDay(30)
                .setRangDate(startDate, endDate)
                .setOutSideCancelable(false)
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        pvTest2.setTitleText(getTimeNoSS(date));
                    }
                })
                .build();

        pvTest2.setMonthDayWeek(true);
    }

    private String getTimeNoSS(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        return format.format(date);
    }
}
