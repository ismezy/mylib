package com.zy.mylib.system.rest;

import com.zy.mylib.system.manager.SysConfigItemManager;
import com.zy.mylib.base.exception.BusException;
import com.zy.mylib.utils.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * @author ASUS
 */
@Controller
@RequestMapping("/files/temp")
public class TempFileController {
    @Inject
    private SysConfigItemManager configManager;
    @Value("${project.path.temp}")
    String tempPath;

    @GetMapping("/download/{filename}.{ext}")
    public void download(@PathVariable("filename") String filename, @PathVariable("ext") String extFileName, HttpServletResponse res) {
        File file = new File(tempPath, filename + "." + extFileName);
        try {
            FileUtils.copy(file, res.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw BusException.builder().message("下载失败").build();
        }
    }
}
