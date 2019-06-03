package cn.cafuc.flyeat.sb.dormitorymanagement.service;

import cn.cafuc.flyeat.sb.dormitorymanagement.model.Bunk;

import java.util.List;

public interface BunkService {
    int insert(Bunk record);
    List<Bunk> getByRoom(int roomId);
    int getByRoomId(int roomId,String bunkNum);
    int empty(int roomId);
    Bunk getBunk(int roomId,String bunkNum);
    int add(int id);
    Bunk getById(int id);
    int emptyByBunk(int id);
}
