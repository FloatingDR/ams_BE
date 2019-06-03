package cn.cafuc.flyeat.sb.dormitorymanagement.mapper;

import cn.cafuc.flyeat.sb.dormitorymanagement.model.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentMapper {
    Student getByBunk(int bunkId);
    int insert(Student record);
    List<Student>  getAll();
    int insertSelective(Student record);
    int deleteByBunkId(Integer bunkId);
    int deleteByRoomId(Integer roomId);
    int update(int id,int bunkId);
    Student getByNum(String num);
    List<String> getMajor();
    List<Student> selectByMajor(String major);
    List<Student> selectBySchool(String school);

}