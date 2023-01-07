package com.zy.mylib.app.admin.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
open class WebMvcConfig : WebMvcConfigurer{
  @Override
  override fun addCorsMappings(registry: CorsRegistry) {
    super.addCorsMappings(registry)
    registry.addMapping("/**")
        .allowCredentials(true)
        .allowedOriginPatterns("*")
        .allowedMethods("GET","POST","PUT","DELETE")
        .allowedHeaders("*")
        .exposedHeaders("*")
  }

}