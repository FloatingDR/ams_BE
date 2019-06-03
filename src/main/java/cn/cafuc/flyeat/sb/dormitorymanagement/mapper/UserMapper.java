package cn.cafuc.flyeat.sb.dormitorymanagement.mapper;

import cn.cafuc.flyeat.sb.dormitorymanagement.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int insert(User record);
    User selectByAccount(String account);
    int changePass(String name,String pass);
}
