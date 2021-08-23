package cn.baizhi.dao;

import java.util.List;

public interface BaseDao<T,K> {
    //查所有
    List<T> selectAll();

    //添加
    void insert(T t);

    //根据id删除
    void deleteById(K k);

    //根据id查一个
    T selectById(K k);

    //修改
    void update(T t);
}
