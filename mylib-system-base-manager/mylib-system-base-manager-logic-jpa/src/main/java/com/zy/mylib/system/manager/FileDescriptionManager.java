package com.zy.mylib.system.manager;

import com.zy.mylib.system.entity.FileDescription;
import com.zy.mylib.data.jpa.JpaManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * 文件描述管理接口
 * @author ASUS
 */
public interface FileDescriptionManager extends JpaManager<FileDescription, String> {
    /**
     * 添加文件描述
     *
     * @return
     */
    FileDescription addFile(String objectType, String objectId, String fileType, FileDescription tempPath, String targetPath, Integer sortNumber, String userId);

    /**
     * 批量添加
     * @param objectType
     * @param objectId
     * @return
     */
    void addFiles(String objectType, String objectId, String targetPath, String userId, Map<String, List<FileDescription>> files);


    /**
     * 标记删除
     *
     * @return
     */
    FileDescription markRemove(String id);

    /**
     * 删除某业务对象所有数据
     * @param objectType
     * @param objectId
    void removeFile(String objectType, String objectId, User curUser);

    /**
     * 删除业务对象某类型所有文件
     * @param objectType
     * @param objectId
     * @param fileType
     * @param curUser
    void removeFile(String objectType, String objectId, String fileType, User curUser);

    /**
     * 删除业务对象某类型的一个文件
     * @param objectType
     * @param objectId
     * @param sortNumber
     * @param curUser
    //    void removeFile(String objectType, String objectId, Integer sortNumber, User curUser);
     */
    /**
     * 查找业务对象所有文件
     *
     * @param objectType
     * @param objectId
     * @return
     */
    List<FileDescription> find(String objectType, String objectId, String userId);

    /**
     * 查找业务对象所有文件，返回附件类型MAP
     *
     * @param objectType
     * @param objectId
     * @return
     */
    Map<String, List<FileDescription>> findMap(String objectType, String objectId, String userId);

    /**
     * 分页查询
     *
     * @param pageable
     * @param filter
     * @return
     */
    Page<FileDescription> findPage(Pageable pageable, FileDescription filter, String userId);

    /**
     * 添加对象附件图片
     *
     * @param objectType 对象类型
     * @param objectId   对象Id
     * @param photos     图片列表
     * @param targetPath 图片保存路径
     */
    void addPhotos(String objectType, String objectId, Map<String, List<FileDescription>> photos, String targetPath, String userId);

    /**
     * 缺图片
     * @param fullPhotos 完整的图片列表
     * @param photos 对象图片列表
     * @return 缺图片类型字符串，用“，”隔开
     */
    String missPhotos(List<String> fullPhotos, Map<String, List<FileDescription>> photos);

    /**
     * 根据业务对象获取文件
     * @param objectID
     * @return
             */
    Map<String, List<FileDescription>> findByObjectID(String objectID, String userId);
}
