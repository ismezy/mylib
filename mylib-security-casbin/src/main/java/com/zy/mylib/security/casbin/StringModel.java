package com.zy.mylib.security.casbin;

import org.casbin.jcasbin.model.Model;

/**
 * @author ASUS
 */
public class StringModel extends Model {
    public StringModel(String model) {
        this.loadModelFromText(model);
    }
}
