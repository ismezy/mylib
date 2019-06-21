package com.zy.mylib.system.rest;

import com.zy.mylib.system.entity.CodeItem;
import com.zy.mylib.system.manager.CodeItemManager;
import com.zy.mylib.data.jpa.JpaManager;
import com.zy.mylib.data.jpa.PageUtils;
import com.zy.mylib.webmvc.data.jpa.JpaEntityRestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 扬
 * @date 2017/3/21
 */
@RestController
@RequestMapping("/sys/codeitem")
@Api(tags = "字典项管理")
public class CodeItemController extends JpaEntityRestController<CodeItem, String> {
    @Autowired
    private CodeItemManager codeItemManager;

    @Override
    protected JpaManager<CodeItem, String> getManager() {
        return codeItemManager;
    }

    @Override
    protected Map<String, PageUtils.Operate> getPageOperate(CodeItem codeItem, Map<String, PageUtils.Operate> operateMap) {
        operateMap.put("name", PageUtils.Operate.like);
        return operateMap;
    }

    @Override
    protected Map<String, Object> getPageExtendParam(CodeItem entity, HttpServletRequest request, Map<String, Object> extendParams) {
        return extendParams;
    }
    @ApiOperation(
            value = "根据字典编码查找字典项",
            notes = "根据字典编码查找字典项。",
            httpMethod = "GET",
            produces = "JSON",
            responseContainer = "List",
            response = CodeItem.class
    )
    @RequestMapping(value = "", method = RequestMethod.GET, params = "codemap")
    public List<CodeItem> findByCodemap(@RequestParam("codemap") @ApiParam(value="字典编码", required = true, type="query", name="codemap") String codemap){
        Map<String, Object> params = new HashMap<>(1);
        params.put("codemap",codemap);
        return  codeItemManager.findByCodeMap(codemap);
    }
//    @RequestMapping(value = "",method = RequestMethod.DELETE, params = {"codemap","code"})
//    public void removeById(@RequestParam("codemap") String codemap, @RequestParam("code") String code){
//        CodeItemPK pk = new CodeItemPK();
//        pk.setCode(code);
//        pk.setCodemap(codemap);
//        codeItemManager.remove(pk);
//    }

    @RequestMapping(value="/convert/{codeMap}",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,CodeItem> findConvert(@PathVariable("codeMap") String codeMap){
        return codeItemManager.findMapByCode(codeMap);
    }
}
