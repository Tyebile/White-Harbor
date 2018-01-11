package tech.tyebile.pentos.generator.util;


import org.apache.commons.lang.ObjectUtils;
import org.apache.velocity.VelocityContext;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static tech.tyebile.pentos.generator.util.StringUtil.lineToHump;

/**
 * 代码生成类
 * @author tyebilesong
 * @created 05/01/2018
 */
public class MybatisGeneratorUtil {

    /**generatorConfig模板路径*/
    private static String generatorConfig_vm = "/template/generatorConfig.vm";

    /**
     * main method
     * @param jdbcDriver
     * @param jdbcUrl
     * @param jdbcUsername
     * @param jdbcPassword
     * @param packageName
     * @param tableName
     * @param tablePrefix
     * @throws Exception
     */
    public static void generator(
            String jdbcDriver,
            String jdbcUrl,
            String jdbcUsername,
            String jdbcPassword,
            String packageName,
            String tableName,
            String tablePrefix) throws Exception {

        String targetProject = MybatisGeneratorUtil.class.getResource("/").getPath().replace("/target/classes/", "") + "/src/main/java";


        String generatorConfigXml = MybatisGeneratorUtil.class.getResource("/").getPath().replace("/target/classes/", "") + "/src/main/resources/generatorConfig.xml";

        System.out.println("========== 开始生成generatorConfig.xml文件 ==========");
        List<Map<String, Object>> tables = new ArrayList<>();
        //模版影星
        VelocityContext context = new VelocityContext();

        Map<String, Object> table;

        JdbcUtil jdbcUtil = new JdbcUtil(jdbcDriver, jdbcUrl, jdbcUsername, AESCryptUtil.aesDecode(jdbcPassword));

        context.put("generator_javaModelGenerator_targetPackage", packageName + ".model");

        generatorConfig_vm = MybatisGeneratorUtil.class.getResource(generatorConfig_vm).getPath();


        table = new HashMap<>(2);
        table.put("table_name", tableName);
        //删除表名前缀
        table.put("model_name", lineToHump(ObjectUtils.toString(tableName).replace(tablePrefix, "")));
        tables.add(table);

        context.put("tables", tables);
        context.put("targetProject", targetProject);
        context.put("generator_jdbc_password", AESCryptUtil.aesDecode(jdbcPassword));
        context.put("generator_javaModelGenerator_targetPackage", packageName + ".model");

        VelocityUtil.generate(generatorConfig_vm, generatorConfigXml, context);

        System.out.println("========== 结束生成generatorConfig.xml文件 ==========");

        System.out.println("========== 开始运行MybatisGenerator =================");
        List<String> warnings = new ArrayList<>();
        File configFile = new File(generatorConfigXml);
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
        for (String warning : warnings) {
            System.out.println(warning);
        }
        System.out.println("========== 结束运行MybatisGenerator =================");

    }


}
