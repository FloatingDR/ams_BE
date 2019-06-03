package cn.cafuc.flyeat.sb.dormitorymanagement.mapper;

import cn.cafuc.flyeat.sb.dormitorymanagement.model.Building;
import cn.cafuc.flyeat.sb.dormitorymanagement.model.Supervisor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BuildingMapper {
    int insert(Building record);
    Building getInfoById(Integer BuildingId);
    List<Integer> getBuildingId();
    List<String> getBuildingName();
    int insertSelective(Building record);
    int getId(String num);
    Supervisor getSupervisor(int supervisorId);
    Building getBuilding(String num);
    int deleteWorker(int id);
    int setWorker(String num,int workerId);
    String workerContact(int workerId);
    String studentContact(int stuId);
}