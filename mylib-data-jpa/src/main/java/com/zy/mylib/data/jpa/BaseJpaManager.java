package com.zy.mylib.data.jpa;

import com.zy.mylib.base.i18n.I18n;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @author ASUS
 */
public abstract class BaseJpaManager<T extends JpaEntity,PK extends Serializable> extends I18n implements JpaManager<T,PK>{
	/**
	 * 获取实体对应的Repository
	 * @return
	 */
	protected abstract JpaRepository<T, PK> getRepository();

	@Override
	public Optional<T> findById(PK id){
		return getRepository().findById(id);
	}

	@Override
	public List<T> findAll(){
		return (List<T>)getRepository().findAll();
	}
	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public T save(T entity){
		return getRepository().save(entity);
	}
	@Override
    @Transactional(rollbackFor = RuntimeException.class)
	public Iterable<T> save(Iterable<T> entities){
		return getRepository().saveAll(entities);
	}
	@Override
    @Transactional(rollbackFor = RuntimeException.class)
	public void remove(T entity){
        getRepository().delete(entity);
	}
	@Override
    @Transactional(rollbackFor = RuntimeException.class)
	public void remove(Iterable<T> entities){
        getRepository().deleteAll(entities);
	}
	@Override
    @Transactional(rollbackFor = RuntimeException.class)
	public void remove(PK id){
        getRepository().deleteById(id);
	}
	/**
	 * 
	 * @param page
	 */
	@Override
	public Page<T> findPage(Pageable page, Specification<T> sepc){
		return getRepository().findAll(sepc, page);
	}
	
	@Override
    public List<T> getList(Specification<T> sepc){
		return getRepository().findAll(sepc);
	}

}
