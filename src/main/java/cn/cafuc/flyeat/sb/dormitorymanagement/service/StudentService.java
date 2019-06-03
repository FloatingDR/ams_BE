package cn.cafuc.flyeat.sb.dormitorymanagement.service;

import cn.cafuc.flyeat.sb.dormitorymanagement.model.Student;

import java.util.List;

public interface StudentService {
    Student getByBunk(int bunkId);
    List<Student> getAll();
    int deleteByBunkId(Integer bunkId);
    int deleteByRoomId(Integer roomId);
    int insert(Student student);
    int update(int id,int bunkId);
    Student getByNum(String num);
    List<String> getMajor();
    List<Student> selectByMajor(String major);
    List<Student> selectBySchool(String school);
}
