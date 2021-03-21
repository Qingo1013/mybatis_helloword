package cn.qqa.tests;

import cn.qqa.mapper.EmpMapper;
import cn.qqa.pojo.Emp;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import javax.lang.model.element.VariableElement;
import java.io.IOException;
import java.io.InputStream;

public class MybatisCRUD {
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

    @Test
    public void select(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        Emp emp = mapper.selectEmp(1);
        System.out.println(emp);
    }

    /**
     * 注意要提交事务
     */
    @Test
    public void insert(){
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        Emp emp = new Emp();
        emp.setUsername("秦清澳");
        try{
            Integer result = mapper.insertEmp(emp);
            System.out.println(result);
            sqlSession.commit();
        }catch (Exception ex){
            System.out.println("插入失败");
            sqlSession.rollback();;
        }finally {
            sqlSession.close();
        }
    }

    /**
     * 更新
     * 注意要提交事务
     */
    @Test
    public void update(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        Emp emp = new Emp();
        emp.setId(5);
        emp.setUsername("汪星源");
        try{
            Integer result = mapper.updateEmp(emp);
            System.out.println(result);
            sqlSession.commit();
        }catch (Exception ex){
            System.out.println("插入失败");
            sqlSession.rollback();;
        }finally {
            sqlSession.close();
        }
    }

    /**
     * 删除
     * 注意要提交事务
     */
    @Test
    public void delete(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        try{
            Integer result = mapper.deleteEmp(4);
            System.out.println(result);
            sqlSession.commit();
        }catch (Exception ex){
            System.out.println("删除失败");
            sqlSession.rollback();;
        }finally {
            sqlSession.close();
        }
    }
}
