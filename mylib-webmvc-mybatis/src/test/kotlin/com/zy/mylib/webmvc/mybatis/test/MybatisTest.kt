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
package com.zy.mylib.webmvc.mybatis.test

import org.springframework.transaction.annotation.EnableTransactionManagement
import com.zy.mylib.webmvc.mybatis.test.sys.entity.ApiUser
import com.zy.mylib.webmvc.mybatis.test.sys.service.IApiUserService
import javax.inject.Inject
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ActiveProfiles
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.MethodOrderer.MethodName
import kotlin.Throws
import com.zy.mylib.base.exception.BusException
import com.zy.mylib.base.model.*
import com.zy.mylib.utils.RandomUtils
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@SpringBootTest(classes = [MybatisTest::class])
@EnableAutoConfiguration
@AutoConfigureMockMvc
@ComponentScan("com.zy.mylib.webmvc.mybatis.test.**")
@ActiveProfiles("test")
@TestMethodOrder(MethodName::class)
@EnableTransactionManagement
class MybatisTest {
  @Inject
  private lateinit var apiUserService: IApiUserService

  @Inject
  lateinit var objectMapper: ObjectMapper

  @Test
  @Throws(JsonProcessingException::class)
  fun t01AddTest() {
    var apiUser: ApiUser = ApiUser()
    apiUser.code = "11111"
    apiUser.name = "测试1"
    apiUser.system = "t3"
    apiUser.secret = RandomUtils.randomFor6()
    apiUser = apiUserService.add(apiUser)!!
    println("-------------------------")
    println(objectMapper.writeValueAsString(apiUser))
    println("-------------------------")
    apiUser!!.id = null
    apiUser.code = "22222"
    apiUser.name = "测试2"
    apiUser.system = "t3"
    apiUser = apiUserService.add(apiUser)!!
    println("-------------------------")
    println(objectMapper.writeValueAsString(apiUser))
    println("-------------------------")
    println(objectMapper.writeValueAsString(apiUserService.all()))
    println("-------------------------")
  }

  /**
   * 已存在添加测试
   */
  @Test
  @Throws(JsonProcessingException::class)
  fun t02ExistAdd() {
    Assertions.assertThrows(BusException::class.java) {
      var apiUser: ApiUser = ApiUser()
      apiUser.code = "11111"
      apiUser.name = "测试1"
      apiUser.secret = RandomUtils.randomFor6()
      apiUser = apiUserService.add(apiUser)!!
      println(objectMapper!!.writeValueAsString(apiUser))
    }
  }

  @Test
  @Throws(JsonProcessingException::class)
  fun t03Update() {
    val apiUser = apiUserService!!.findByCode("11111")!!
    apiUser.name = "测试111111"
    apiUser.secret = RandomUtils.randomFor6()
    apiUserService.update(apiUser)
    println(objectMapper!!.writeValueAsString(apiUser))
  }

  @Test
  @Throws(JsonProcessingException::class)
  fun t04Update() {
    Assertions.assertThrows(BusException::class.java) {
      val apiUser = apiUserService!!.findByCode("11111")!!
      apiUser.name = "测试111111"
      apiUser.code = "22222"
      apiUser.secret = RandomUtils.randomFor6()
      apiUserService.update(apiUser)
    }
  }

  @Test
  @Throws(JsonProcessingException::class)
  fun t05PageTest() {
    val conditions1: MutableList<Condition> = mutableListOf(
        Condition(property = "system", value = "t1", logicalOperator = LogicalOperators.or),
        Condition(property = "system", value = "t2", logicalOperator = LogicalOperators.or))
    val conditions: MutableList<Condition> = mutableListOf(
        Condition(property = "code", value = "test", comparisonOperator = ComparisonOperators.like),
        Condition(conditions = conditions1))
    val response = apiUserService!!.pageQuery(
        PageRequest(size = 10L, sortRequests = listOf(
                SortRequest(property = "code", direction = SortRequest.SortDirection.Descend)
        )),  conditions)
    println(objectMapper!!.writeValueAsString(response))
  }
}