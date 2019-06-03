package cn.cafuc.flyeat.sb.dormitorymanagement.Handler.factory;

import cn.cafuc.flyeat.sb.dormitorymanagement.Handler.ErrorHander;
import cn.cafuc.flyeat.sb.dormitorymanagement.Handler.Impl.BasicErrorDate;
import cn.cafuc.flyeat.sb.dormitorymanagement.Handler.Impl.InsErrorDate;
import cn.cafuc.flyeat.sb.dormitorymanagement.Handler.Impl.StudentErrorDate;
import cn.cafuc.flyeat.sb.dormitorymanagement.Handler.Impl.SuperErrorDate;
import org.springframework.stereotype.Component;


import javax.annotation.Resource;

@Component
public class ErrorHanderFactory {
    @Resource
    StudentErrorDate studentErrorDate;
    @Resource
    InsErrorDate insErrorDate;
    @Resource
    SuperErrorDate superErrorDate;
    @Resource
    BasicErrorDate basicErrorDate;

    public ErrorHander ErrorDateFactory(String type){
        switch (type){
            case "学生信息":return studentErrorDate;
            case "辅导员信息":return insErrorDate;//后更名为教师信息
            case "宿管信息":return superErrorDate;
            case "全部导入":return basicErrorDate;
        }
        return null;
    }
}
