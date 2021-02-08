package com.zy.mylib.utils;

import java.util.Map;

public class MapGetter {
  private Map<?, ?> map;

  public MapGetter(Map<?, ?> map) {
    this.map = map;
  }

  public <T> T get(Object key) {
    return (T) map.get(key);
  }
}
