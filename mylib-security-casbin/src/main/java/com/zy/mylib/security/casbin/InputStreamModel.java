package com.zy.mylib.security.casbin;

import com.zy.mylib.utils.FileUtils;
import org.casbin.jcasbin.model.Model;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author ASUS
 */
public class InputStreamModel extends Model {
    public InputStreamModel(InputStream inputStream) {
        try {
            this.loadModelFromText(FileUtils.readAllText(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
