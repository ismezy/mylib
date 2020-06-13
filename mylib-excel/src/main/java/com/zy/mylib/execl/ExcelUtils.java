package com.zy.mylib.execl;

import org.jxls.common.Context;
import org.jxls.transform.Transformer;
import org.jxls.transform.poi.PoiTransformer;
import org.jxls.util.JxlsHelper;

import java.io.*;
import java.util.Map;

/**
 * excel工具类
 *
 * @author ASUS
 */
public class ExcelUtils {
    /**
     * 生成excel文件
     * @param data 模板参数
     * @param template 模板
     * @param target 保存路径
     * @throws IOException
     */
    public static void genExcel(Map<String, Object> data, InputStream template, File target) throws IOException {
        JxlsHelper jxlsHelper = JxlsHelper.getInstance();
        Context context = PoiTransformer.createInitialContext();
        data.forEach((key, value) -> context.putVar(key, value));

        FileOutputStream fileOutputStream = new FileOutputStream(target);
        Transformer trans = jxlsHelper.createTransformer(template, fileOutputStream);
        jxlsHelper.processTemplate(context, trans);
        fileOutputStream.close();
    }
}
