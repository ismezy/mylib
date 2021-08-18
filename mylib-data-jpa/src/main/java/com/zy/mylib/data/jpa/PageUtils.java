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
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.zy.mylib.data.jpa;

import com.zy.mylib.base.model.ComparisonOperators;
import com.zy.mylib.base.model.Condition;
import com.zy.mylib.base.model.LogicalOperators;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageUtils {

  static public <T extends JpaEntity> Specification<T> getSpecification(final List<Condition> conditions, Class<T> classes) {
    Specification<T> ret = new Specification<T>() {
      Map<String, From> fromMap = new HashMap();
      Root<T> root;

      @Override
      public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
                                   CriteriaBuilder cb) {
        Map<String, From<T, ? extends Serializable>> map = new HashMap<>(10);
        this.root = root;
        fromMap.put("", root);
        return getPredicates(conditions, cb);
      }

      private From getFrom(String key) {
        String[] paths = key.split("\\.");
        String path = "";
        From cur = root;
        for (int i = 0; i < paths.length - 1; i++) {
          String p = paths[i];
          path += (path.length() > 0 ? "." : "") + p;
          if (fromMap.containsKey(path)) {
            cur = fromMap.get(path);
            continue;
          } else {
            cur = cur.join(p);
            fromMap.put(path, cur);
          }
        }
        return cur;
      }

      @SuppressWarnings("rawtypes")
      private Predicate getPredicates(List<Condition> conditions, CriteriaBuilder cb) {
        return cb.and(conditions.stream().map(condition -> {
          if (condition.getConditions() != null && condition.getConditions().size() > 0) {
            // 条件组
            return cb.and(getGroupPredicate(condition, cb));
          }
          return cb.and(getPredicate(condition, cb));
        }).filter(it -> it != null).toArray(Predicate[]::new));
      }

      /**
       * 条件分组，用于支持或条件 (xxx=bbb and yyy=ccc) or (zzz > ddd)
       * @param condition
       * @param cb
       * @param
       */
      private Predicate getGroupPredicate(Condition condition, CriteriaBuilder cb) {
        if (LogicalOperators.or == condition.getLogicalOperator()) {
          return cb.or(condition.getConditions().stream().map(it -> getPredicate(it, cb))
              .filter(it -> it != null)
              .toArray(Predicate[]::new));
        }
        return cb.and(condition.getConditions().stream().map(it -> getPredicate(it, cb))
            .filter(it -> it != null)
            .toArray(Predicate[]::new));
      }

      private Predicate getPredicate(Condition condition, CriteriaBuilder cb) {
        From from = getFrom(getParentPath(condition.getProperty()));
        ComparisonOperators o = condition.getComparisonOperator();
        String name = getName(condition.getProperty());

        if (o == ComparisonOperators.startWith) {
          return cb.like(from.get(name).as(condition.getValue().getClass()), condition.getValue() + "%");
        } else if (o == ComparisonOperators.endWith) {
          return cb.like(from.get(name).as(condition.getValue().getClass()), "%" + condition.getValue());
        } else if (o == ComparisonOperators.like) {
          return cb.like(from.get(name).as(condition.getValue().getClass()), "%" + condition.getValue() + "%");
        } else if (o == ComparisonOperators.gt) {
          return cb.greaterThan(from.get(name).as(condition.getValue().getClass()), (Comparable) condition.getValue());
        } else if (o == ComparisonOperators.gte) {
          return cb.greaterThanOrEqualTo(from.get(name).as(condition.getValue().getClass()), (Comparable) condition.getValue());
        } else if (o == ComparisonOperators.lt) {
          return cb.lessThan(from.get(name).as(condition.getValue().getClass()), (Comparable) condition.getValue());
        } else if (o == ComparisonOperators.lte) {
          return cb.lessThanOrEqualTo(from.get(name).as(condition.getValue().getClass()), (Comparable) condition.getValue());
        } else if (o == ComparisonOperators.in) {
          if (condition.getValues() != null && condition.getValues().size() > 0) {
            return from.get(name).in(condition.getValues());
          }
          return null;
        } else if (o == ComparisonOperators.notIn) {
          if (condition.getValues() != null && condition.getValues().size() > 0) {
            return cb.not(from.get(name).in(condition.getValues()));
          }
          return null;
        } else if (o == ComparisonOperators.neq) {
          return cb.not(cb.equal(from.get(name), condition.getValue()));
        } else if (o == ComparisonOperators.between) {
          Object[] values = condition.getValues().toArray();
          if ((values[0] == null || (values[0] instanceof String && StringUtils.isBlank((String) values[0])))
              && (values[1] == null || (values[1] instanceof String && StringUtils.isBlank((String) values[1])))) {
            return null;
          } else if ((values[1] == null || (values[1] instanceof String && StringUtils.isBlank((String) values[1])))) {
            return cb.greaterThanOrEqualTo(from.get(name).as(values[0].getClass()), (Comparable) values[0]);
          } else if ((values[1] == null || (values[1] instanceof String && StringUtils.isBlank((String) values[1])))) {
            return cb.lessThanOrEqualTo(from.get(name).as(values[1].getClass()), (Comparable) values[1]);
          }
          return cb.between(from.get(name).as(values[0].getClass()), (Comparable) values[0], (Comparable) values[1]);
        } else if (o == ComparisonOperators.isNull) {
          return cb.isNull(from.get(name).as(condition.getValue().getClass()));
        } else if (o == ComparisonOperators.notNull) {
          return cb.isNotNull(from.get(name).as(condition.getValue().getClass()));
        } else {
          return cb.equal(from.get(name).as(condition.getValue().getClass()), condition.getValue());
        }
      }

      private String getParentPath(String property) {
        String[] paths = property.split("\\.");
        if (paths.length > 1) {
          return "";
        }
        ArrayUtils.subarray(paths, 0, paths.length - 2);
        return String.join(".", paths);
      }

      private String getName(String property) {
        String[] paths = property.split("\\.");
        return paths[paths.length - 1];
      }
    };
    return ret;
  }
}
