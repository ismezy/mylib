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
package com.zy.mylib.codegen

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import org.apache.velocity.Template
import org.apache.velocity.VelocityContext
import org.apache.velocity.app.VelocityEngine
import java.io.File
import java.io.FileWriter
import java.util.*

class CodeGen {
  companion object {
    /**
     * 开始构建
     */
    fun run(vararg model: String, srcPath: String? = null) {
      // 初始化模板引擎
      val properties = Properties()
      ClassLoader.getSystemResourceAsStream("velocity.properties").use { properties.load(it) }
      val velocityEngine = VelocityEngine(properties)
      velocityEngine.init()
      // 加载配置
      val mapper = ObjectMapper(YAMLFactory())
      ClassLoader.getSystemResourceAsStream("global.yml").use {
        val globalConfig = mapper.readValue(it, CodeGenConfig::class.java)
        // srcPath参数为主
        if(srcPath != null) {
          globalConfig.srcPath = srcPath
        }
        val entities = model.map { entity ->
          ClassLoader.getSystemResourceAsStream("model/$entity.yml").use { stream ->
            mapper.readValue(stream, EntityConfig::class.java)
          }
        }
        // 生成代码
        GenImpl(velocityEngine, globalConfig, entities).gen()
      }
    }
  }
}

class GenImpl(
    private var engine: VelocityEngine,
    private var config: CodeGenConfig,
    private var entities: List<EntityConfig>) {

  fun gen() {
    entities.forEach {
      val context = VelocityContext(mapOf(
          "entity" to it,
          "config" to config,
          "package" to if(it.pkg != null) it.pkg else config.pkg
      ))
      // 生成实体
      genEntity(context, it)
      genDao(context, it)
      genManager(context, it)
      genDto(context, it);
      if (config.genRest) {
        genRest(context, it)
      }
    }
  }

  private fun genDto(context: VelocityContext, entity: EntityConfig) {
    val dtoTmpl = engine.getTemplate("templates/${config.lang}/${entity.type}/dto.vm")
    val mapperTmpl = engine.getTemplate("templates/${config.lang}/${entity.type}/dtoMapper.vm")
    // 创建目录
    val dtoPath = File(config.srcPath, config.pkg.replace('.', '/') + "/dto")
    val mapperPath = File(config.srcPath, config.pkg.replace('.', '/') + "/dto/mapper")
    mapperPath.mkdirs()
    context.put("dtoSuffix", "Request")
    write(File(dtoPath, "${entity.name}Request.${config.fileExtName}"), dtoTmpl, context)
    context.put("dtoSuffix", "Response")
    write(File(dtoPath, "${entity.name}Response.${config.fileExtName}"), dtoTmpl, context)
    write(File(mapperPath, "${entity.name}Convert.${config.fileExtName}"), mapperTmpl, context)
  }

  private fun genRest(context: VelocityContext, entity: EntityConfig) {
    val template = engine.getTemplate("templates/${config.lang}/${entity.type}/rest.vm")
    // 创建目录
    val targetPath = File(config.srcPath, config.pkg.replace('.', '/') + "/rest")
    targetPath.mkdirs()
    write(File(targetPath, "${entity.name}Rest.${config.fileExtName}"), template, context)
  }

  private fun write(target: File, template: Template, context: VelocityContext) {
    // 如果文件存在跳过
    if (target.exists()) {
      return
    }
    FileWriter(target).use {
      template.merge(context, it)
    }
    println("${target.path}已创建")
  }

  private fun genManager(context: VelocityContext, entity: EntityConfig) {
    val template = engine.getTemplate("templates/${config.lang}/${entity.type}/manager.vm")
    val implTemplate = engine.getTemplate("templates/${config.lang}/${entity.type}/managerImpl.vm")
    // 创建目录
    val targetPath = File(config.srcPath, config.pkg.replace('.', '/') + "/manager")
    val implTargetPath = File(config.srcPath, config.pkg.replace('.', '/') + "/manager/impl")
    implTargetPath.mkdirs()
    // 生成manager
    write(File(targetPath, "${entity.name}Manager.${config.fileExtName}"), template, context)
    // 生成managerImpl
    write(File(implTargetPath, "${entity.name}ManagerImpl.${config.fileExtName}"), implTemplate, context)
  }

  private fun genDao(context: VelocityContext, entity: EntityConfig) {
    val template = engine.getTemplate("templates/${config.lang}/${entity.type}/dao.vm")
    // 创建目录
    val targetPath = File(config.srcPath, config.pkg.replace('.', '/') + "/dao")
    targetPath.mkdirs()
    write(File(targetPath, "${entity.name}Dao.${config.fileExtName}"), template, context)
    if(entity.type == "mybatis") {
      // 创建mapper.xml
      val mapperXmlRoot = File(config.srcPath, "../resources/mapper");
      val mapperTemplate = engine.getTemplate("templates/${config.lang}/${entity.type}/mybatisMapper.vm")
      mapperXmlRoot.mkdirs()
      write(File(mapperXmlRoot, "${entity.name}Mapper.xml"), mapperTemplate, context);
    }
  }

  private fun genEntity(context: VelocityContext, entity: EntityConfig) {
    val template = engine.getTemplate("templates/${config.lang}/${entity.type}/entity.vm")
    // 创建目录
    val targetPath = File(config.srcPath, config.pkg.replace('.', '/') + "/entity")
    targetPath.mkdirs()
    write(File(targetPath, "${entity.name}.${config.fileExtName}"), template, context)
  }

}
