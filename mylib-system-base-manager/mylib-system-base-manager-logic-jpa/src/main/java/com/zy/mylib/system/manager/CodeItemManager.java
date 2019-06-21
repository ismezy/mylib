package com.zy.mylib.system.manager;

import com.zy.mylib.system.entity.CodeItem;
import com.zy.mylib.data.jpa.JpaManager;

import java.util.List;
import java.util.Map;

/**
 *
 * @author 扬
 * @date 2017/3/21
 */
public interface CodeItemManager extends JpaManager<CodeItem, String> {
    List<CodeItem> findByCodeMap(String codemap);

    Map<String,CodeItem> findMapByCode(String codeMap);
}
