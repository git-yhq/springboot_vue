package cn.baizhi.service;

import cn.baizhi.annotation.DeleteCache;
import cn.baizhi.dao.CategoryDao;
import cn.baizhi.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("categoryService")
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDao categoryDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Category> queryByLevels(int levels) {
        return categoryDao.selectByLevels(levels);
    }

    @DeleteCache
    @Override
    public Map<String, Object> deleteByParentId(String id) {
        Map<String,Object> map = new HashMap<>();
        //删除之前，先根据一级类别id查看一下有没有二级分类
        List<Category> list = categoryDao.selectByParentId(id);
        if(list.size()==0){
            categoryDao.deleteById(id);
            map.put("msg", "删除成功");
        }else{
            map.put("msg", "该类下有二级类别");
        }
        return map;
    }

    @DeleteCache
    @Override
    public void dropById(String id) {
        categoryDao.deleteById(id);
    }

    @DeleteCache
    @Override
    public Map<String, Object> save2(Category category) {
        Map<String,Object> map = new HashMap<>();
        //添加之前先看看有没有同名的一级类别
        Category category1 = categoryDao.selectByCateName(category.getCateName());
        if(category1!=null){ //已有同名的类别
            map.put("msg", "该类别名已经存在");
        }else{
            category.setId(UUID.randomUUID().toString());
            categoryDao.add(category);
            map.put("msg", "添加成功");
        }
        return map;
    }

    @DeleteCache
    @Override
    public Map<String,Object> save(Category category) {
        Map<String,Object> map = new HashMap<>();
        //将category的parentId存在map中
        map.put("id", category.getParentId());
        //添加之前先判断添加的子类别是否已经存在
        Category category1 = categoryDao.selectByCateName(category.getCateName());
        if(category1!=null){ //类别已经存在
            map.put("msg", "类别已经存在");
        }else {//类别不存在，添加
            category.setId(UUID.randomUUID().toString());
            categoryDao.add(category);
            map.put("msg", "添加成功");
        }

        return map;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Category> queryByParentId(String id) {
        return categoryDao.selectByParentId(id);
    }
}
