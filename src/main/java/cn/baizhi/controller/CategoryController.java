package cn.baizhi.controller;

import cn.baizhi.entity.Category;
import cn.baizhi.service.CategoryServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("category")
@CrossOrigin
public class CategoryController {
    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);
    @Autowired
    private CategoryServiceImpl categoryService;
    @RequestMapping("queryByLevels")
    public List<Category> queryByLevels(int levels){
        log.debug(String.valueOf(levels));
        return categoryService.queryByLevels(levels);
    }
    @RequestMapping("queryByParentId")
    public List<Category> queryByParentId(String id){
        log.debug("id");
        return categoryService.queryByParentId(id);
    }
    @RequestMapping("save")
    public Map<String,Object> save(@RequestBody Category category){
        log.debug(category.toString());
        return categoryService.save(category);
    }
    @RequestMapping("delete")
    public void delete(String id){
        log.debug(id);
        categoryService.dropById(id);
    }
    @RequestMapping("deleteByParentId")
    public Map<String,Object> deleteByParentId(String id){
        log.debug(id);
        return categoryService.deleteByParentId(id);
    }
    @RequestMapping("save2")
    public Map<String,Object> save2(@RequestBody Category category){
        log.debug(category.toString());
        return categoryService.save2(category);
    }
}
