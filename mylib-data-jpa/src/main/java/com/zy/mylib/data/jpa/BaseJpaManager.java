package com.zy.mylib.data.jpa;

import com.zy.mylib.base.exception.BusException;
import com.zy.mylib.base.i18n.I18n;
import com.zy.mylib.utils.BeanUtils;
import com.zy.mylib.utils.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author ASUS
 */
public abstract class BaseJpaManager<T extends JpaEntity,PK extends Serializable> extends I18n implements JpaManager<T,PK>{
	@Inject
	EntityManager entityManager;

	/**
	 * 获取entityManager
	 * @return
	 */
    public EntityManager getEntityManager() {
        return entityManager;
    }

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
			if(!StringUtils.equals(existId, newId) && exist != null && newId != null) {
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


	/**
	 * 首字母大写
	 *
	 * @param word
	 * @return 首字母大写的字符串
	 */
	public String firstLowerCase(String word) {
		if (StringUtils.isBlank(word)) {
			return word;
		}
		return word.substring(0, 1).toLowerCase() + (word.length() > 2 ? word.substring(1) : "");
	}

	class Condition {
		String prop;
		PageUtils.Operate operate;
		Object value;

		public Condition(String prop, PageUtils.Operate operate, Object value) {
			this.prop = prop;
			this.operate = operate;
			this.value = value;
		}

		public List<Object> getParams() {
			List<Object> ret = new ArrayList<>(10);
			if(value.getClass().isArray()) {
				Object[] array = (Object[]) value;
				for(Object o : array) {
					ret.add(o);
				}
			} else {
				ret.add(value);
			}
			return ret;
		}

		public String getCondition(int index) {
			switch (operate) {
				case like:
					return prop + " like concat('%', ?" + index + " , '%')";
				case startsWith:
					return prop + " like concat('%', ?" + index + ")";
				case endsWith:
					return prop + " like concat(?" + index + ", '%')";
				case noteq:
					return prop + " <> ?" + index;
				case in:
					return prop + " in ?" + index;
				case notin:
					return prop + " not in ?" + index;
				case notNull:
					return prop + " is not null";
				case isNull:
					return prop + " is null";
				case gt:
					return prop + " > ?" + index;
				case gte:
					return prop + " >= ?" + index;
				case lt:
					return prop + " < ?" + index;
				case lte:
					return prop + " <= ?" + index;
				case between:
					return prop + " between ?" + index + " and ?" + (index + 1);
				default:
					return prop + " = ?" + index;
			}
		}
	}

	@Override
	public Page<T> pager(Pageable pageable, T filter, Map<String, PageUtils.Operate> operateMap, Map<String, Object> extParams) {
		List<Condition> conditions = genWhere("", filter, operateMap, extParams);
		StringBuilder where = new StringBuilder();
		List<Object> params = new ArrayList<>(20);
		for(Condition c : conditions) {
			if(where.length() > 0){
				where.append(" and ");
			} else {
				where.append("where ");
			}
			where.append(c.getCondition(params.size()));
			params.addAll(c.getParams());
		}
		StringBuilder odb = new StringBuilder();
		if(pageable.getSort() != null) {
			for(Sort.Order order : pageable.getSort()) {
				if(odb.length() == 0) {
					odb.append("order by ");
				} else {
					odb.append(",");
				}
				odb.append(order.getProperty()).append(" ").append(order.isDescending() ? "desc" : "asc");
			}
		}
		String entityName = filter.getClass().getName();
		String jpql = "select t from " + entityName + "  t where t.id in ?1 " + odb.toString();
		String countJpql = "select count(t) from " + entityName + " t " + where.toString();
		String queryIdJpql = "select t.id from " + entityName + " t " + where.toString() + " " + odb.toString();
		System.out.println(queryIdJpql);
		TypedQuery<Long> countQuery = entityManager.createQuery(countJpql, Long.class);
		TypedQuery<T> query = entityManager.createQuery(jpql, (Class<T>)filter.getClass());
		TypedQuery<String> idsQuery = entityManager.createQuery(queryIdJpql, String.class);
		for (int i = 0; i < params.size(); i++) {
			countQuery.setParameter(i, params.get(i));
			idsQuery.setParameter(i, params.get(i));
		}

		idsQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
				.setMaxResults(pageable.getPageSize());
		List<String> ids = idsQuery.getResultList();
		query.setParameter(1, ids);
		Long count = countQuery.getSingleResult();
		List<T> content = query.getResultList();
		Page<T> page = new PageImpl<T>(content, pageable, count);
		return page;
	}

	private List<Condition> genWhere(String path, JpaEntity filter, Map<String, PageUtils.Operate> operateMap, Map<String, Object> extParams) {
		List<Condition> ret = new ArrayList<>(20);
		Method[] methods = filter.getClass().getMethods();
		for (Method m : methods) {
			if (m.getName().startsWith("get") && m.getParameterCount() == 0) {
				String prop = firstLowerCase(m.getName().substring(3));
				if("class".equals(prop)) {
					continue;
				}
				Object value = null;
				if (extParams.containsKey(path + prop)) {
					value = extParams.get(path + prop);
				} else {
					try {
						value = m.invoke(filter);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (value == null) {
					continue;
				}
				if(value instanceof JpaEntity) {
					ret.addAll(genWhere(prop + ".", (JpaEntity) value, operateMap, extParams));
				} else {
					ret.add(genWhere(value, path + prop, operateMap));
				}
			}
		}
		return ret;
	}

	private Condition genWhere(Object value, String prop, Map<String, PageUtils.Operate> operateMap) {
		PageUtils.Operate o = PageUtils.Operate.eq;
		if(operateMap.containsKey(prop)) {
			o = operateMap.get(prop);
		}
		return new Condition(prop, o, value);
	}

}
