package cn.cafuc.flyeat.sb.dormitorymanagement.controller;

import cn.cafuc.flyeat.sb.dormitorymanagement.Bean.ResponseBean;
import cn.cafuc.flyeat.sb.dormitorymanagement.model.*;
import cn.cafuc.flyeat.sb.dormitorymanagement.service.BuildService;
import cn.cafuc.flyeat.sb.dormitorymanagement.service.BunkService;
import cn.cafuc.flyeat.sb.dormitorymanagement.service.RoomService;
import cn.cafuc.flyeat.sb.dormitorymanagement.service.StudentService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
//import com.sun.org.apache.bcel.internal.generic.FieldOrMethod;
//import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/student")
@CrossOrigin
@Transactional
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private BuildService buildService;
    @Autowired
    private BunkService bunkService;

    /*获取所有学生信息*/
    @GetMapping("/majorget")
    public ResponseBean getStudentByMajor(String major,int pageNum,int pageSize){
        List<Student> info;
        PageHelper.startPage(pageNum,pageSize);
        info=studentService.selectByMajor(major);
        addInfo(info);
        PageInfo<Student> pageInfo=new PageInfo<>(info);
        return new ResponseBean(200,"success",pageInfo);

    }

    @GetMapping("/info")
    public ResponseBean getStudentInfoAll(int pageNum,int pageSize){
        List<Student> info;
        PageHelper.startPage(pageNum,pageSize);
        info=studentService.getAll();
        addInfo(info);
        PageInfo<Student> pageInfo=new PageInfo<>(info);
        return new ResponseBean(200,"success",pageInfo);
    }
    @GetMapping("/school")
    public ResponseBean getStudentBySchool(String school,int pageNum,int pageSize){
        List<Student> info;
        PageHelper.startPage(pageNum,pageSize);
        info=studentService.selectBySchool(school);
        addInfo(info);
        PageInfo<Student> pageInfo=new PageInfo<>(info);
        return new ResponseBean(200,"success",pageInfo);
    }

    @GetMapping("/buildinfo")
        public ResponseBean buildinfo(String buildingNum){
        Building building=buildService.getBuilding(buildingNum);
        System.out.println(building);
        List<Room> rooms = roomService.getRoomByBuildingId(building.getBuildingId());
        List<Student> students=new ArrayList<Student>();
        for(Room r:rooms){
            List<Bunk> bunks=bunkService.getByRoom(r.getRoomId());
            for(Bunk b:bunks){
                Student student=studentService.getByBunk(b.getBunkId());
                student.setRoomNo(r.getRoomNumber());
                student.setBunkNo(b.getBunkNumber());
                students.add(student);
            }
        }

        return new ResponseBean(200,"success",students);
    }

    /*通过床位号删除一个学生信息*/
    @GetMapping("/delete/one")
    public ResponseBean deleteOne(Integer bunkId){
        try {
            studentService.deleteByBunkId(bunkId);
        }
        catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseBean(404,"fault",null);
        }
        return new ResponseBean(200,"succeess",null);
    }
    @GetMapping("/getbybunk")
    public ResponseBean getNameByBunkId(int bunkId){
        Student student=studentService.getByBunk(bunkId);
        if(student==null)return new ResponseBean(404,"fault",null);
        return new ResponseBean(200,"success",student);
    }
    /*获得所有学生专业*/
    @GetMapping("/major")
    public ResponseBean getMajor(){
       return new ResponseBean(200,"success",studentService.getMajor());
    }

    /*微调学生信息*/
    @PostMapping("/adjustStuInfo")
    public ResponseBean adjustStudentInfo(@RequestBody TempStudent student){
       try{
           //System.out.print(student);
           Student currStu=studentService.getByNum(student.getStudentNum());
           int buildId=buildService.getId(student.getBuildingNum());
           Room room=roomService.getRoomIdByNum(buildId,student.getRoomNum());
           if(bunkService.getBunk(room.getRoomId(),student.getBunkNum())!=null){
               Bunk bunk = bunkService.getBunk(room.getRoomId(),student.getBunkNum());
               if(bunk.getIsEmptyBunk()==true) {
                   //bunkService.empty(currStu.getBunkId());
                   bunkService.emptyByBunk(currStu.getBunkId());
                   bunkService.add(bunk.getBunkId());
                   currStu.setBunkId(bunk.getBunkId());
                   studentService.update(currStu.getStudentId(),bunk.getBunkId());
               }else{
                   return new ResponseBean(400,"bunk has somebody!",null);
               }
           }else {
               return new ResponseBean(401,"bunk is null",null);
           }
       }catch (Exception e){
           e.printStackTrace();
       }
        return new ResponseBean(200,"success",null);
    }

    @PostMapping("/exchange")
    public ResponseBean exchange(@RequestBody Student[] student){
        Student student1=student[0];
        Student student2=student[1];
        System.out.println(student1+" "+student2);
        int id=student1.getBunkId();
        studentService.update(student1.getStudentId(),student2.getBunkId());
        studentService.update(student2.getStudentId(),id);
        return new ResponseBean(200,"success",null);
    }
    @GetMapping("/getstudent")
    public ResponseBean getStudent(String studentNum){
        Student student;
        if(studentService.getByNum(studentNum)!=null){
            student=studentService.getByNum(studentNum);
            return new ResponseBean(200,"success",student);
        }else {
            return new ResponseBean(400,"fault",null);
        }

    }
    private void addInfo(List<Student> info){
        for (int i=0;i<info.size();i++){
            boolean gender=info.get(i).getGender();
            if(gender==true){
                info.get(i).setSex("男");
            }else {
                info.get(i).setSex("女");
            }
            Bunk bunk=bunkService.getById(info.get(i).getBunkId());
            info.get(i).setBunkNo(bunk.getBunkNumber());
            Room room=roomService.getById(bunk.getRoomId());
            info.get(i).setRoomNo(room.getRoomNumber());
            Building building= buildService.getInfoById(room.getBuildingId());
            info.get(i).setBuild(building.getBuildingNumber());
        }
    }
}
