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
package com.zy.mylib.security.dto.mapper

import com.zy.mylib.security.dto.AddUserResponse
import com.zy.mylib.security.dto.UpdateUserResponse
import com.zy.mylib.security.dto.UserListResponse
import com.zy.mylib.security.dto.UserSaveRequest
import com.zy.mylib.security.entity.User
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface UserConvert {
  fun fromUserSaveRequest(req: UserSaveRequest): User
  fun toUpdateUserResponse(use: User): UpdateUserResponse
  fun toAddUserResponse(user: User): AddUserResponse
  fun toUserListResponse(user: User): UserListResponse
}