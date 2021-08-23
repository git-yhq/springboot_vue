package cn.baizhi.dao;

import cn.baizhi.vo.MonthAndCount;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthAndCountDao {
    //根据性别查询每个月的注册人数
    List<MonthAndCount> selectCountBySex(String sex);
}
