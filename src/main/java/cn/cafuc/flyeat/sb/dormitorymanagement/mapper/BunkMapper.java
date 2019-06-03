package cn.cafuc.flyeat.sb.dormitorymanagement.mapper;

import cn.cafuc.flyeat.sb.dormitorymanagement.model.Bunk;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BunkMapper {
    int insert(Bunk record);
    List<Bunk> getByRoom(int roomId);
    int insertSelective(Bunk record);
    int getByRoomId(int roomId,String bunkNum);
    int empty(int roomId);
    Bunk getBunk(int roomId,String bunkNum);
    int add(int id);
    Bunk getById(int id);
    int emptyByBunk(int id);
}