/*
 * Copyright © ${project.inceptionYear} ismezy (ismezy@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
