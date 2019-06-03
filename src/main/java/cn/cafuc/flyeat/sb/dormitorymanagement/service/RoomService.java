package cn.cafuc.flyeat.sb.dormitorymanagement.service;

import cn.cafuc.flyeat.sb.dormitorymanagement.model.Room;

import java.util.List;

public interface RoomService {
    int insert(Room record);
    List<Room> getRoomByBuildingId(Integer BuildingId);
    int getId(String num,int buildId);
    List<Room> getRoomId(int buildingId,int floorNum);
    Room getRoomIdByNum(int buildId,String roomNum);
    Room getById(int id);
}
