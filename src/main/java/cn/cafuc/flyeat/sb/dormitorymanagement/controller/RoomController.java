package cn.cafuc.flyeat.sb.dormitorymanagement.controller;

import cn.cafuc.flyeat.sb.dormitorymanagement.Bean.ResponseBean;

import cn.cafuc.flyeat.sb.dormitorymanagement.model.Bunk;
import cn.cafuc.flyeat.sb.dormitorymanagement.model.Room;
import cn.cafuc.flyeat.sb.dormitorymanagement.service.BuildService;
import cn.cafuc.flyeat.sb.dormitorymanagement.service.BunkService;
import cn.cafuc.flyeat.sb.dormitorymanagement.service.RoomService;
import cn.cafuc.flyeat.sb.dormitorymanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/room")
public class RoomController {

    @Autowired
    BuildService buildService;
    @Autowired
    RoomService roomService;
    @Autowired
    BunkService bunkService;
    @Autowired
    StudentService studentService;

    @PostMapping("/empty")
    public ResponseBean emptyRoom(@RequestParam("roomIds") List<Integer> roomIds){
        if(roomIds.isEmpty())return new ResponseBean(400,"fault",null);
        try{
            for(int id:roomIds){
                bunkService.empty(id);
                studentService.deleteByRoomId(id);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseBean(400,"fault",null);
        }
        return new ResponseBean(200,"success",null);
    }


    @GetMapping("/getroom")
    public ResponseBean getRoomId(int buildingId,String roomNumber){
        int roomId=roomService.getId(roomNumber,buildingId);
        if(roomId==-1)return new ResponseBean(400,"PramIsUseless",null);
        return new ResponseBean(200,"success",roomId);
    }
    @GetMapping("/bunkinfo")
    public ResponseBean getBunkInfo(String buildingNumber, String roomNumber){
        int buildingId=buildService.getId(buildingNumber);
        if(buildingId==-1)new ResponseBean(400,"PramIsUseless",null);

        int roomId=roomService.getId(roomNumber,buildingId);
        if(roomId==-1)return new ResponseBean(400,"PramIsUseless",null);

        List<Bunk> bunks=bunkService.getByRoom(roomId);

        return new ResponseBean(200,"success",bunkService.getByRoom(roomId));
    }
    @GetMapping("/bunkid")
    public ResponseBean getBunkIdByRomm(int roomId,String bunkNum){
        int bunkId=bunkService.getByRoomId(roomId,bunkNum);
        if(bunkId==-1)return new ResponseBean(400,"PramIsUseless",null);
        return new ResponseBean(200,"success",bunkId);
    }

    //获取某楼栋的空房间和空床位信息
    @GetMapping("/emptyinfo/bunk")
    public ResponseBean emptyInfo(int buildingId){
        //System.out.print(buildingId);
        List<Room> hasEmpty=new ArrayList<Room>();
        List<Room> rooms=roomService.getRoomByBuildingId(buildingId);
        for(Room x:rooms){
            x.setEmptyBunk(new ArrayList<Bunk>());
            List<Bunk> bunks=bunkService.getByRoom(x.getRoomId());
            for(Bunk y:bunks){
                if(y.getIsEmptyBunk()==true){
                    x.getEmptyBunk().add(y);
                }
            }
            if(x.getEmptyBunk().size()!=0){
                x.setEmptyBunkNum(x.getEmptyBunk().size());
                hasEmpty.add(x);
            }
        }
//        for(Room z:hasEmpty){
//            System.out.println(z);
//        }
        return new ResponseBean(200,"success",hasEmpty);
    }
}
