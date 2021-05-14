package cn.kduck.webapp;

import org.springframework.stereotype.Service;

@Service
public class DemoRemoteServiceImpl implements DemoRemoteService{

    @Override
    public int add(int a, int b) {
        return a+b;
    }
}
