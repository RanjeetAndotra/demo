package com.Project.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DemoServiceImpl implements DemoService{

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public Object saveData() {

        return null;
    }
}
