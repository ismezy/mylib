package com.zy.mylib.data.jpa;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

/**
 * @author ASUS
 */
@JsonFilter("JacksonFilter")
public class JsonFilterProvider extends SimpleFilterProvider {

}
