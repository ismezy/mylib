package com.zy.mylib.webmvc.model;

import com.zy.mylib.base.model.Condition;
import com.zy.mylib.base.model.PageRequest;
import com.zy.mylib.base.model.SortRequest;

import java.util.List;

public class QueryWrapper {
  List<Condition> conditions;
  List<SortRequest> sorts;
  PageRequest page;

  public List<Condition> getConditions() {
    return conditions;
  }

  public QueryWrapper setConditions(List<Condition> conditions) {
    this.conditions = conditions;
    return this;
  }

  public List<SortRequest> getSorts() {
    return sorts;
  }

  public QueryWrapper setSorts(List<SortRequest> sorts) {
    this.sorts = sorts;
    return this;
  }

  public PageRequest getPage() {
    return page;
  }

  public QueryWrapper setPage(PageRequest page) {
    this.page = page;
    return this;
  }
}
