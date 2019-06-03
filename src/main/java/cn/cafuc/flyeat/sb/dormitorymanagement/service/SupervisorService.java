package cn.cafuc.flyeat.sb.dormitorymanagement.service;

import cn.cafuc.flyeat.sb.dormitorymanagement.model.Supervisor;

import java.util.List;

public interface SupervisorService {
    List<Supervisor> getAll();
    int insert(Supervisor record);
    Supervisor get(String num);
}
