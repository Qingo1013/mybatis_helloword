package cn.qqa.mapper;

import cn.qqa.pojo.Emp;
import org.apache.ibatis.annotations.Select;

public interface EmpMapper {
    //根据id查询Emp对应实体（数据库字段）
    //@Select("select * from emp where id = #{id}")
    //查询
    Emp selectEmp(Integer id);

    //插入
    Integer insertEmp(Emp emp);

    //更新
    Integer updateEmp(Emp emp);

    //s删除
    Integer deleteEmp(Integer id);
}
