import org.junit.Test;
import org.springframework.context.expression.BeanExpressionContextAccessor;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.PropertyAccessor;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.ReflectivePropertyAccessor;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
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


