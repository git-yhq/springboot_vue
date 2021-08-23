package cn.baizhi.service;

import cn.baizhi.entity.Category;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    //根据类别等级差类别
    List<Category> queryByLevels(int levels);

    //根据父类id查询子类别
    List<Category> queryByParentId(String id);

    //根据父类id添加子类别
    Map<String,Object> save(Category category);

    //根据id删除类别
    void dropById(String id);

    //删除一级类别
    Map<String,Object> deleteByParentId(String id);

    //添加一级类别
    Map<String,Object> save2(Category category);
}
