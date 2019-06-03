package cn.cafuc.flyeat.sb.dormitorymanagement.service;

import cn.cafuc.flyeat.sb.dormitorymanagement.model.Instructor;

public interface InstructorService {
    int insert(Instructor record);
    Instructor getById(int id);
    Instructor getByName(String name);
    Instructor getByIdNum(String num);
}
