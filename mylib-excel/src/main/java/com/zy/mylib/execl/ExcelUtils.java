/*
 * Copyright © ${project.inceptionYear} ismezy (ismezy@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
