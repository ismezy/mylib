package com.zy.mylib.system.rest;

import com.zy.mylib.system.entity.FileDescription;
import com.zy.mylib.system.manager.FileDescriptionManager;
import com.zy.mylib.system.manager.SysConfigItemManager;
import com.google.common.io.Files;
import com.zy.mylib.base.exception.BusException;
import com.zy.mylib.utils.FileUtils;
import com.zy.mylib.webmvc.base.BaseRest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * @author ASUS
 */
@RestController
@RequestMapping("/files")
public class FileRest extends BaseRest {
    @Value("${project.path.temp}")
    String tempPath;
    @Value("${project.path.root")
    String rootPath;
    @Inject
    private SysConfigItemManager config;
    @Inject
    private FileDescriptionManager fileManager;

    @PostMapping("/file")
    public Map<String, String> uploadBase64(@RequestParam(value = "files[]", required = true) MultipartFile upload) {
        Map<String, String> ret = new HashMap(1);
        String ext = FileUtils.getFileExtName(upload.getOriginalFilename());
        File target = new File(tempPath, UUID.randomUUID().toString() + "." + ext);
        try {
            FileUtils.copyAndCreatePath(upload.getInputStream(), target);
        } catch (IOException e) {
            throw BusException.builder().message("上传文件失败").build();
        }
        ret.put("fileName", target.getName());
        return ret;
    }

    @PostMapping("/base64")
    public Map<String, String> uploadBase64(@RequestBody String base64) {
        Map<String, String> ret = new HashMap(1);
        ret.put("fileName", saveBase64(base64));
        return ret;
    }

    @GetMapping("/object/{objectID}")
    public Map<String, List<FileDescription>> findFiles(@PathVariable("objectID") String objectID) {
        return this.fileManager.findByObjectID(objectID, "");
    }

    @GetMapping("/base64/{id}")
    public Map<String, String> downloadBase64(@PathVariable("id") String id) {
        Optional<FileDescription> opt = this.fileManager.findById(id);
        if (!opt.isPresent()) {
            throw BusException.builder().message("附件不存在").httpStatus(404).build();
        }
        Map<String, String> ret = new HashMap<>(1);
        File image = new File(rootPath, opt.get().getFilePath());
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024 * 200);
            FileUtils.copy(image, bos);
            String base64 = Base64.getEncoder().encodeToString(bos.toByteArray());
            ret.put("base64", "data:image/" + opt.get().getFileExtName() + ";base64," + base64);
            return ret;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw BusException.builder().message("文件不存在").httpStatus(404).build();
        } catch (Exception e) {
            e.printStackTrace();
            throw BusException.builder().message("读取文件失败").httpStatus(404).build();
        }

    }

    @GetMapping("/file/{id}")
    public void downloadByID(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) {
        Optional<FileDescription> opt = this.fileManager.findById(id);
        if (!opt.isPresent()) {
            throw BusException.builder().message("附件不存在").httpStatus(404).build();
        }
        File file = new File(rootPath, opt.get().getFilePath());
        try {
            // FireFox
            String filename = FileUtils.getFilename(file.getName());
            if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0 || request.getHeader("User-Agent").toLowerCase().indexOf("chrome") > 0) {
                try {
                    filename = new String(filename.getBytes("utf-8"), "ISO8859-1");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            response.addHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
            response.setContentType(FileUtils.getContentType(Files.getFileExtension(file.getName())));
            FileUtils.copy(file, response.getOutputStream());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw BusException.builder().message("文件不存在").httpStatus(404).build();
        } catch (Exception e) {
            e.printStackTrace();
            throw BusException.builder().message("读取文件失败").httpStatus(404).build();
        }

    }

    private String saveBase64(String base64) {
        StringUtils.startsWith(base64, "data:image/");
        int index = base64.indexOf(';');
        String fileType = base64.substring(11, index);
        String fileContent = base64.substring(index + 8);
        byte[] fileBytes = Base64.getDecoder().decode(fileContent);
        File target = new File(tempPath, UUID.randomUUID().toString() + "." + fileType);
        target.getParentFile().mkdirs();
        try {
            FileCopyUtils.copy(fileBytes, target);
        } catch (IOException e) {
            e.printStackTrace();
            throw BusException.builder().message("上传文件失败").build();
        }
        return target.getName();
    }

}
