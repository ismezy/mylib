package com.zy.mylib.webmvc.mybatis.test;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zy.mylib.webmvc.mybatis.test.sys.service.IApiUserService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.inject.Inject;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybatisTest.class)
@EnableAutoConfiguration
@AutoConfigureMockMvc
@ComponentScan("com.zy.mylib.webmvc.mybatis.test.**")
@MapperScan("com.zy.mylib.webmvc.mybatis.test.**.mapper*")
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@EnableTransactionManagement
public class MybatisTest {
    @Inject
    private IApiUserService apiUserService;
    @Inject
    ObjectMapper objectMapper;

    @Test
    public void test() throws JsonProcessingException {
        System.out.println(objectMapper.writeValueAsString(apiUserService.list()));
        System.out.println("-------------------------");
    }

}
