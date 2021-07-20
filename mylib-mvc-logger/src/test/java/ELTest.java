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
import org.junit.jupiter.api.Test;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.HashMap;
import java.util.Map;

public class ELTest {
    @Test
    public void test() {
        SpelParserConfiguration config = new SpelParserConfiguration(SpelCompilerMode.MIXED,
                this.getClass().getClassLoader());

        SpelExpressionParser parser = new SpelExpressionParser(config);

        Map<String, Object> params = new HashMap<>(1);
        params.put("test", null);
        ParserContext parserContext = new TemplateParserContext("${", "}");
        StandardEvaluationContext context = new StandardEvaluationContext(params);
        context.addPropertyAccessor(new MapAccessor());
        Expression expr = parser.parseExpression("${test?.value}", parserContext);
        String ret = expr.getValue(context, String.class);
        System.out.println(ret);
    }

}


