package com.zy.mylib.utils;

/**
 *
 * @author 扬
 * @date 2017/5/15
 */
public class ArrayUtils {
    /**
     * 从数组中查找元素的索引
     * @param elements 数组
     * @param element 元素
     * @return 数组下标
     */
    static public <T> int indexOf(T[] elements,T element){
        int i = 0;
        for (T elem: elements) {
            if(elem != null && elem.equals(element)){
                return i;
            }
            i++;
        }
        return -1;
    }
}
