package cn.cafuc.flyeat.sb.dormitorymanagement.mapper;

import cn.cafuc.flyeat.sb.dormitorymanagement.model.Bunk;
import cn.cafuc.flyeat.sb.dormitorymanagement.model.Room;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoomMapper {
    int insert(Room record);
    List<Room> getRoomByBuildingId(Integer BuildingId);
    Room getRoomIdByNum(@Param("buildId") int buildId,@Param("roomNum") String roomNum);
    int insertSelective(Room record);
    int getId(int buildId,String num);
    List<Room> getRoomId(int buildingId,int floorNum);
    Room getById(int id);
}