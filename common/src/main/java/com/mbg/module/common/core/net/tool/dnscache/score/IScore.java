package com.mbg.module.common.core.net.tool.dnscache.score;

import com.mbg.module.common.core.net.tool.dnscache.model.DomainModel;
import com.mbg.module.common.core.net.tool.dnscache.model.IpModel;

import java.util.ArrayList;
/**
 * Created by fenglei on 15/4/21.
 */
public interface IScore {

    public String[] serverIpScore(DomainModel domainModel) ;
    
    public String[] ListToArr(ArrayList<IpModel> list) ;
}
