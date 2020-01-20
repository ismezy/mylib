package com.zy.mylib.system.rest;

import com.zy.mylib.system.entity.SysConfigItem;
import com.zy.mylib.system.manager.SysConfigItemManager;
import com.zy.mylib.data.jpa.JpaManager;
import com.zy.mylib.data.jpa.PageUtils;
import com.zy.mylib.webmvc.data.jpa.JpaEntityRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统配置rest接口
 *
 * @author ASUS
 */
@RestController
@RequestMapping("/sys/config/manager")
public class SysConfigItemRest extends JpaEntityRestController<SysConfigItem, String> {
    @Autowired
    private SysConfigItemManager sysConfigItemManager;

    @Override
    protected JpaManager<SysConfigItem, String> getManager() {
        return sysConfigItemManager;
    }

    @Override
    protected Map<String, PageUtils.Operate> getPageOperate(SysConfigItem entity, Map<String, PageUtils.Operate> operateMap) {
        return new HashMap<>(0);
    }

    @Override
    protected Map<String, Object> getPageExtendParam(SysConfigItem entity, HttpServletRequest request, Map<String, Object> extendParams) {
        return new HashMap<>(0);
    }
}