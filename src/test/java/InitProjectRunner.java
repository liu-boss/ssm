import com.coderman.util.PropertiesUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.Test;

import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 初始化项目
 *
 * @Author zhangyukang
 * @Date 2020/8/31 09:27
 * @Version 1.0
 **/
public class InitProjectRunner {

    private static final String driverClass;
    private static final String user;
    private static final String password;
    private static final String url;


    static {
        driverClass= PropertiesUtil.getProperty("driverClass");
        user= PropertiesUtil.getProperty("user");
        password= PropertiesUtil.getProperty("password");
        url= PropertiesUtil.getProperty("url");
    }

    @Test
    public void init() {
        initProject("init.sql");
    }


    public static void initProject(String sqlFile){
        try {
            Connection conn = getMySqlConnection();
            ScriptRunner runner = new ScriptRunner(conn);
            Resources.setCharset(StandardCharsets.UTF_8); //设置字符集,不然中文乱码插入错误
            runner.setLogWriter(null);//设置是否输出日志
            // 绝对路径读取
            //Reader read = new FileReader(new File("f:\\test.sql"));
            // 从class目录下直接读取
            Reader read = Resources.getResourceAsReader(sqlFile);
            runner.runScript(read);
            runner.closeConnection();
            conn.close();
            System.out.println("sql脚本执行完毕");
        } catch (Exception e) {
            System.out.println("sql脚本执行发生异常");
            e.printStackTrace();
        }
    }

    /**
     * @return
     * @throws Exception
     * @功能描述： 获取数据库连接
     */
    public static Connection getMySqlConnection() throws Exception {
        Class.forName(driverClass);
        return DriverManager.getConnection(url, user, password);
    }

}
