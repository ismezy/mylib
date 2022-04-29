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
import com.zy.mylib.excel.ExcelUtils
import org.junit.jupiter.api.Test
import java.io.File
import java.io.IOException
import java.util.*

/**
 * 测试excel工具类
 */
class TestExcel {
  private val firstName = arrayOf("赵", "钱", "孙", "李", "周", "武", "郑", "王")
  private val middleName = arrayOf("欣", "爱", "小", "百", "千", "万")
  private val lastName = arrayOf("桐", "宜", "国", "勇", "刚", "民", "庆", "玲", "凤", "龙")
  var random = Random(Date().time)
  @Test
  @Throws(IOException::class)
  fun genExcel() {
    val inputStream = ClassLoader.getSystemResourceAsStream("sample.xlsx")
    val data: MutableMap<String?, Any?> = HashMap(1)
    val students: MutableList<Map<String, Any>> = ArrayList(10)
    for (i in 0..9) {
      val s: MutableMap<String, Any> = HashMap(5)
      s["name"] = randName()
      s["age"] = 13 + random.nextInt(2)
      s["chs"] = 70 + random.nextInt(51)
      s["eng"] = 70 + random.nextInt(51)
      s["math"] = 70 + random.nextInt(51)
      students.add(s)
    }
    data["students"] = students
    ExcelUtils.genExcel(data, inputStream, File("/test.xlsx"))
  }

  private fun randName(): String {
    var name = firstName[random.nextInt(firstName.size)]
    if (random.nextBoolean()) {
      name += middleName[random.nextInt(middleName.size)]
    }
    return name + lastName[random.nextInt(lastName.size)]
  }
}
