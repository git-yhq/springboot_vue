package cn.baizhi.service;

import cn.baizhi.vo.MonthAndCount;

import java.util.List;

public interface MonthAndCountService {
    List<MonthAndCount> queryBySex(String sex);
}
