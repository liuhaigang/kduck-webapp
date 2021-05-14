package cn.kduck.webapp;

import cn.kduck.core.remote.annotation.ProxyService;

@ProxyService(serviceName = "demoService")
public interface DemoRemoteService {

    public int add(int a,int b);
}
