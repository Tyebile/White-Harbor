package tech.tyebile.pentos.generator;

import tech.tyebile.pentos.generator.util.MybatisGeneratorUtil;
import tech.tyebile.pentos.generator.util.PropertiesFileUtil;

/**
 * 代码生成类
 * @author tyebilesong
 * @created 05/01/2018
 */
public class Generator {

    /**表名前缀*/
    private static String TABLE_PREFIX = "t_b_";
    /**包路径*/
    private static String PACKAGE_NAME = "tech.tyebile.pentos.generator";

    private static String JDBC_DRIVER = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.driver");
    private static String JDBC_URL = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.url");
    private static String JDBC_USERNAME = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.username");
    private static String JDBC_PASSWORD = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.password");

    /**
     * 自动代码生成
     * @param args
     */
    public static void main(String[] args) throws Exception {

        String tableName = "t_b_chain";
        MybatisGeneratorUtil.generator(JDBC_DRIVER, JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD, PACKAGE_NAME, tableName, TABLE_PREFIX);
    }
}
