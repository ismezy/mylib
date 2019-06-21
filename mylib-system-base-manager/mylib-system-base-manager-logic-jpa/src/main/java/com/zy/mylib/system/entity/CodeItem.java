package com.zy.mylib.system.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.zy.mylib.data.jpa.NameCodeEntity;
import io.swagger.annotations.ApiModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author ASUS
 */
@Entity
@Table(name="sys_codeitem")
@ApiModel(value = "CodeItem", description = "字典项", parent = NameCodeEntity.class)
public class CodeItem extends NameCodeEntity {
	private static final long serialVersionUID = -3532091040124604358L;

	/**
	 * 代码集ID
     */
	@NotBlank(message = "{error.c6901}",groups = {AddCheck.class, UpdateCheck.class})
	@Size(min = 2,max = 32, message = "{error.c6903}",groups = {AddCheck.class, UpdateCheck.class})
	@Column(name="codemap",length=32)
	@JsonView(BaseView.class)
	private String codemap;
	/**
	 * 顺序号
     */
	@Column(name="SORT",length=16)
	@Size(max = 16, message = "{error.c6909}",groups = {AddCheck.class, UpdateCheck.class})
	@JsonView(BaseView.class)
	private String sort;
	/**
	 * 代码项值
     */
	@Column(length = 32)
	@Size(max = 32, message = "{error.c6908}",groups = {AddCheck.class, UpdateCheck.class})
	@JsonView(BaseView.class)
	private String value;
	
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getCodemap() {
		return codemap;
	}
	public void setCodemap(String codemap) {
		this.codemap = codemap;
	}

	/**
	 * Sets new value.
	 *
	 * @param value New value of value.
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Gets value.
	 *
	 * @return Value of value.
	 */
	public String getValue() {
		return value;
	}
}
