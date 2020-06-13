import com.zy.mylib.execl.ExcelUtils;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 测试excel工具类
 */
public class TestExcel {
    private String[] firstName = {"赵", "钱", "孙", "李", "周", "武", "郑", "王"};
    private String[] middleName = {"欣", "爱", "小", "百", "千", "万"};
    private String[] lastName = {"桐", "宜", "国", "勇", "刚", "民", "庆", "玲", "凤", "龙"};
    Random random = new Random(new Date().getTime());
    /**
     *
     */
    @Test
    public void genExcel() throws IOException {
        InputStream inputStream = ClassLoader.getSystemResourceAsStream("sample.xlsx");
        Map<String, Object> data = new HashMap<>(1);
        List<Map<String, Object>> students = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            Map<String, Object> s = new HashMap<>(5);
            s.put("name", randName());
            s.put("age", 13 + random.nextInt(2));
            s.put("chs", 70 + random.nextInt(51));
            s.put("eng", 70 + random.nextInt(51));
            s.put("math", 70 + random.nextInt(51));
            students.add(s);
        }
        data.put("students", students);
        ExcelUtils.genExcel(data, inputStream, new File("/test.xlsx"));
    }

    private String randName() {
        String name = firstName[random.nextInt(firstName.length)];
        if(random.nextBoolean()) {
            name += middleName[random.nextInt(middleName.length)];
        }
        return name + lastName[random.nextInt(lastName.length)];
    }
}
