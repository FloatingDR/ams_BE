package cn.cafuc.flyeat.sb.dormitorymanagement.service;

import cn.cafuc.flyeat.sb.dormitorymanagement.model.Building;
import cn.cafuc.flyeat.sb.dormitorymanagement.model.Supervisor;

import java.util.List;

public interface BuildService {

    int insert(Building record);
    Building getInfoById(Integer BuildingId);
    List<Integer> getBuildingId();
    int getId(String num);
    List<String> getBuildingName();
    Supervisor getSupervisor(int supervisorId);
    Building getBuilding(String num);
    int deleteWorker(int id);
    int setWorker(String num,int workerId);
    String workerContact(int workerId);
    String studentContact(int stuId);
}
