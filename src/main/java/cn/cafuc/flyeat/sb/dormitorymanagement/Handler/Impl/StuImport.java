package cn.cafuc.flyeat.sb.dormitorymanagement.Handler.Impl;

import cn.cafuc.flyeat.sb.dormitorymanagement.Bean.ResponseBean;
import cn.cafuc.flyeat.sb.dormitorymanagement.Handler.Impl.tool.Tools;
import cn.cafuc.flyeat.sb.dormitorymanagement.Handler.Import;
import cn.cafuc.flyeat.sb.dormitorymanagement.model.*;
import cn.cafuc.flyeat.sb.dormitorymanagement.service.*;
import com.zaxxer.hikari.util.SuspendResumeLock;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.Null;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class StuImport implements Import {
    @Resource
    BuildService buildService;
    @Resource
    RoomService roomService;
    @Resource
    BunkService bunkService;
    @Resource
    InstructorService instructorService;
    @Resource
    StudentService studentService;


    @Override
    public ResponseBean importInfo(String filePath) throws IOException {
        FileInputStream fileInputStream=new FileInputStream(new File(filePath));
        XSSFWorkbook workbook=new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet=workbook.getSheetAt(0);
        List<XSSFRow> errorRow=new ArrayList<>();
        try {
            int flag=1;
            for(int i=1;i<=sheet.getLastRowNum();i++){
                Student student=new Student();
                flag=1;
                XSSFRow row=sheet.getRow(i);
                if(row==null)break;
                String buildNum= Tools.buildHandle(Tools.handleDouble(row.getCell(3).toString()));
                String roomNum=Tools.roomHandle(row.getCell(4).toString());
                String bunkNum=Tools.bunkHandle(row.getCell(5).toString());
                Building building=null;
                Room room=null;
                Bunk bunk=null;
                if(buildService.getBuilding(buildNum)!=null){
                    building=buildService.getBuilding(buildNum);
                    if(roomService.getRoomIdByNum(building.getBuildingId(),roomNum)!=null){
                        room=roomService.getRoomIdByNum(building.getBuildingId(),roomNum);
                        if(bunkService.getBunk(room.getRoomId(),bunkNum)!=null){
                            bunk=bunkService.getBunk(room.getRoomId(),bunkNum);
                            if(bunk.getIsEmptyBunk()==false){
                                String errofInfo="此床有人";
                                row.createCell(16).setCellValue(errofInfo);
                                errorRow.add(row);
                                flag=0;
                            }else{
                                if(setInfo(row,student,bunk.getBunkId(),errorRow)==false){
                                    flag=0;
                                }
                            }
                        }else{
                            String errofInfo="床位号错误";
                            row.createCell(16).setCellValue(errofInfo);
                            errorRow.add(row);
                            flag=0;
                        }
                    }else {
                        String errofInfo="房间号错误";
                        row.createCell(16).setCellValue(errofInfo);
                        errorRow.add(row);
                        flag=0;
                    }
                }else{
                    String errofInfo="楼栋号错误";
                    row.createCell(16).setCellValue(errofInfo);
                    errorRow.add(row);
                    flag=0;
                }
                if(flag==1){
                    bunkService.add(bunk.getBunkId());
                    studentService.insert(student);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
       if(errorRow.size()==0)return new ResponseBean(200,"success",null);
       else return new ResponseBean(400,"fault",errorRow);
    }

    private boolean setInfo(XSSFRow row,Student student,Integer bunkId,List<XSSFRow> errorRow){
       try {
           if(Tools.judgeName(row.getCell(0).toString())==false){
               String errofInfo="姓名不规范";
               row.createCell(16).setCellValue(errofInfo);
               errorRow.add(row);
               return false;
           }
           student.setName(row.getCell(0).toString());
           if(row.getCell(1).toString().replaceAll(" ","").length()==0){
               String errofInfo="学号不能为空";
               row.createCell(16).setCellValue(errofInfo);
               errorRow.add(row);
               return false;
           }
           student.setStudentNumber(Tools.handleDouble(row.getCell(1).toString()));
           if(studentService.getByNum(student.getStudentNumber())!=null){
               String errofInfo="学号重复";
               row.createCell(16).setCellValue(errofInfo);
               errorRow.add(row);
               return false;
           }
           String genger=row.getCell(2).toString();
           if(genger.equals("男"))
               student.setGender(true);
           else
               student.setGender(false);
           student.setBunkId(bunkId);
           student.setSchool(row.getCell(6).toString());
           student.setMajor(row.getCell(7).toString());
           student.setGrade(Tools.handleDouble(row.getCell(8).toString()));
           student.setStudentClass(Tools.handleDouble(row.getCell(9).toString()));
           student.setNation(row.getCell(10).toString());
           student.setPlace(row.getCell(11).toString());
           student.setTel(Tools.handleDouble(row.getCell(12).toString()));
           student.setStatus(row.getCell(13).toString());
           student.setIdentifyNumber(row.getCell(14).toString());
           if(instructorService.getByName(row.getCell(15).toString())==null)return true;
           Instructor instructor=instructorService.getByName(row.getCell(15).toString());
           if(instructor!=null)student.setInstructorId(instructor.getInstructorId());
       }catch (Exception e){
           e.printStackTrace();
       }
        return true;
    }
}
