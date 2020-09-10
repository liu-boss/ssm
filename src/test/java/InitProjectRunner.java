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
    private static String dbHost = "127.0.0.1";                 // 数据库地址
    private static String dbName = "ssm_shiro";                 // 数据库名称
    private static String userName = "root";                    // 登录账户
    private static String userPassword = "zhangyukang";         // 登录密码
    private static String dbPort = "3306";                      // 数据库端口号


    /**
     * @return
     * @throws Exception
     * @功能描述： 获取数据库连接
     */
    public static Connection getMySqlConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?useUnicode=true&characterEncoding=utf-8&port=" + dbPort + "&autoReconnect=true&serverTimezone=GMT%2B8";
        return DriverManager.getConnection(url, userName, userPassword);
    }


    @Test
    public void testInit() throws Exception {
        try {
            Connection conn = getMySqlConnection();
            ScriptRunner runner = new ScriptRunner(conn);
            Resources.setCharset(StandardCharsets.UTF_8); //设置字符集,不然中文乱码插入错误
            runner.setLogWriter(null);//设置是否输出日志
            // 绝对路径读取
            //Reader read = new FileReader(new File("f:\\test.sql"));
            // 从class目录下直接读取
            Reader read = Resources.getResourceAsReader("init.sql");
            runner.runScript(read);
            runner.closeConnection();
            conn.close();
            System.out.println("sql脚本执行完毕");
        } catch (Exception e) {
            System.out.println("sql脚本执行发生异常");
            e.printStackTrace();
        }
    }
}
