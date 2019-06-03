package cn.cafuc.flyeat.sb.dormitorymanagement.service;

import cn.cafuc.flyeat.sb.dormitorymanagement.model.User;

public interface UserService {
    int insert(User record);
    User selectByAccount(String account);
    int changePass(String name,String pass);
}
