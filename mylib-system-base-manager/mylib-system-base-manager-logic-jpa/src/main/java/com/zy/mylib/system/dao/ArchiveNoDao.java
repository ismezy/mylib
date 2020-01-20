package com.zy.mylib.system.dao;

import com.zy.mylib.system.entity.ArchiveNo;
import com.zy.mylib.data.jpa.JpaRepository;

import java.util.List;

/**
 * @author ASUS
 */
public interface ArchiveNoDao extends JpaRepository<ArchiveNo, String> {
    List<ArchiveNo> findByType(String type);

}
