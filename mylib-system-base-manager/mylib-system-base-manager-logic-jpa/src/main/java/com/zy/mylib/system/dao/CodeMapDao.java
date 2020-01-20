package com.zy.mylib.system.dao;


import com.zy.mylib.system.entity.CodeMap;
import com.zy.mylib.data.jpa.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author ASUS
 */
public interface CodeMapDao extends JpaRepository<CodeMap, String> {
    Page<CodeMap> findByCodeLikeOrNameLike(Pageable page, String code, String name);

    Page<CodeMap> findByCodeLikeAndNameLike(Pageable page, String code, String name);
}
