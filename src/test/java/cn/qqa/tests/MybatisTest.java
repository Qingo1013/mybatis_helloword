package cn.qqa.tests;

import cn.qqa.mapper.EmpMapper;
import cn.qqa.pojo.Emp;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Before;
import org.junit.Test;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.io.InputStream;

/**
 * Mybatis 搭建步骤：
 * 1.添加pom依赖（Mybatis核心依赖和数据库【Mysql】版本对应版本的驱动jar包）
 * 2.新建数据库及表
 * 3.添加Mybatis全局配置文件（可以再Mybatis官网中复制）
 * 4.修改全局配置文件中的 数据源配置信息
 * 5.添加数据库表中对应的POJO类（类比JavaBean类）
 * 6.添加对应的PojoMapper.xml（里面维护所有的sql语句，在mybatis中sql语句与java代码分离）
 *      (1)需要修改namespace: 如果是StatementId的方式没有特殊的要求
 *                        如果是接口绑定的方式必须等于接口的完整限定名（包+类名）
 *      (2)需要修改对应的id(唯一)，resultType对应返回的类型（如果为POJO，则要指定完整限定名）
 * 7.修改Mybatis全局配置文件中的<Mapper></Mapper>
 */
public class MybatisTest {

    SqlSessionFactory sqlSessionFactory;
    @Before
    public void before(){
        //从XML中构建SqlSeesionFactory
        String resource = "mybatis.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        }catch (IOException e){
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    /**
     * ·基于statementId的方式执行SQL
     *
     *  <mapper resource="EmpMapper.xml" />-
     * @throws IOException
     */
    @Test
    public void test01(){

        try (SqlSession session = sqlSessionFactory.openSession()) {
            Emp emp = (Emp) session.selectOne("cn.qqa.pojo.EmpMapper.selectEmp", 1);
            System.out.println(emp);
        }
    }
    /**
     * ·基于接口绑定的方式
     * java与sql解耦
     * 1.新建数据访问层的接口：POJOMapper
     * 2.添加mapper中对应的操作方法
     *      （1）方法名要与PojoMapper.xml（Mapper）中配置的节点的id一致
     *      （2）方法返回类型要与PojoMapper.xml（Mapper中配置的节点的resultType一致
     *      （3）PojoMapper.xml中对应节点的参数必须要在方法的参数中声明
     *  3.PojoMapper.xml中的namespace必须要和接口的完整限定名一致
     *  4.修改Mybatis全局配置文件中的mappers，
     *      <mapper class="cn.qqa.mapper.EmpMapper"/>
     *  5.一定要将PojoMapper.xml文件和接口文件放在同一级目录中
     *      如果使用maven，只需在resources目录中，新建和接口类应的文件夹就行了，生成会自动合并在一起
     *
     */
    @Test
    public void test02(){
        try (SqlSession session = sqlSessionFactory.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            Emp emp = mapper.selectEmp(1);
            System.out.println(emp);
        }
    }
    /***
     * ·基于注解的方式
     * 在接口方法上写上对应的注解（sql语句），没有解耦
     * @Select("select * from emp where id = #{id}")
     * 注意：注解可以和xml共存，但是xml中不能有方法中用到的id
     */
    @Test
    public void test03(){
        try (SqlSession session = sqlSessionFactory.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            Emp emp = mapper.selectEmp(1);
            System.out.println(emp);
        }
    }
}
