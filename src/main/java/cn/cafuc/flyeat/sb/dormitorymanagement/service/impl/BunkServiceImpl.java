package cn.cafuc.flyeat.sb.dormitorymanagement.service.impl;

import cn.cafuc.flyeat.sb.dormitorymanagement.mapper.BunkMapper;
import cn.cafuc.flyeat.sb.dormitorymanagement.model.Bunk;
import cn.cafuc.flyeat.sb.dormitorymanagement.service.BunkService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
@Service
public class BunkServiceImpl implements BunkService {
    @Resource
    BunkMapper bunkMapper;

    @Override
    public int getByRoomId(int roomId,String bunkNum) {
       return bunkMapper.getByRoomId(roomId,bunkNum);
    }

    @Override
    public int insert(Bunk record) {
        return bunkMapper.insert(record);
    }

    @Override
    public List<Bunk> getByRoom(int roomId) {
        return bunkMapper.getByRoom(roomId);
    }

    @Transactional
    @Override
    public int add(int id) {
        return bunkMapper.add(id);
    }

    @Override
    public Bunk getBunk(int roomId, String bunkNum) {
        return bunkMapper.getBunk(roomId,bunkNum);
    }

    @Override
    public Bunk getById(int id) {
        return bunkMapper.getById(id);
    }

    @Override
    public int emptyByBunk(int id) {
        return bunkMapper.emptyByBunk(id);
    }

    @Transactional
    @Override
    public int empty(int roomId) {
        return bunkMapper.empty(roomId);
    }
}
