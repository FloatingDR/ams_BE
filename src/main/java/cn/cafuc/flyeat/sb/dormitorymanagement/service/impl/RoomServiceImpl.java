package cn.cafuc.flyeat.sb.dormitorymanagement.service.impl;

import cn.cafuc.flyeat.sb.dormitorymanagement.mapper.RoomMapper;
import cn.cafuc.flyeat.sb.dormitorymanagement.model.Room;
import cn.cafuc.flyeat.sb.dormitorymanagement.service.RoomService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    @Resource
    RoomMapper roomMapper;

    @Override
    public Room getRoomIdByNum(int buildId, String roomNum) {
        return roomMapper.getRoomIdByNum(buildId,roomNum);
    }

    @Override
    public Room getById(int id) {
        return roomMapper.getById(id);
    }

    @Override
    public List<Room> getRoomId(int buildingId, int floorNum) {
        return roomMapper.getRoomId(buildingId,floorNum);
    }

    @Override
    public int insert(Room record) {
        return roomMapper.insert(record);
    }

    public List<Room> getRoomByBuildingId(Integer BuildingId) {
        return roomMapper.getRoomByBuildingId(BuildingId);
    }

    @Override
    public int getId(String num, int buildId) {
        return roomMapper.getId(buildId, num);
    }


}