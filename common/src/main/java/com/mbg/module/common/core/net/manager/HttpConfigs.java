package com.mbg.module.common.core.net.manager;

class HttpConfigs {
    /**
     * 调试开关是否开启、默认关闭
     */
    public static final boolean debug = false;

    /**
     * 是否使用DNS解析，true:强制每次都使用httpDns; false: dns缓存中存在ip的就使用，否则就不使用
     */
    public static final boolean httpDnsForce = false;

    /**
     * httpDns失败后是否重试(目前国内版是true, 国际版是false，因为在印尼等市场必须每个接口都使用httpDns，但例如验证码等接口不允许重试)
     */
    public static final boolean httpDnsFailedRetry = true;

    /**
     * 是否使用OkHttp进行网络请求
     */
    public static final boolean usrOkHttp=true;

}
