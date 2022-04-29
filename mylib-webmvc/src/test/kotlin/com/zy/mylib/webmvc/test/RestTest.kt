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
package com.zy.mylib.webmvc.test

import com.zy.mylib.webmvc.test.rest.TestRest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@SpringJUnitWebConfig
@ContextConfiguration(classes = [TestRest::class])
@EnableWebMvc
class RestTest {
  private var mockMvc: MockMvc? = null
  @BeforeEach
  fun setup(wac: WebApplicationContext?) {
    mockMvc = MockMvcBuilders.webAppContextSetup(wac).build()
  }

  @Test
  @Throws(Exception::class)
  fun test1GetTest() {
    mockMvc?.perform(
      MockMvcRequestBuilders.get("/test/world!")
        .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
    )
      ?.andExpect(MockMvcResultMatchers.status().isOk)
      ?.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
      ?.andExpect(MockMvcResultMatchers.jsonPath("$.hello").value("world!"))
  }
}
