package com.zy.mylib.system.rest;

import com.zy.mylib.system.entity.CodeMap;
import com.zy.mylib.system.manager.CodeMapManager;
import com.zy.mylib.data.jpa.JpaManager;
import com.zy.mylib.data.jpa.PageUtils;
import com.zy.mylib.webmvc.data.jpa.JpaEntityRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author 扬
 * @date 2017/3/21
 */
@RestController
@RequestMapping("/sys/codemap")
public class CodeMapController extends JpaEntityRestController<CodeMap, String> {
    @Autowired
    private CodeMapManager codeMapManager;

    @Override
    protected JpaManager<CodeMap, String> getManager() {
        return codeMapManager;
    }

    @Override
    protected Map<String, PageUtils.Operate> getPageOperate(CodeMap codemap, Map<String, PageUtils.Operate> operateMap) {
        operateMap.put("code", PageUtils.Operate.like);
        operateMap.put("name", PageUtils.Operate.like);
        return operateMap;
    }

    @Override
    protected Map<String, Object> getPageExtendParam(CodeMap entity, HttpServletRequest request, Map<String, Object> extendParams) {
        return extendParams;
    }
}
