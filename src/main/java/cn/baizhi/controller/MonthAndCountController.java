package cn.baizhi.controller;

import cn.baizhi.service.MonthAndServiceImpl;
import cn.baizhi.vo.MonthAndCount;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("hello")
public class MonthAndCountController {
    @Autowired
    private MonthAndServiceImpl monthAndService;
    @RequestMapping("findBySex")
    public Map<String,Object> findBySex(){
        Map<String,Object> map = new HashedMap<>();
        List<String> data = Arrays.asList("1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月");
        List<Integer> manCount = new ArrayList<>();
        List<Integer> womanCount = new ArrayList<>();
        System.out.println(manCount);
        System.out.println(womanCount);

        List<MonthAndCount> list1 = monthAndService.queryBySex("男");
        List<MonthAndCount> list2 = monthAndService.queryBySex("女");
        /*for (MonthAndCount monthAndCount : list1) {
            System.out.println(monthAndCount);
        }*/
       /* System.out.println("1111111111111111111111111111111");
        for (MonthAndCount monthAndCount : list2) {
            System.out.println(monthAndCount);
        }*/
        for(int i = 1;i<=12;i++){
            Integer x = null;
            for (int i1 = 0; i1 < list1.size(); i1++) {
                if (list1.get(i1).getMonth()==i){
                    x = i1;
                }
            }
                if (x != null){
                    manCount.add(list1.get(x).getCount());
                }else{
                    manCount.add(0);
                }
        }

//            System.out.println("manCount"+manCount);

        for(int i = 1;i<=12;i++){
            Integer x = null;
            for (int i1 = 0; i1 < list2.size(); i1++) {
                if (list2.get(i1).getMonth()==i){
                    x = i1;
                }
            }
            if (x != null){
                womanCount.add(list2.get(x).getCount());
            }else{
                womanCount.add(0);
            }
        }
       /* System.out.println(manCount);
        System.out.println(womanCount);*/
//        System.out.println("womanCount"+womanCount);

        map.put("data", data);
        map.put("manCount", manCount);
        map.put("womanCount",womanCount);
        return map;
    }
}
