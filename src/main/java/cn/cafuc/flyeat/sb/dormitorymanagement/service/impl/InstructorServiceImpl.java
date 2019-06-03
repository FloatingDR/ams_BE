package cn.cafuc.flyeat.sb.dormitorymanagement.service.impl;

import cn.cafuc.flyeat.sb.dormitorymanagement.mapper.InstructorMapper;
import cn.cafuc.flyeat.sb.dormitorymanagement.model.Instructor;
import cn.cafuc.flyeat.sb.dormitorymanagement.service.InstructorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class InstructorServiceImpl implements InstructorService {
    @Resource
    InstructorMapper instructorMapper;

    @Override
    public Instructor getByIdNum(String num) {
        return instructorMapper.getByIdNum(num);
    }

    @Override
    public Instructor getByName(String name) {
        return instructorMapper.getByName(name);
    }

    @Transactional
    @Override
    public int insert(Instructor record) {
        return instructorMapper.insert(record);
    }

    @Override
    public Instructor getById(int id) {
        return instructorMapper.getById(id);
    }
}
