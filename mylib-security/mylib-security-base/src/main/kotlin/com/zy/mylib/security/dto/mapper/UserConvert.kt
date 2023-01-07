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