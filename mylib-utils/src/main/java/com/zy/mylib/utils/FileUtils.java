package com.zy.mylib.utils;

import com.google.common.io.Files;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.input.ReaderInputStream;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件工具类
 *
 * @author ASUS
 */
public class FileUtils {
    /**
     * <p>
     * 获取文件扩展名
     * </p>
     * <pre>
     * 例子
     * foo.txt      --&gt; "txt"
     * a/b/c.jpg    --&gt; "jpg"
     * a/b.txt/c    --&gt; ""
     * a/b/c        --&gt; ""
     * </pre>
     *
     * @param fileName
     */
    public static String getFileExtName(String fileName) {
        return FilenameUtils.getExtension(fileName);
    }

    /**
     * 复制文件，如果目标目录不存在则创建目录
     *
     * @param from
     * @param to
     * @throws IOException
     */
    public static void copyAndCreatePath(File from, File to) throws IOException {
        Files.createParentDirs(to);
        Files.copy(from, to);
    }

    /**
     * 复制文件，如果目标目录不存在则创建目录
     *
     * @param from
     * @param to
     * @throws IOException
     */
    public static void copyAndCreatePath(InputStream from, File to) throws IOException {
        Files.createParentDirs(to);
        org.apache.commons.io.FileUtils.copyToFile(from, to);
    }


    /**
     * 根据文件扩展名返回HTTP正文类型
     *
     * @param fileExt
     * @return
     */
    public static String getContentType(String fileExt) {
        if ("jpg".equals(fileExt)) {
            return "image/jpeg";
        } else if ("png".equals(fileExt)) {
            return "image/png";
        } else if ("pdf".equals(fileExt)) {
            return "application/pdf";
        } else if ("doc".equals(fileExt) || "docx".equals(fileExt)) {
            return "application/msword";
        }
        return "application/octet-stream";
    }

    /**
     * 文件复制
     *
     * @param from
     * @param to
     * @throws IOException
     */
    public static void copy(File from, OutputStream to) throws IOException {
        Files.copy(from, to);
    }

    /**
     * 文件复制
     *
     * @param from
     * @param to
     * @throws IOException
     */
    public static void copy(File from, File to) throws IOException {
        Files.copy(from, to);
    }

    /**
     * 获取文件名
     *
     * @param filePath
     * @return
     */
    public static String getFilename(String filePath) {
        return FilenameUtils.getName(filePath);
    }

    public static String readAllText(InputStream is) throws IOException {
        StringBuffer sb = new StringBuffer(1024 * 3);
        int len = 0;
        final int buffLen = 1024;
        final byte[] buff = new byte[buffLen];
        do {
            len = is.read(buff, 0, buffLen);
            sb.append(new String(buff, 0, len, "utf-8"));
        } while (len == buffLen);
        is.close();
        return sb.toString();
    }
}
