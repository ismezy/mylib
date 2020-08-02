package com.zy.mylib.webmvc.mybatis.test;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zy.mylib.base.exception.BusException;
import com.zy.mylib.utils.RandomUtils;
import com.zy.mylib.webmvc.mybatis.test.sys.entity.ApiUser;
import com.zy.mylib.webmvc.mybatis.test.sys.service.IApiUserService;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.inject.Inject;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybatisTest.class)
@EnableAutoConfiguration
@AutoConfigureMockMvc
@ComponentScan("com.zy.mylib.webmvc.mybatis.test.**")
@MapperScan("com.zy.mylib.webmvc.mybatis.test.sys.mapper")
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@EnableTransactionManagement
public class MybatisTest {
    @Inject
    private IApiUserService apiUserService;
    @Inject
    ObjectMapper objectMapper;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void t01AddTest() throws JsonProcessingException {
        ApiUser apiUser = new ApiUser();
        apiUser.setCode("11111");
        apiUser.setName("测试1");
        apiUser.setSecret(RandomUtils.randomFor6());
        apiUser = apiUserService.add(apiUser);
        System.out.println("-------------------------");
        System.out.println(objectMapper.writeValueAsString(apiUser));
        System.out.println("-------------------------");
        apiUser.setId(null);
        apiUser.setCode("22222");
        apiUser.setName("测试2");
        apiUser = apiUserService.add(apiUser);
        System.out.println("-------------------------");
        System.out.println(objectMapper.writeValueAsString(apiUser));
        System.out.println("-------------------------");
        System.out.println(objectMapper.writeValueAsString(apiUserService.list()));
        System.out.println("-------------------------");
    }

    /**
     * 已存在添加测试
     */
    @Test
    public void t02ExistAdd() throws JsonProcessingException {
        thrown.expect(BusException.class);
        ApiUser apiUser = new ApiUser();
        apiUser.setCode("11111");
        apiUser.setName("测试1");
        apiUser.setSecret(RandomUtils.randomFor6());
        apiUser = apiUserService.add(apiUser);
        System.out.println(objectMapper.writeValueAsString(apiUser));
    }

    @Test
    public void  t03Update() throws JsonProcessingException {
        ApiUser apiUser = apiUserService.findByCode("11111");
        apiUser.setName("测试111111");
        apiUser.setSecret(RandomUtils.randomFor6());
        apiUserService.update(apiUser);
        System.out.println(objectMapper.writeValueAsString(apiUser));
    }

    @Test
    public void t04Update() throws JsonProcessingException {
        thrown.expect(BusException.class);
        ApiUser apiUser = apiUserService.findByCode("11111");
        apiUser.setName("测试111111");
        apiUser.setCode("22222");
        apiUser.setSecret(RandomUtils.randomFor6());
        apiUserService.update(apiUser);
    }
}
