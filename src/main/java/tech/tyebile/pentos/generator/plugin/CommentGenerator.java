package tech.tyebile.pentos.generator.plugin;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 生成model中，字段增加注释
 * @author tyebilesong
 * @created 05/01/2018
 */

public class CommentGenerator extends DefaultCommentGenerator {

	private static final String ID = "id";
	private static final String AUTHOR = "tyebilesong";

	@Override
	public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		topLevelClass.addJavaDocLine("/**");
		topLevelClass.addJavaDocLine(" * " + introspectedTable.getRemarks());
		topLevelClass.addJavaDocLine(" * @author " + AUTHOR);
		topLevelClass.addJavaDocLine(" * @created " + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		topLevelClass.addJavaDocLine(" */");
		topLevelClass.addAnnotation("@Table(tableName = \""+introspectedTable.getFullyQualifiedTable()+"\")");
	}

	@Override
	public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
		super.addFieldComment(field, introspectedTable, introspectedColumn);
		if (introspectedColumn.getRemarks() != null && !"".equals(introspectedColumn.getRemarks())) {
			if(field.getName().equals(ID)){
				field.addAnnotation("@PrimaryKeyField");
			}
			field.addJavaDocLine("/**");
			field.addJavaDocLine(" * " + introspectedColumn.getRemarks());
			field.addJavaDocLine(" */");
		}
	}
}
