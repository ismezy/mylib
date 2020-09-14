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
package com.zy.mylib.security.casbin;


import org.casbin.jcasbin.model.Model;
import org.casbin.jcasbin.persist.Adapter;
import org.casbin.jcasbin.persist.Helper;

import java.io.*;
import java.util.List;

/**
 * casbin adapter string impl
 *
 * @author ASUS
 */
public class StringAdapter implements Adapter {
    private String policy;

    public StringAdapter(String policy) {
        this.policy = policy;
    }

    @Override
    public void loadPolicy(Model model) {
        loadPolicyFile(model, Helper::loadPolicyLine);
    }

    @Override
    public void savePolicy(Model model) {
        throw new Error("not implemented");
    }

    @Override
    public void addPolicy(String sec, String ptype, List<String> rule) {
        throw new Error("not implemented");
    }

    @Override
    public void removePolicy(String sec, String ptype, List<String> rule) {
        throw new Error("not implemented");
    }

    @Override
    public void removeFilteredPolicy(String sec, String ptype, int fieldIndex, String... fieldValues) {
        throw new Error("not implemented");
    }

    private void loadPolicyFile(Model model, Helper.loadPolicyLineHandler<String, Model> handler) {
        BufferedReader br = new BufferedReader(new StringReader(policy));

        String line;
        try {
            while ((line = br.readLine()) != null) {
                handler.accept(line, model);
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new Error("IO error occurred");
        }

    }
}
