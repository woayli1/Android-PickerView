package com.woayli1.pickerview.adapter;

import com.woayli1.adapter.WheelAdapter;
import com.woayli1.pickerview.bean.DateObject;

import java.util.ArrayList;

/**
 * @author gc
 * @version 1.0.0
 * @projectName com.woayli1.pickerview.adapter
 * @description:
 * @date :2023/12/15 10:47
 */
public class StringWheelAdapter implements WheelAdapter {

    // items
    private ArrayList<DateObject> list;

    public StringWheelAdapter(ArrayList<DateObject> list) {
        this.list = list;
    }

    @Override
    public String getItem(int index) {
        if (index >= 0 && index < list.size()) {
            return list.get(index).getListItem();
        }
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int getItemsCount() {
        return list.size();
    }

}
