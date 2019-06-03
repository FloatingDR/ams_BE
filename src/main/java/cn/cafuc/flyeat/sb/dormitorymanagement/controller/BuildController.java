package cn.cafuc.flyeat.sb.dormitorymanagement.controller;

import cn.cafuc.flyeat.sb.dormitorymanagement.Bean.ResponseBean;
import cn.cafuc.flyeat.sb.dormitorymanagement.model.Building;
import cn.cafuc.flyeat.sb.dormitorymanagement.model.Room;
import cn.cafuc.flyeat.sb.dormitorymanagement.service.BuildService;
import cn.cafuc.flyeat.sb.dormitorymanagement.service.BunkService;
import cn.cafuc.flyeat.sb.dormitorymanagement.service.RoomService;
import cn.cafuc.flyeat.sb.dormitorymanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin

@RequestMapping("/building")
public class BuildController {
    @Autowired
    private RoomService roomService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private BuildService buildService;
    @Autowired
    private BunkService bunkService;

    /*通过楼栋号清空该楼栋住宿信息*/
    @PostMapping("/empty")
    public ResponseBean emptyBuiling(@RequestParam(value = "BuildingId") List<Integer> BuildingIds){
        if(BuildingIds.isEmpty())return new ResponseBean(400,"PramIsNull",null);
        try {
            for(Integer BuildingId:BuildingIds){
                List<Room> rooms=roomService.getRoomByBuildingId(BuildingId);
                for (Room room: rooms) {
                    studentService.deleteByRoomId(room.getRoomId());
                    bunkService.empty(room.getRoomId());
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseBean(404,"fault",null);
        }
        return new ResponseBean(200,"success",null);
    }
    /*清空某栋楼某楼层住宿信息*/
    @GetMapping("/emptyFloor")
    public ResponseBean emptyFloor(int buildingId,int floorNum){
       try {
           List<Room> rooms= roomService.getRoomId(buildingId,floorNum);
           for(Room room:rooms){
               bunkService.empty(room.getRoomId());
               studentService.deleteByRoomId(room.getRoomId());
           }
       }catch (Exception e){
           e.printStackTrace();
           return new ResponseBean(400,"falut",null);
       }
        return  new ResponseBean(200,"success",null);
    }

    /*通过楼栋id 查询楼栋信息*/
    @GetMapping("/info")
    public ResponseBean getBuildingInfo(Integer BuildingId){
        if(BuildingId==null)return new ResponseBean(400,"PramIsNull",null);
        Building building;
        try {
            building= buildService.getInfoById(BuildingId);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseBean(404,"fault",null);
        }
        return new ResponseBean(200,"success",building);
    }
    /*获取所有楼栋编号*/
    @GetMapping("/getid")
    public ResponseBean getId(){
        List<Integer> buildingId;
        try{
            buildingId= buildService.getBuildingId();
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseBean(404,"fault",null);
        }
        return new ResponseBean(200,"success",buildingId);
    }
    /*获取所有楼栋名称
    *   eg：A栋
    * */
    @GetMapping("/getname")
    public ResponseBean getcharName(){
        List<String> buildingName;
        try{
            buildingName= buildService.getBuildingName();
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseBean(404,"fault",null);
        }
        return new ResponseBean(200,"success",buildingName);
    }

    @GetMapping("/nametoid")
    public ResponseBean getIdByName(@RequestParam("num") String buildingNumber){
        //System.out.println(buildingNumber);
        return new ResponseBean(200,"success",buildService.getId(buildingNumber));
    }
    @GetMapping("/getallroom")
    public ResponseBean getRoomInfo(int buildingId){
        return new ResponseBean(200,"success",roomService.getRoomByBuildingId(buildingId));
    }
}
