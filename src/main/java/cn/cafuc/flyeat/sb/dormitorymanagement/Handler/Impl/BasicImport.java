package cn.cafuc.flyeat.sb.dormitorymanagement.Handler.Impl;

import cn.cafuc.flyeat.sb.dormitorymanagement.Bean.ResponseBean;
import cn.cafuc.flyeat.sb.dormitorymanagement.Handler.Impl.tool.Tools;
import cn.cafuc.flyeat.sb.dormitorymanagement.Handler.Import;

import cn.cafuc.flyeat.sb.dormitorymanagement.model.*;
import cn.cafuc.flyeat.sb.dormitorymanagement.service.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.tools.Tool;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class BasicImport implements Import {
    @Autowired
    SupervisorService supervisorService;
    @Autowired
    InstructorService instructorService;
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    BuildService buildService;
    @Autowired
    RoomService roomService;
    @Autowired
    BunkService bunkService;
    @Autowired
    StudentService studentService;
    @Autowired
    UserService userService;

    @Override
    public ResponseBean importInfo(String filePath) throws IOException {
        List<XSSFRow> errorRow=new ArrayList<XSSFRow>();
        try {
            FileInputStream file=new FileInputStream(new File(filePath));
            XSSFWorkbook workbook=new XSSFWorkbook(file);
            createBuilding(workbook,errorRow);
//            inserStudentInfo(workbook,errorRow);
            insertInstructor(workbook,errorRow);
            insertSuperviosr(workbook,errorRow);
            //insertUser(workbook,errorRow);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseBean(403,"error",null);
        }
        System.out.print(errorRow.size());
        return new ResponseBean(400, "maybe", errorRow);
    }
    private void createBuilding(XSSFWorkbook workbook,List<XSSFRow> errorRow)throws Exception{
        XSSFSheet sheet=workbook.getSheetAt(0);
        XSSFRow row=null;
        errorRow.add(createStuTableHead());
        try {
            for(int i=1;i<=sheet.getLastRowNum();i++){
                row=sheet.getRow(i);
                if(row==null)continue;
                if(row.getCell(11)==null||row.getCell(0)==null){
                    errorRow.add(row);
                    continue;
                }
                String buildNum= Tools.buildHandle(Tools.handleDouble(row.getCell(11).toString()));
                if(buildNum==""||buildNum==null){
                    String errofInfo="信息系统中没有该楼栋";
                    row.createCell(15).setCellValue(errofInfo);
                    errorRow.add(row);
                }
                else{
                    Building building=new Building();
                    String gender=row.getCell(2).toString();
                    if(gender.equals("男")){
                        building.setGenderProperty(true);
                    }else{
                        building.setGenderProperty(false);
                    }
                    if(buildService.getBuilding(buildNum)==null){//如果楼栋不存在 新建楼栋再创建房间信息
                        building.setBuildingNumber(buildNum);
                        buildService.insert(building);
                        building=buildService.getBuilding(buildNum);
                        createRoom(building,row,errorRow);
                    }else{ //如果楼栋存在，创建房间信息
                        building=buildService.getBuilding(buildNum);
                        createRoom(building,row,errorRow);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void createRoom(Building building,XSSFRow row,List<XSSFRow> errorRow){
        String roomNum=Tools.roomHandle(Tools.handleDouble(row.getCell(13).toString()));
        Room room=new Room();
        if(roomNum==null||roomNum==""){
            String errofInfo="房间号不能为空";
            row.createCell(15).setCellValue(errofInfo);
            errorRow.add(row);
        }
        else {
            try {
                if(roomService.getRoomIdByNum(building.getBuildingId(),roomNum)==null){//如果房间不存在，新建房间
                    room.setBuildingId(building.getBuildingId());
                    if(Tools.floorHandle(Tools.handleDouble(row.getCell(12).toString()))!=null){
                        Integer floorNum=new Integer(Tools.floorHandle(Tools.handleDouble(row.getCell(12).toString())));
                        room.setFloorNumber(floorNum);
                    }else {
                        throw new Exception("楼层数错误");
                    }
                    room.setRoomNumber(roomNum);
                    roomService.insert(room);
                    room=roomService.getRoomIdByNum(building.getBuildingId(),roomNum);
                    createBunk(room,row,errorRow);
                }else {
                    room=roomService.getRoomIdByNum(building.getBuildingId(),roomNum);
                    createBunk(room,row,errorRow);
                }
            }catch (Exception e){
                String errofInfo="信息错误";
                row.createCell(15).setCellValue(errofInfo);
                errorRow.add(row);
                //e.printStackTrace();
            }
        }

    }
    private void createBunk(Room room,XSSFRow row,List<XSSFRow> errorRow){
        String bunkNum=Tools.bunkHandle(Tools.handleDouble(row.getCell(14).toString()));
        if(bunkNum==null||bunkNum==""){
            String errofInfo="床位号不能为空";
            row.createCell(15).setCellValue(errofInfo);
            errorRow.add(row);
            return;
        }
        Bunk bunk = new Bunk();
        if(bunkService.getBunk(room.getRoomId(),bunkNum)==null){
            bunk.setIsEmptyBunk(true);
            bunk.setRoomId(room.getRoomId());
            bunk.setBunkNumber(bunkNum);
            bunkService.insert(bunk);
        }
    }

    private void inserStudentInfo(XSSFWorkbook workbook,List<XSSFRow> errorRow)throws Exception{
        try {
            XSSFSheet sheet=workbook.getSheet("学生信息");
            int num=sheet.getLastRowNum();
            for(int i=1;i<=num;i++){
                XSSFRow row=sheet.getRow(i);
                Student student=new Student();
                if(row==null)continue;
                if(row.getCell(0)==null){
                    /*String errofInfo="姓名不能为空";
                    row.createCell(15).setCellValue(errofInfo);
                    errorRow.add(row);
                    System.out.print(row.getCell(1).toString());*/
                    continue;
                }
                student.setName(row.getCell(0).toString().trim());
                if(Tools.judgeName(student.getName())==false){
                    String errorInfo="名字格式错误";
                    row.createCell(15).setCellValue(errorInfo);
                    errorRow.add(row);
                    continue;
                }
                if(row.getCell(1).toString().replaceAll(" ","")==""){
                    String errofInfo="学号不能为空";
                    row.createCell(15).setCellValue(errofInfo);
                    errorRow.add(row);
                    continue;
                }
                String stuNum=Tools.handleDouble(Tools.handleDouble(row.getCell(1).toString().trim()));
                student.setStudentNumber(stuNum);
                if(studentService.getByNum(stuNum)!=null){
                    String errofInfo="学号录入重复";
                    row.createCell(15).setCellValue(errofInfo);
                    errorRow.add(row);
                    continue;
                }
                String gender=row.getCell(2).toString().replace(" ","");
                if(gender.equals("男")){
                    student.setGender(true);
                }else{
                    student.setGender(false);
                }
                if(row.getCell(3)!=null)student.setSchool(row.getCell(3).toString().trim());//学院
                if(row.getCell(4)!=null)student.setMajor(row.getCell(4).toString().trim());//专业
                if(row.getCell(5)!=null)student.setGrade(row.getCell(5).toString().trim());//年级
                if(row.getCell(6)!=null)student.setStudentClass(row.getCell(6).toString().trim());//班级
                if(row.getCell(7)!=null)student.setNation(row.getCell(7).toString().trim());//民族
                if(row.getCell(8)!=null)student.setPlace(row.getCell(8).toString().trim());//籍贯
                if(row.getCell(9)!=null)student.setTel(Tools.handleDouble(row.getCell(9).toString()));//联系方式
                if(Tools.IDP(row.getCell(10).toString())!=null)
                    student.setIdentifyNumber(Tools.IDP(row.getCell(10).toString()));
                else {
                    String errofInfo="身份证信息错误";
                    row.createCell(15).setCellValue(errofInfo);
                    errorRow.add(row);
                    continue;
                }
                if(buildService.getBuilding(Tools.buildHandle(Tools.handleDouble(row.getCell(11).toString())))==null){
                    String errofInfo="楼栋信息错误";
                    row.createCell(15).setCellValue(errofInfo);
                    errorRow.add(row);
                    continue;
                }
                Building building=buildService.getBuilding(Tools.buildHandle(Tools.handleDouble(row.getCell(11).toString())));
                if(roomService.getRoomIdByNum(building.getBuildingId(),Tools.roomHandle(Tools.handleDouble(row.getCell(13).toString())))==null){
                    continue;
                }
                Room room=roomService.getRoomIdByNum(building.getBuildingId(),Tools.roomHandle(Tools.handleDouble(row.getCell(13).toString())));
                if(bunkService.getBunk(room.getRoomId(),Tools.bunkHandle(Tools.handleDouble(row.getCell(14).toString())))==null){
                    continue;
                }
                Bunk bunk=bunkService.getBunk(room.getRoomId(),Tools.bunkHandle(Tools.handleDouble(row.getCell(14).toString())));
                if(bunk.getIsEmptyBunk()==false){
                    Student temp=studentService.getByBunk(bunk.getBunkId());
                    String errofInfo=String.format("该房间已有人入住，住宿人: %s专业,%s %s ",temp.getMajor(),temp.getName(),temp.getStudentNumber());
                    row.createCell(15).setCellValue(errofInfo);
                    errorRow.add(row);
                    continue;
                }
                bunkService.add(bunk.getBunkId());
                student.setBunkId(bunk.getBunkId());
                studentService.insert(student);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private boolean insertInstructor(XSSFWorkbook workbook,List<XSSFRow> errorRow){
        errorRow.add(createInsTableHead());
        try{
           XSSFSheet sheet=workbook.getSheet("辅导员信息表");
           if(sheet.getRow(1)==null)return false;
           for(int i=1;i<=sheet.getLastRowNum();i++){
               XSSFRow row=sheet.getRow(i);
               Instructor instructor=new Instructor();
               if(row.getCell(0).toString().replaceAll(" ","").length()==0){
                   String errofInfo="名字不能为空";
                   row.createCell(9).setCellValue(errofInfo);
                   errorRow.add(row);
                   continue;
               }
               instructor.setName(row.getCell(0).toString().replaceAll(" ",""));
               if(Tools.judgeName(instructor.getName())==false){
                   String errofInfo="名字格式错误";
                   row.createCell(9).setCellValue(errofInfo);
                   errorRow.add(row);
                   continue;
               }
               if(row.getCell(1).toString().replaceAll(" ","").length()!=0){
                   instructor.setInstructorNumber(row.getCell(1).toString().trim().toUpperCase());
               }
               else{
                   String errofInfo="工号不能为空";
                   row.createCell(9).setCellValue(errofInfo);
                   errorRow.add(row);
                   continue;
               }
               if(instructorService.getByIdNum(instructor.getInstructorNumber())!=null){
                   String errofInfo="工号重复";
                   row.createCell(9).setCellValue(errofInfo);
                   errorRow.add(row);
                   continue;
               }
               String genger=row.getCell(2).toString();
               if(genger.equals("男")){
                   instructor.setGender(true);
               }else {
                   instructor.setGender(false);
               }
               if(Tools.IDP(row.getCell(3).toString())==null){
                   String errofInfo="身份证信息错误";
                   row.createCell(9).setCellValue(errofInfo);
                   errorRow.add(row);
                   continue;
               }
               instructor.setIdentifyNumber(Tools.IDP(row.getCell(3).toString()));
               instructor.setSchool(row.getCell(5).toString().trim());
               instructor.setMajor(row.getCell(6).toString().trim());
               instructor.setTel(Tools.handleDouble(row.getCell(4).toString()));
               instructorService.insert(instructor);
               User user=new User();
               user.setAccount(instructor.getIdentifyNumber());
               user.setPassword("6944edcd743bacc8297cdabe0130a604");
               user.setInstructorId(instructorService.getByIdNum(instructor.getInstructorNumber()).getInstructorId());
               userService.insert(user);
           }
       }catch (Exception e){
           //e.printStackTrace();
           return false;
       }
       return true;
    }
    private void insertSuperviosr(XSSFWorkbook workbook,List<XSSFRow> errorRow){
        errorRow.add(createSupTableHead());
        try{
            XSSFSheet sheet=workbook.getSheet("宿管信息表");
            for(int i=1;i<=sheet.getLastRowNum();i++){
                XSSFRow row=sheet.getRow(i);
                if(row==null)continue;
                Supervisor supervisor=new Supervisor();
                if(Tools.judgeName(row.getCell(0).toString())==false){
                    String errorInfo="名字信息错误";
                    row.createCell(8).setCellValue(errorInfo);
                    errorRow.add(row);
                    continue;
                }
                supervisor.setName(row.getCell(0).toString());
                if(row.getCell(2)==null||row.getCell(2).toString().replaceAll(" ","").length()==0){
                    String errorInfo="工号不能为空";
                    row.createCell(8).setCellValue(errorInfo);
                    errorRow.add(row);
                    continue;
                }

                supervisor.setSupervisorNumber(row.getCell(2).toString().trim().toUpperCase());
                if(supervisorService.get(supervisor.getSupervisorNumber())!=null){
                    String errorInfo="工号重复";
                    row.createCell(8).setCellValue(errorInfo);
                    errorRow.add(row);
                    continue;
                }
                String genger=row.getCell(3).toString().trim();
                if(genger.equals("男")){
                    supervisor.setGender(true);
                }else{
                    supervisor.setGender(false);
                }
                supervisor.setPlace(row.getCell(4).toString().trim());
                supervisor.setIdentifyNumber(Tools.IDP(row.getCell(5).toString().trim()));
                supervisor.setTel(Tools.handleDouble(row.getCell(6).toString().trim()));
                if(row.getCell(6).toString().replaceAll(" ","").length()==0){
                    String errorInfo="监管范围不能为空";
                    row.createCell(8).setCellValue(errorInfo);
                    errorRow.add(row);
                    continue;
                }

                String managerBuild=Tools.buildHandle(Tools.handleDouble(row.getCell(7).toString().trim()));
                if(managerBuild==null||managerBuild==""){
                    String errorInfo="监管楼栋在数据库中不存在";
                    row.createCell(8).setCellValue(errorInfo);
                    errorRow.add(row);
                    continue;
                }
                supervisorService.insert(supervisor);
                supervisor=supervisorService.get(supervisor.getSupervisorNumber());
                buildService.setWorker(managerBuild,supervisor.getSupervisorId());
                User user=new User();
                user.setAccount(supervisor.getIdentifyNumber());
                user.setAccount("6944edcd743bacc8297cdabe0130a604");
                user.setSupervisorId(supervisor.getSupervisorId());
                userService.insert(user);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void insertUser(XSSFWorkbook workbook,List<XSSFRow> errorRow){
        try {
            XSSFSheet sheet=workbook.getSheetAt(3);
            if(sheet.getRow(1)==null)return;
            for (int i=1;i<=sheet.getLastRowNum();i++){
                XSSFRow row=sheet.getRow(i);
                UserInfo userInfo=new UserInfo();
                userInfo.setName(row.getCell(0).toString().trim());
                userInfo.setIdentifyNumber(Tools.IDP(row.getCell(1).toString()));
                userInfo.setTel(Tools.handleDouble(row.getCell(2).toString()));
                userInfo.setOrganization(row.getCell(3).toString().trim());
                userInfo.setStatus(row.getCell(4).toString().trim());
                User user=new User();
                user.setAccount(userInfo.getIdentifyNumber());
                user.setAccount("6944edcd743bacc8297cdabe0130a604");
                userService.insert(user);
                userInfoService.insert(userInfo);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private XSSFRow createStuTableHead(){
        XSSFWorkbook workbook=new XSSFWorkbook();
        XSSFSheet sheet=workbook.createSheet("1");
        XSSFRow row=sheet.createRow(0);
        row.createCell(0).setCellValue("学生");
        row.createCell(1).setCellValue("学号");
        row.createCell(2).setCellValue("性别");
        row.createCell(3).setCellValue("学院");
        row.createCell(4).setCellValue("专业");
        row.createCell(5).setCellValue("年级");
        row.createCell(6).setCellValue("班级");
        row.createCell(7).setCellValue("民族");
        row.createCell(8).setCellValue("籍贯");
        row.createCell(9).setCellValue("电话");
        row.createCell(10).setCellValue("身份证");
        row.createCell(11).setCellValue("楼栋");
        row.createCell(12).setCellValue("楼层");
        row.createCell(13).setCellValue("房间号");
        row.createCell(14).setCellValue("床位号");
        row.createCell(15).setCellValue("错误信息");
        return row;
    }
    private XSSFRow createInsTableHead(){
        XSSFWorkbook workbook=new XSSFWorkbook();
        XSSFSheet sheet=workbook.createSheet("1");
        XSSFRow row=sheet.createRow(0);
        row.createCell(0).setCellValue("老师");
        row.createCell(1).setCellValue("工号");
        row.createCell(2).setCellValue("性别");
        row.createCell(3).setCellValue("身份证");
        row.createCell(4).setCellValue("电话");
        row.createCell(5).setCellValue("学院");
        row.createCell(6).setCellValue("专业");
        row.createCell(7).setCellValue("年级");
        row.createCell(8).setCellValue("班级");
        row.createCell(9).setCellValue("错误信息");
        return row;
    }
    private XSSFRow createSupTableHead(){
        XSSFWorkbook workbook=new XSSFWorkbook();
        XSSFSheet sheet=workbook.createSheet("1");
        XSSFRow row=sheet.createRow(0);
        row.createCell(0).setCellValue("宿管");
        row.createCell(1).setCellValue("身份");
        row.createCell(2).setCellValue("工号");
        row.createCell(3).setCellValue("性别");
        row.createCell(4).setCellValue("籍贯");
        row.createCell(5).setCellValue("身份证号");
        row.createCell(6).setCellValue("联系方式");
        row.createCell(7).setCellValue("管理楼栋");
        row.createCell(8).setCellValue("错误信息");
        return row;
    }
}
