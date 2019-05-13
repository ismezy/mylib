package com.zy.mylib.jackson;


import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

public class DefaultHibernateModule extends Hibernate5Module {
	public DefaultHibernateModule() {
		this.configure(Feature.FORCE_LAZY_LOADING, true);
	}
}
