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
package com.zy.mylib.webmvc.mybatis.test.sys.service.impl

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.zy.mylib.webmvc.mybatis.test.sys.entity.ApiUser
import com.zy.mylib.mybatis.manager.MyBatisBaseManagerImpl
import com.zy.mylib.webmvc.mybatis.test.sys.service.IApiUserService
import com.zy.mylib.webmvc.mybatis.test.sys.mapper.ApiUserMapper
import javax.inject.Inject
import java.util.HashMap
import javax.inject.Named

/**
 *
 *
 * 服务实现类
 *
 *
 * @author zhouyang
 * @since 2020-07-25
 */
@Named
open class ApiUserServiceImpl : MyBatisBaseManagerImpl<ApiUserMapper, ApiUser>(), IApiUserService {


    override fun findExist(entity: ApiUser): ApiUser? {
        val map: MutableMap<String, Any?> = HashMap(0)
        map["code"] = entity.code
        val list = mapper!!.selectByMap(map)
        return if (list.size > 0) list[0] else null
    }

    override fun findByCode(code: String): ApiUser? {
        return mapper!!.findByCode(code)
    }
}
