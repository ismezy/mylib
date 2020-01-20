package com.zy.mylib.system.dao;

import com.zy.mylib.system.entity.CodeItem;
import com.zy.mylib.data.jpa.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author ASUS
 */
public interface CodeItemDao extends JpaRepository<CodeItem, String> {
    List<CodeItem> findByCodemap(String codemap);

    Page<CodeItem> findByCodemapLike(Pageable page, String codemap);

    CodeItem findByCodemapAndCode(String codemap, String code);

    List<CodeItem> findByCodemapOrderByCodeAsc(String codemap);

    Page<CodeItem> findByCodemapLikeOrderByCodeAsc(Pageable page, String string);

    List<CodeItem> findByCodemapOrderBySortAscCodeAsc(String codemap);

    Page<CodeItem> findByCodemapOrderBySortAscCodeAsc(Pageable page, String codemap);
}
