package com.zy.mylib.webmvc.mybatis.test.sys.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy.mylib.webmvc.mybatis.test.sys.entity.ApiUser;
import com.zy.mylib.webmvc.mybatis.test.sys.service.IApiUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhouyang
 * @since 2020-07-25
 */
@RestController
@RequestMapping("/sys/api-user")
public class ApiUserController {
    @Autowired
    IApiUserService apiUserService;

    @GetMapping
    public IPage<ApiUser> page(Page<ApiUser> page) {
        return apiUserService.page(page);
    }
}

