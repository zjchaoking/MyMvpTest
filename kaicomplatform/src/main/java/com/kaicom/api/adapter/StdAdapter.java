package com.kaicom.api.adapter;

import java.util.List;

import android.content.Context;
import android.widget.TextView;

import com.kaicom.fw.R;

@Deprecated
public class StdAdapter extends AbsCommonAdapter<String> {

    public StdAdapter(Context context, List<String> datas) {
        super(context, datas, R.layout.std_adapter_item);
    }

    @Override
    public void convert(ViewHolder holder, String data, int position) {
        TextView textView = holder.getView(R.id.std_adapter_text1);
        textView.setText(data);
    }

}
