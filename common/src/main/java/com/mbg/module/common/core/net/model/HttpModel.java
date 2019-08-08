package com.mbg.module.common.core.net.model;

import java.util.List;

/**
 *
 * {
 *     "code": 200,
 *     "message": "",
 *     "request_id": "123333",
 *     "request_time": 1394537010.2336,
 *     "response_time": 1394537010.2347,
 *     "data": [],  // 请求返回的数据，数组对象格式格式
 *     "extra": {}  // 请求返回的附加数据，对象格式
 *    }
 */
public class HttpModel<D,E extends BaseExtra> {
    public String code;
    public String message;
    public String request_id;
    public String request_time;
    public String response_time;
    public List<D> data;
    public E extra;


    public  boolean hasData(){
        if (data!=null){
            return (data.size()>0);
        }

        return false;
    }

    public boolean hasMore(){
        if (extra!=null){
            return (extra.hasmore==1);
        }

        return false;
    }

}
