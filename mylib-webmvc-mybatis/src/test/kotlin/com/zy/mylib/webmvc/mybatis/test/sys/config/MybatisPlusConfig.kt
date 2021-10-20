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
package com.zy.mylib.webmvc.mybatis.test.sys.config

import org.springframework.transaction.annotation.EnableTransactionManagement
import org.mybatis.spring.annotation.MapperScan
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor
import com.baomidou.mybatisplus.annotation.DbType
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@EnableTransactionManagement
@Configuration
@MapperScan("com.zy.mylib.webmvc.mybatis.test.sys.mapper")
open class MybatisPlusConfig {
    @Bean
    open fun paginationInterceptor(): MybatisPlusInterceptor {
        val mybatisPlusInterceptor = MybatisPlusInterceptor()
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        // paginationInterceptor.setLimit(500);
        // 开启 count 的 join 优化,只针对部分 left join
        val paginationInnerInterceptor = PaginationInnerInterceptor(DbType.H2)
        mybatisPlusInterceptor.addInnerInterceptor(paginationInnerInterceptor)
        return mybatisPlusInterceptor
    }
}