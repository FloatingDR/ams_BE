package cn.cafuc.flyeat.sb.dormitorymanagement.Handler.factory;

import cn.cafuc.flyeat.sb.dormitorymanagement.Handler.Impl.*;
import cn.cafuc.flyeat.sb.dormitorymanagement.Handler.Import;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ImportFactory {
    @Resource
    BasicImport basic;
    @Resource
    InstructorImport instructorImport;
    @Resource
    SupervisorImport supervisorImport;
    @Resource
    StuImport stu;

    public Import getImportType(String type){
        switch (type){
            case "学生信息":return stu;
            case "宿管信息":return supervisorImport;
            case "辅导员信息":return instructorImport;//后更名为教师信息
            case "全部导入":return basic;
        }
        return null;
    }
}
