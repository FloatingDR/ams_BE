package cn.cafuc.flyeat.sb.dormitorymanagement.service.impl;

import cn.cafuc.flyeat.sb.dormitorymanagement.mapper.UserMapper;
import cn.cafuc.flyeat.sb.dormitorymanagement.model.User;
import cn.cafuc.flyeat.sb.dormitorymanagement.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;

    @Transactional
    @Override
    public int changePass(String name, String pass) {
        return userMapper.changePass(name,pass);
    }

    @Transactional
    @Override
    public int insert(User record) {
        return userMapper.insert(record);
        //return userMapper.insert(record);
    }

    @Override
    public User selectByAccount(String account) {
        return userMapper.selectByAccount(account);
    }
}
