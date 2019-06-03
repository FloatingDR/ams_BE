package cn.cafuc.flyeat.sb.dormitorymanagement.service.impl;

import cn.cafuc.flyeat.sb.dormitorymanagement.mapper.StudentMapper;
import cn.cafuc.flyeat.sb.dormitorymanagement.model.Student;
import cn.cafuc.flyeat.sb.dormitorymanagement.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Resource
    StudentMapper studentMapper;

    @Override
    public Student getByBunk(int bunkId) {
        return studentMapper.getByBunk(bunkId);
    }

    @Override
    public List<Student> getAll() {
        return studentMapper.getAll();
    }

    @Transactional
    @Override
    public int deleteByBunkId(Integer bunkId) {
        return studentMapper.deleteByBunkId(bunkId);
    }

    @Transactional
    @Override
    public int deleteByRoomId(Integer roomId) {

        return studentMapper.deleteByRoomId(roomId);
    }

    @Override
    public Student getByNum(String num) {
        return studentMapper.getByNum(num);
    }

    @Transactional
    @Override
    public int update(int id, int bunkId) {
        return studentMapper.update(id,bunkId);
    }

    @Override
    public List<String> getMajor() {
        return studentMapper.getMajor();
    }

    @Override
    public List<Student> selectBySchool(String school) {
        return studentMapper.selectBySchool(school);
    }

    @Override
    public List<Student> selectByMajor(String major) {
        return studentMapper.selectByMajor(major);
    }

    @Transactional
    @Override
    public int insert(Student student) {
        return studentMapper.insert(student);
    }

}
