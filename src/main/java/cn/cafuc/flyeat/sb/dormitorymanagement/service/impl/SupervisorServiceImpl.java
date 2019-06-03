package cn.cafuc.flyeat.sb.dormitorymanagement.service.impl;

import cn.cafuc.flyeat.sb.dormitorymanagement.mapper.SupervisorMapper;
import cn.cafuc.flyeat.sb.dormitorymanagement.model.Supervisor;
import cn.cafuc.flyeat.sb.dormitorymanagement.service.SupervisorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SupervisorServiceImpl implements SupervisorService {
    @Resource
    SupervisorMapper supervisorMapper;

    public List<Supervisor> getAll(){
        return supervisorMapper.getAll();
    }

    @Transactional
    @Override
    public int insert(Supervisor record) {
        return supervisorMapper.insert(record);
    }

    @Override
    public Supervisor get(String num) {
        return supervisorMapper.get(num);
    }
}
