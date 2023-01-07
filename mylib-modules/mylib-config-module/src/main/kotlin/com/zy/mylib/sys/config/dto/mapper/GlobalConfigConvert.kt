package com.zy.mylib.sys.config.dto.mapper

import com.zy.mylib.sys.config.entity.GlobalConfig
import com.zy.mylib.sys.config.dto.AddGlobalConfigRequest
import com.zy.mylib.sys.config.dto.UpdateGlobalConfigRequest
import com.zy.mylib.sys.config.dto.AddGlobalConfigResponse
import com.zy.mylib.sys.config.dto.UpdateGlobalConfigResponse
import com.zy.mylib.sys.config.dto.QueryGlobalConfigResponse
import com.zy.mylib.sys.config.dto.GetGlobalConfigResponse
import org.mapstruct.Mapper

/**
 * 全局配置 DTO Response  mapper
 * @author 代码生成器
 */
@Mapper(componentModel = "spring")
interface GlobalConfigConvert {
  fun fromAddGlobalConfigRequest(req: AddGlobalConfigRequest): GlobalConfig
  fun fromUpdateGlobalConfigRequest(req: UpdateGlobalConfigRequest): GlobalConfig
  fun toAddGlobalConfigResponse(entity: GlobalConfig): AddGlobalConfigResponse
  fun toUpdateGlobalConfigResponse(entity: GlobalConfig): UpdateGlobalConfigResponse
  fun toQueryGlobalConfigResponse(entity: GlobalConfig): QueryGlobalConfigResponse
  fun toGetGlobalConfigResponse(entity: GlobalConfig): GetGlobalConfigResponse
}
