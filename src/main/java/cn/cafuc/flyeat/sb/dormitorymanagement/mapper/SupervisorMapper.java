package cn.cafuc.flyeat.sb.dormitorymanagement.mapper;

import cn.cafuc.flyeat.sb.dormitorymanagement.model.Supervisor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SupervisorMapper {
    int insert(Supervisor record);
    List<Supervisor> getAll();
    int insertSelective(Supervisor record);
    Supervisor get(String num);
}