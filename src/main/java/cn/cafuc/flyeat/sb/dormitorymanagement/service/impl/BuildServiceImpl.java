package cn.cafuc.flyeat.sb.dormitorymanagement.service.impl;

import cn.cafuc.flyeat.sb.dormitorymanagement.mapper.BuildingMapper;
import cn.cafuc.flyeat.sb.dormitorymanagement.model.Building;
import cn.cafuc.flyeat.sb.dormitorymanagement.model.Supervisor;
import cn.cafuc.flyeat.sb.dormitorymanagement.service.BuildService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BuildServiceImpl implements BuildService {
    @Resource
    BuildingMapper buildingMapper;

    @Override
    public int insert(Building record) {
        return buildingMapper.insert(record);
    }

    public Building getInfoById(Integer BuildingId){

        return buildingMapper.getInfoById(BuildingId);
    }


    @Override
    public List<String> getBuildingName() {
        return buildingMapper.getBuildingName();
    }

    @Override
    public List<Integer> getBuildingId() {
        return buildingMapper.getBuildingId();
    }

    @Override
    public Building getBuilding(String num) {
        return buildingMapper.getBuilding(num);
    }

    @Transactional
    @Override
    public int deleteWorker(int id) {
        return buildingMapper.deleteWorker(id);
    }

    @Transactional
    @Override
    public int setWorker(String num, int workerId) {
        return buildingMapper.setWorker(num,workerId);
    }

    @Override
    public Supervisor getSupervisor(int supervisorId) {
         if(buildingMapper.getSupervisor(supervisorId)==null){return null;}
         else return buildingMapper.getSupervisor(supervisorId);
    }

    @Override
    public String studentContact(int stuId) {
        return buildingMapper.studentContact(stuId);
    }

    @Override
    public String workerContact(int workerId) {
        return buildingMapper.workerContact(workerId);
    }

    @Override
    public int getId(String num) {
        return buildingMapper.getId(num);
    }
}
