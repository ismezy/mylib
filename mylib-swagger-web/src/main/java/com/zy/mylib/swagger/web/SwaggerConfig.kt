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
package com.zy.mylib.swagger.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket

/**
 * @author zhouyang
 */
@Configuration
open class SwaggerConfig : WebMvcConfigurer {
  @Bean
  open fun api(): Docket {
    return Docket(DocumentationType.SWAGGER_2)
      .select()
      .apis(RequestHandlerSelectors.any())
      .paths(PathSelectors.any())
      .build()
      .pathMapping("/")
      .apiInfo(apiInfo())
      .securitySchemes(listOf<SecurityScheme>(apiKey()))
      .securityContexts(listOf(securityContext()))
  }

  private fun apiKey(): ApiKey {
    return ApiKey("token", "token", "header")
  }

  private fun securityContext(): SecurityContext {
    return SecurityContext.builder()
      .securityReferences(defaultAuth())
      .forPaths(PathSelectors.regex("/.*"))
      .build()
  }

  private fun defaultAuth(): List<SecurityReference> {
    val authorizationScope = AuthorizationScope("global", "accessEverything")
    val authorizationScopes = arrayOfNulls<AuthorizationScope>(1)
    authorizationScopes[0] = authorizationScope
    return listOf(
      SecurityReference("token", authorizationScopes)
    )
  }

  override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
    registry.addResourceHandler("/swagger-ui/**")
      .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
  }

  /**
   * @return an instance of ApiInfo
   */
  private fun apiInfo(): ApiInfo {
    val apiInfoBuilder = ApiInfoBuilder()
    return apiInfoBuilder.build()
  }
}
