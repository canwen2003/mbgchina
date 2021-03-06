package com.mbg.module.common.core.net.tool.dnscache.score.plugin;

import com.mbg.module.common.core.net.tool.dnscache.model.IpModel;
import com.mbg.module.common.core.net.tool.dnscache.score.IPlugIn;
import com.mbg.module.common.core.net.tool.dnscache.score.PlugInManager;

import java.util.ArrayList;



public class PriorityPlugin implements IPlugIn {

    @Override
    public void run(ArrayList<IpModel> list) {
        // 查找到最大优先级
        float MAX_PRIORITY = 0;
        for (IpModel temp : list) {
            if (temp.priority == null || temp.priority.equals(""))
                continue;
            float priority = Float.parseFloat(temp.priority);
            MAX_PRIORITY = Math.max(MAX_PRIORITY, priority);
        }
        // 计算比值
        if (MAX_PRIORITY == 0) {
            return;
        }
        float bi = getWeight() / MAX_PRIORITY;
        // 计算得分
        for (IpModel temp : list) {
            if (temp.priority == null || temp.priority.equals("")){
                continue;
            }
            float priority = Float.parseFloat(temp.priority);
            temp.grade += (priority * bi);
        }

    }

    @Override
    public float getWeight() {
        return PlugInManager.PriorityPluginNum;
    }

    @Override
    public boolean isActivated() {
        return true;
    }

}
