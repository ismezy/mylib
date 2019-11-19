package com.zy.mylib.system.manager.impl;

import com.zy.mylib.system.dao.CodeMapDao;
import com.zy.mylib.system.entity.CodeMap;
import com.zy.mylib.system.manager.CodeMapManager;
import com.zy.mylib.data.jpa.BaseJpaManager;
import com.zy.mylib.data.jpa.JpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 扬
 * @date 2017/3/21
 */
@Service
public class CodeMapManagerImpl extends BaseJpaManager<CodeMap, String> implements CodeMapManager {
  @Autowired
  private CodeMapDao dao;

  @Override
  protected JpaRepository<CodeMap, String> getRepository() {
    return dao;
  }
}
