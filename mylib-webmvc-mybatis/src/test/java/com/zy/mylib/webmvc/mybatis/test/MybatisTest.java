/*
 * Copyright © ${project.inceptionYear} ismezy (ismezy@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zy.mylib.webmvc.mybatis.test;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zy.mylib.base.exception.BusException;
import com.zy.mylib.base.model.*;
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
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybatisTest.class)
@EnableAutoConfiguration
@AutoConfigureMockMvc
@ComponentScan("com.zy.mylib.webmvc.mybatis.test.**")
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
        apiUser.setSystem("t3");
        apiUser.setSecret(RandomUtils.randomFor6());
        apiUser = apiUserService.add(apiUser);
        System.out.println("-------------------------");
        System.out.println(objectMapper.writeValueAsString(apiUser));
        System.out.println("-------------------------");
        apiUser.setId(null);
        apiUser.setCode("22222");
        apiUser.setName("测试2");
        apiUser.setSystem("t3");
        apiUser = apiUserService.add(apiUser);
        System.out.println("-------------------------");
        System.out.println(objectMapper.writeValueAsString(apiUser));
        System.out.println("-------------------------");
        System.out.println(objectMapper.writeValueAsString(apiUserService.all()));
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

    @Test
    public void t05PageTest() throws JsonProcessingException {
        t01AddTest();
        List<BaseCondition> conditions = new ArrayList<>();
        conditions.add(Condition.builder().property("code").value("test").comparisonOperator(ComparisonOperators.like).build());
        List<BaseCondition> conditions1 = new ArrayList<>();
        conditions1.add(Condition.builder().property("system").value("t1").logicalOperator(LogicalOperators.or).build());
        conditions1.add(Condition.builder().property("system").value("t2").logicalOperator(LogicalOperators.or).build());
        conditions.add(ConditionGroup.builder().conditions(conditions1).build());
        ConditionGroup group = ConditionGroup.builder().conditions(conditions).build();
        PageResponse<ApiUser> response = apiUserService.pageQuery(PageRequest.builder().page(1L).size(2L).build(),
            group);
        System.out.println(objectMapper.writeValueAsString(response));
    }
}
