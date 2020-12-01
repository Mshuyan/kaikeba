package thread;

import java.util.*;

/**
 * @author shuyan
 */
public class LambdaStreamDemo {

    public void test(){
        List<MyData> datas = new ArrayList<>();
        Set<MyData> dataSet = new HashSet<>();

        datas.stream().forEach(data ->dataSet.add(data));
        // 可使用 方法引用符 简化
        datas.stream().forEach(dataSet::add);
    }
}
