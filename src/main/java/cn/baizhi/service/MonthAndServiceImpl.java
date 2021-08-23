package cn.baizhi.service;

import cn.baizhi.dao.MonthAndCountDao;
import cn.baizhi.vo.MonthAndCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class MonthAndServiceImpl implements MonthAndCountService {
    @Autowired
    private MonthAndCountDao monthAndCountDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<MonthAndCount> queryBySex(String sex) {
        return monthAndCountDao.selectCountBySex(sex);
    }
}
