import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author zhangyukang
 * @Date 2020/7/6 11:13
 * @Version 1.0
 **/
public class MybatisGenerator {


    public static void main(String[] args) throws Exception {
        Logger logger=LoggerFactory.getLogger(MybatisGenerator.class);
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        InputStream inputStream = MybatisGenerator.class.getResourceAsStream("mybatis/mybatis-generator.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(inputStream);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
                callback, warnings);
        myBatisGenerator.generate(null);
        logger.info("generator success");
    }
}
