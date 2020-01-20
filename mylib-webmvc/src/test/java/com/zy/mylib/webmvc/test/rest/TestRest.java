package com.zy.mylib.webmvc.test.rest;

import com.zy.mylib.webmvc.base.BaseRest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestRest extends BaseRest {
    @GetMapping("/{context}")
    public Map<String, String> testMono(@PathVariable String context) {
        Map ret = new HashMap<>(1);
        ret.put("hello", context);
        return ret;
    }
}
