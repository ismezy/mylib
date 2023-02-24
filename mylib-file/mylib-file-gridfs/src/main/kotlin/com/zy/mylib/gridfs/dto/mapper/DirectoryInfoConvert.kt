package com.zy.mylib.gridfs.dto.mapper

import com.zy.mylib.gridfs.entity.DirectoryInfo
import com.zy.mylib.gridfs.dto.AddDirectoryInfoRequest
import com.zy.mylib.gridfs.dto.UpdateDirectoryInfoRequest
import com.zy.mylib.gridfs.dto.AddDirectoryInfoResponse
import com.zy.mylib.gridfs.dto.UpdateDirectoryInfoResponse
import com.zy.mylib.gridfs.dto.QueryDirectoryInfoResponse
import com.zy.mylib.gridfs.dto.GetDirectoryInfoResponse
import org.mapstruct.Mapper

/**
 * 目录信息 DTO Response  mapper
 * @author 代码生成器
 */
@Mapper(componentModel = "spring")
interface DirectoryInfoConvert {
  fun fromAddDirectoryInfoRequest(req: AddDirectoryInfoRequest): DirectoryInfo
  fun fromUpdateDirectoryInfoRequest(req: UpdateDirectoryInfoRequest): DirectoryInfo
  fun toAddDirectoryInfoResponse(entity: DirectoryInfo): AddDirectoryInfoResponse
  fun toUpdateDirectoryInfoResponse(entity: DirectoryInfo): UpdateDirectoryInfoResponse
  fun toQueryDirectoryInfoResponse(entity: DirectoryInfo): QueryDirectoryInfoResponse
  fun toGetDirectoryInfoResponse(entity: DirectoryInfo): GetDirectoryInfoResponse
}
