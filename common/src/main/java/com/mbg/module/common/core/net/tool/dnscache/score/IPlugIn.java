package com.mbg.module.common.core.net.tool.dnscache.score;

import com.mbg.module.common.core.net.tool.dnscache.model.IpModel;

import java.util.ArrayList;



public interface IPlugIn {

	/**
	 * 插件实现计算分值的方法
	 */
	public abstract void run(ArrayList<IpModel> list);
	
	abstract float getWeight();
	
	abstract boolean isActivated();
}
