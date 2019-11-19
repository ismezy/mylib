package com.zy.mylib.system.dao;


import com.zy.mylib.system.entity.FileDescription;
import com.zy.mylib.data.jpa.JpaRepository;

import java.util.List;

/**
 * 文件描述持久接口
 *
 * @author ASUS
 */
public interface FileDescriptionDao extends JpaRepository<FileDescription, String> {
  /**
   * 查询文件
   *
   * @param objectType
   * @param objectId
   * @param fileType
   * @param sortNumber
   * @param deleted
   * @return
   */
  FileDescription findByObjectTypeAndObjectIdAndFileTypeAndSortNumberAndDeleted(String objectType, String objectId, String fileType, Integer sortNumber, boolean deleted);

  /**
   * 按业务类型和业务ID查找附件，并按附件类型和顺序号排序
   *
   * @param objectType
   * @param objectId
   * @param deleted
   * @return
   */
  List<FileDescription> findByObjectTypeAndObjectIdAndDeletedOrderByFileTypeAscSortNumberAsc(String objectType, String objectId, boolean deleted);

  /**
   * 查找业务对象附件，不包含删除附件
   *
   * @param objectID
   * @param deleted
   * @return
   */
  List<FileDescription> findByObjectIdAndDeletedOrderByFileTypeAscSortNumberAsc(String objectID, boolean deleted);

  /**
   * 根据对象和id获取附件数量
   *
   * @param objectType
   * @param objectId
   * @return
   */
  long countByObjectTypeAndObjectId(String objectType, String objectId);

  /**
   * 根据对象、ID、文件类型查找
   *
   * @param objectType
   * @param objectId
   * @param type
   * @param deleted
   * @return
   */
  List<FileDescription> findByObjectTypeAndObjectIdAndFileTypeAndDeletedOrderBySortNumber(String objectType, String objectId, String type, boolean deleted);
}
