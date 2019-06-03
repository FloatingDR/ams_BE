package cn.cafuc.flyeat.sb.dormitorymanagement.controller;

import cn.cafuc.flyeat.sb.dormitorymanagement.Bean.ResponseBean;
import cn.cafuc.flyeat.sb.dormitorymanagement.model.Supervisor;
import cn.cafuc.flyeat.sb.dormitorymanagement.service.BuildService;
import cn.cafuc.flyeat.sb.dormitorymanagement.service.SupervisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/workerinfo")
@Transactional
public class WorkerController {
    @Autowired
    private SupervisorService supervisorService;
    @Autowired
    private BuildService buildService;

    @Value("${Basic}")
    private String basic;

    /*获得所有宿管信息*/
    @GetMapping("/supervisor/all")
    public ResponseBean getSupervisorAll(){
        List<Supervisor> info;
        try {
            info= supervisorService.getAll();
            for(int i=0;i<info.size();i++){
                info.get(i).setWatchSpan(buildService.workerContact(info.get(i).getSupervisorId()));
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseBean(404,"fault",null);
        }
        return new ResponseBean(200,"success",info);
    }

    /*更改宿管工作范围*/
    @PostMapping("/adjust")
    public ResponseBean adjustSupervisor(String workerNum,String buildNum)throws Exception{
        Supervisor supervisor=supervisorService.get(workerNum);
        buildService.deleteWorker(supervisor.getSupervisorId());
        buildService.setWorker(buildNum,supervisor.getSupervisorId());
        return new ResponseBean(200,"success",null);
    }


}
