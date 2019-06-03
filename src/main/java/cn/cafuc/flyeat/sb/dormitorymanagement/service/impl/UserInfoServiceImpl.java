package cn.cafuc.flyeat.sb.dormitorymanagement.service.impl;

import cn.cafuc.flyeat.sb.dormitorymanagement.mapper.UserInfoMapper;
import cn.cafuc.flyeat.sb.dormitorymanagement.model.UserInfo;
import cn.cafuc.flyeat.sb.dormitorymanagement.service.UserInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    UserInfoMapper userInfoMapper;
    @Transactional
    @Override
    public int insert(UserInfo record) {
        return userInfoMapper.insert(record);
    }
}
