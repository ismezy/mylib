package com.zy.mylib.mapstruct

interface BaseMapper<PO, DTO> {
  /**
   * 实体转换为dto
   */
  fun toDto(po: PO): DTO

  /**
   * dto转为实体
   */
  fun toPo(dto: DTO): PO
}
