package com.zy.mylib.system.manager.impl;

import com.zy.mylib.system.dao.FileDescriptionDao;
import com.zy.mylib.system.entity.FileDescription;
import com.zy.mylib.system.manager.FileDescriptionManager;
import com.zy.mylib.system.manager.SysConfigItemManager;
import com.zy.mylib.base.exception.BusException;
import com.zy.mylib.data.jpa.BaseJpaManager;
import com.zy.mylib.data.jpa.JpaRepository;
import com.zy.mylib.data.jpa.PageUtils;
import com.zy.mylib.utils.DateUtils;
import com.zy.mylib.utils.FileUtils;
import com.zy.mylib.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件描述管理实现类
 */
@Service
public class FileDescriptionManagerImpl extends BaseJpaManager<FileDescription, String> implements FileDescriptionManager {
    private static Logger logger = LoggerFactory.getLogger(FileDescriptionManagerImpl.class);
    @Inject
    private FileDescriptionDao dao;
    @Inject
    private SysConfigItemManager configManager;
    @Value("${project.path.temp}")
    String tempPath;
    @Value("${project.path.root")
    String rootPath;
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public FileDescription addFile(String objectType, String objectId, String fileType, FileDescription file, String targetPath, Integer sortNumber, String userId) {
        FileDescription fileDescription = dao.findByObjectTypeAndObjectIdAndFileTypeAndSortNumberAndDeleted(objectType, objectId,
                fileType, sortNumber, false);
        if (fileDescription != null && fileDescription.getId() != null) {
            markRemove(fileDescription.getId());
        }
        FileDescription fd = new FileDescription();
        fd.setObjectId(objectId);
        fd.setObjectType(objectType);
        fd.setFileExtName(FileUtils.getFileExtName(file.getFilePath()));
        String currentTime = DateUtils.getToday("yyyyMMddHHmmss");
        if(StringUtils.isNotBlank(file.getCustomFileName())) {
            fd.setFilePath(targetPath + "/" + sortNumber + "-" + FileUtils.getFilename(file.getCustomFileName()) + "." + fd.getFileExtName());
        } else {
            fd.setFilePath(targetPath + "/" + fileType + "-" + sortNumber + "-" + currentTime + "." + fd.getFileExtName());
        }
        fd.setCustomFileName(file.getCustomFileName());
        fd.setFileType(fileType);
        fd.setDeleted(false);
        fd.setSortNumber(sortNumber);
        copyFile("", file.getFilePath(), fd.getFilePath());
        return save(fd);
    }

    @Override
    public void addFiles(String objectType, String objectId, String targetPath, String userId, Map<String, List<FileDescription>> files) {
        files.forEach((type, fds) -> {
            final int[] i = {0};
            // 找到当前已附件
            List<FileDescription> exisit = dao.findByObjectTypeAndObjectIdAndFileTypeAndDeletedOrderBySortNumber(objectType, objectId, type, false);
            // 新增或修改附件
            fds.forEach(file -> {
                int index = i[0]++;
                if(StringUtils.isNotBlank(file.getId())){
                    return;
                }
                addFile(objectType, objectId, type, file,  targetPath, index, userId);
            });
            // 移除多余附件
            for(int j = fds.size(); j < exisit.size(); j++) {
                markRemove(exisit.get(j).getId());
            }
        });
    }

    /**
     * 复制文件
     */
    private void copyFile(String cloudUser, String tempPath, String targetPath) {
        String tempRoot = tempPath;
        File oldFile = new File(tempRoot, tempPath);
        File targetFile = new File(rootPath, targetPath);
        targetFile.getParentFile().mkdirs();
        try {
            FileUtils.copyAndCreatePath(oldFile, targetFile);
        } catch (IOException e) {
            logger.error("复制附件异常", e);
            throw BusException.builder().message("无法保存附件").httpStatus(501).build();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public FileDescription markRemove(String id) {
        FileDescription fd = findById(id).get();
        fd.setDeleted(true);
        return save(fd);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FileDescription> find(String objectType, String objectId, String userId) {
        return dao.findByObjectTypeAndObjectIdAndDeletedOrderByFileTypeAscSortNumberAsc(objectType, objectId, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, List<FileDescription>> findMap(String objectType, String objectId, String userId) {
        List<FileDescription> list = find(objectType, objectId, userId);
        Map<String, List<FileDescription>> result = new HashMap<>();
        for (FileDescription fd : list) {
            if (!result.containsKey(fd.getFileType())) {
                result.put(fd.getFileType(), new ArrayList<>());
            }
            result.get(fd.getFileType()).add(fd);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<FileDescription> findPage(Pageable pageable, FileDescription filter, String userId) {
        Map<String, PageUtils.Operate> operateMap = new HashMap<>(10);
        Specification<FileDescription> spec = PageUtils.getSpecification(filter, operateMap);
        return findPage(pageable, spec);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void addPhotos(String objectType, String objectId, Map<String, List<FileDescription>> photos, String targetPath, String userId) {
        if (photos != null) {
            for (Map.Entry<String, List<FileDescription>> m : photos.entrySet()) {
                String fileType = m.getKey();
                List<FileDescription> files = m.getValue();
                for (FileDescription f : files) {
                    if (f == null) {
                        throw newBusException("common.files.e011002");
                    }
                    if (f.getId() != null) {
                        continue;
                    }
                    addFile(objectType, objectId, fileType, f, targetPath, f.getSortNumber(), userId);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String missPhotos(List<String> fullPhotos, Map<String, List<FileDescription>> photos) {
        StringBuffer missPhotos = new StringBuffer();
        for (String fileType : fullPhotos) {
            if (photos.containsKey(fileType) && photos.get(fileType).size() > 0) {
                continue;
            }
            if (missPhotos.length() == 0) {
                missPhotos.append(fileType);
            }
            missPhotos.append("," + fileType);
        }
        return missPhotos.toString();
    }

    @Override
    public Map<String, List<FileDescription>> findByObjectID(String objectID, String userId) {
        List<FileDescription> files = this.dao.findByObjectIdAndDeletedOrderByFileTypeAscSortNumberAsc(objectID, false);
        Map<String, List<FileDescription>> result = new HashMap<>(10);
        List<FileDescription> tmpList = new ArrayList<>();
        String curType =  "";
        for (FileDescription file : files) {
            if(!curType.equals(file.getFileType())) {
                if(!curType.equals("")) {
                    result.put(curType, tmpList);
                    tmpList = new ArrayList<>(10);
                }
                curType = file.getFileType();
            }
            tmpList.add(file);
        }
        if(!curType.equals("")) {
            result.put(curType, tmpList);
        }
        return result;
    }

    @Override
    protected JpaRepository<FileDescription, String> getRepository() {
        return this.dao;
    }
}
