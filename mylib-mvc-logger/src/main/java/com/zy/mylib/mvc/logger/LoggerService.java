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
package com.zy.mylib.mvc.logger;

import com.zy.mylib.security.LoginUser;

import java.util.Date;

/**
 * @author 扬
 * @date 2017/5/12
 */
public interface LoggerService<UT extends LoginUser> {
    /**
     * 添加日志
     *
     * @param cloudUser
     * @param logType
     * @param content
     * @param user
     * @param ip
     * @param nowTime
     */
    void addLog(String cloudUser, String targetId, String logType, String content, UT user, String ip, Date nowTime);
}
