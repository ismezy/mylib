/*
 * Copyright © 2020 ismezy (ismezy@qq.com)
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
package com.zy.mylib.webmvc.data.jpa.config

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.Version
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.zy.mylib.data.jpa.DefaultHibernateModule
import com.zy.mylib.data.jpa.ModulesObjectMapper
import org.springframework.data.domain.Page
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import java.io.IOException

/**
 * @author ASUS
 */
class BaseJpaWebConfig : DelegatingWebMvcConfiguration() {
  override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
    println("******************************Configuring swagger resource handler")
    registry.addResourceHandler("swagger-ui.html")
      .addResourceLocations("classpath:/META-INF/resources/")
    registry.addResourceHandler("/webjars/**")
      .addResourceLocations("classpath:/META-INF/resources/webjars/")
    super.addResourceHandlers(registry)
  }

  override fun addCorsMappings(registry: CorsRegistry) {
    println("*** addCorsMappings called")
    registry.addMapping("/v2/api-docs").allowedOrigins("*")
    super.addCorsMappings(registry)
  }

  public override fun extendMessageConverters(converters: List<HttpMessageConverter<*>?>) {
    val mapper = ModulesObjectMapper()
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
    mapper.configure(SerializationFeature.WRAP_EXCEPTIONS, true)
    mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false)
    //        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
//        mapper.configure(SerializationFeature.EAGER_SERIALIZER_FETCH,false);
//        mapper.configure(Des)
//        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
//                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
//        mapper.setFilterProvider()
    val maperModules: MutableList<Module> = ArrayList()
    val hModule = DefaultHibernateModule()
    maperModules.add(hModule)
    // 添加page jsonview支持
    val pageModule = SimpleModule("jackson-page-with-jsonview", Version.unknownVersion())
    pageModule.addSerializer(Page::class.java, PageSerializer())
    maperModules.add(pageModule)
    mapper.modules = maperModules

    for (i in converters.indices) {
      val c = converters[i]
      if (c is MappingJackson2HttpMessageConverter) {
        c.objectMapper = mapper
      }
    }

    super.extendMessageConverters(converters.filter { it !is MappingJackson2XmlHttpMessageConverter })
  }

  internal inner class PageSerializer : StdSerializer<Page<*>>(
    Page::class.java
  ) {
    @Throws(IOException::class)
    override fun serialize(value: Page<*>, gen: JsonGenerator, provider: SerializerProvider) {
      gen.writeStartObject()
      gen.writeNumberField("number", value.number)
      gen.writeNumberField("numberOfElements", value.numberOfElements)
      gen.writeNumberField("totalElements", value.totalElements)
      gen.writeNumberField("totalPages", value.totalPages)
      gen.writeNumberField("size", value.size)
      gen.writeFieldName("content")
      provider.defaultSerializeValue(value.content, gen)
      gen.writeEndObject()
    }
  }
}
