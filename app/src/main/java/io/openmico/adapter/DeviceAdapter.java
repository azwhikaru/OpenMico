package io.openmico.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import io.openmico.R;
import io.openmico.beans.DeviceBean;

public class DeviceAdapter extends BaseQuickAdapter<DeviceBean, BaseViewHolder> {
    public DeviceAdapter(List<DeviceBean> data) {
        super(R.layout.item_device, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, DeviceBean deviceBean) {
        holder.setText(R.id.tv_device_name, deviceBean.getAlias() + "(" + deviceBean.getName() + ")")
                .setText(R.id.tv_device_sn, deviceBean.getSerialNumber());
    }
}
