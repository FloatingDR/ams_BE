package cn.cafuc.flyeat.sb.dormitorymanagement.mapper;

import cn.cafuc.flyeat.sb.dormitorymanagement.model.Instructor;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InstructorMapper {
    int insert(Instructor record);
    Instructor getById(int id);
    int insertSelective(Instructor record);
    Instructor getByName(String name);
    Instructor getByIdNum(String num);
}