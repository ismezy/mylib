package com.zy.mylib.file.doc

import org.junit.jupiter.api.Test
import java.io.File
import java.util.*

class DocUtilTest {
  @Test
  fun genDoc() {
    val map = mutableMapOf<String, Any>()
    map.put("swjgmc", "成都")
    map.put("wszg", "2023年02月23")
    map.put("users", listOf(
        mapOf("name"  to "zy", "sex" to "男", "age" to 45),
        mapOf("name"  to "wbs", "sex" to "男", "age" to 35),
        mapOf("name"  to "czx", "sex" to "男", "age" to 30),
    ))
    ClassLoader.getSystemResourceAsStream("test.docx").use {
      DocUtils.genDoc(map, it, File("e:/temp/${Date().time}.docx"))
    }
  }
}