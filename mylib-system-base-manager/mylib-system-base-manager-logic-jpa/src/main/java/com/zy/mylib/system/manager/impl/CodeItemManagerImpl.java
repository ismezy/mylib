package com.zy.mylib.system.manager.impl;

import com.zy.mylib.system.dao.CodeItemDao;
import com.zy.mylib.system.entity.CodeItem;
import com.zy.mylib.system.manager.CodeItemManager;
import com.zy.mylib.data.jpa.BaseJpaManager;
import com.zy.mylib.data.jpa.JpaRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 扬
 * @date 2017/3/21
 */
@Service
public class CodeItemManagerImpl extends BaseJpaManager<CodeItem, String> implements CodeItemManager {
    @Inject
    private CodeItemDao dao;

    @Override
    protected JpaRepository<CodeItem, String> getRepository() {
        return dao;
    }

    @Override
    public List<CodeItem> findByCodeMap(String codemap) {
        return dao.findByCodemap(codemap);
    }

    @Override
    public Map<String, CodeItem> findMapByCode(String codeMap) {
        List<CodeItem> list = dao.findByCodemapOrderBySortAscCodeAsc(codeMap);
        LinkedHashMap<String, CodeItem> map = new LinkedHashMap<>();
        for (CodeItem codeItem : list) {
            map.put(codeItem.getCode(), codeItem);
        }
        return map;
    }
}
