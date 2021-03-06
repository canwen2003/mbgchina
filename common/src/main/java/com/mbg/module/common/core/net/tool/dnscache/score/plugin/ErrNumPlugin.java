package com.mbg.module.common.core.net.tool.dnscache.score.plugin;

import com.mbg.module.common.core.net.tool.dnscache.model.IpModel;
import com.mbg.module.common.core.net.tool.dnscache.score.IPlugIn;
import com.mbg.module.common.core.net.tool.dnscache.score.PlugInManager;

import java.util.ArrayList;



public class ErrNumPlugin implements IPlugIn {

    @Override
    public void run(ArrayList<IpModel> list) {
        // 查找到最大错误数
        float MAX_ERRNUM = 0;
        for (IpModel temp : list) {
            if (temp.err_num == null || temp.err_num.equals(""))
                continue;
            float err_num = Float.parseFloat(temp.err_num);
            MAX_ERRNUM = Math.max(MAX_ERRNUM, err_num);
        }
        // 计算比值
        if (MAX_ERRNUM == 0) {
            return;
        }
        float bi = getWeight() / MAX_ERRNUM;
        // 计算得分
        for (IpModel temp : list) {
            if (temp.err_num == null || temp.err_num.equals("")){
                continue;
            }
            float err_num = Float.parseFloat(temp.err_num);
            temp.grade += (err_num - (err_num * bi)); // 错误最少的 得分应该最高
        }
    }

    @Override
    public float getWeight() {
        return PlugInManager.ErrNumPluginNum;
    }

    @Override
    public boolean isActivated() {
        return true;
    }

}
