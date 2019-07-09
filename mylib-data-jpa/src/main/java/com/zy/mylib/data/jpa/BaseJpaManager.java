package com.zy.mylib.data.jpa;

import com.zy.mylib.base.exception.BusException;
import com.zy.mylib.base.i18n.I18n;
import com.zy.mylib.utils.BeanUtils;
import com.zy.mylib.utils.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
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

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public T add(T entity) {
		T exist = findExist(entity);
		if(exist != null) {
			throw BusException.builder().message(entity.description() + "已存在").build();
		}
		addProcess(entity);
		return save(entity);
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public T update(T entity) {
		T exist = findExist(entity);
		if(exist != null) {
			String existId = null;
			String newId = null;
			try {
				existId = (String) BeanUtils.getProperty(exist, "id");
				newId = (String) BeanUtils.getProperty(entity, "id");
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(StringUtils.equals(existId, newId) && exist != null && newId != null) {
				throw BusException.builder().message(entity.description() + "已存在").build();
			}
		}
		updateProcess(entity);
		return save(entity);
	}

	/**
	 * 修改保存前处理方法
	 * @param entity
	 */
	protected void updateProcess(T entity) {
	}

	/**
	 * 查找已存在数据
	 * @param entity
	 * @return
	 */
	protected T findExist(T entity) {
		return null;
	}

	/**
	 * 新增保存前处理方法
	 * @param entity
	 */
	protected void addProcess(T entity) {
	}
}
