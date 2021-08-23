package cn.baizhi.dao;

import cn.baizhi.entity.Category;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CategoryDao extends BaseDao {
    //根据类别等级查询类别
    List<Category> selectByLevels(int levels);

    //根据父类别id查询子类别
    List<Category> selectByParentId(String id);

    //根据父类id添加子类别
    void add(Category category);

    //根据类别的名字查询
    Category selectByCateName(String cateName);

    //根据id删除类别
    void deleteById(String id);
}
