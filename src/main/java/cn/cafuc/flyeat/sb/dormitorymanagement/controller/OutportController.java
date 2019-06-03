package cn.cafuc.flyeat.sb.dormitorymanagement.controller;

import cn.cafuc.flyeat.sb.dormitorymanagement.Bean.ResponseBean;
import cn.cafuc.flyeat.sb.dormitorymanagement.Handler.Impl.tool.Tools;
import cn.cafuc.flyeat.sb.dormitorymanagement.model.*;
import cn.cafuc.flyeat.sb.dormitorymanagement.service.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/upload")
public class OutportController {
    @Autowired
    BuildService buildService;
    @Autowired
    RoomService roomService;
    @Autowired
    SupervisorService supervisorService;
    @Autowired
    BunkService bunkService;
    @Autowired
    StudentService studentService;
    @Autowired
    InstructorService instructorService;

    public ResponseEntity<InputStreamResource> download(String filename, String destname) throws IOException {
        Resource resource = new ClassPathResource("static/" + filename);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");//每次请求都必须向服务器发送。
        //headers.add("Content-Disposition", String.format("attachment;filename=%s", URLEncoder.encode(destname, "utf-8")));
        headers.add("Content-Disposition", "attachment;filename="+ URLEncoder.encode(destname, "utf-8"));

        headers.add("Pragma", "no-cache");//Pragma:no-cache，跟Cache-Control: no-cache相同
        //但是Pragma: no-cache可以应用到http 1.0 和http 1.1，而Cache-Control: no-cache只能应用于http 1.1.
        headers.add("Expries", "0");//Expires 表示存在时间，允许客户端在这个时间之前不去检查（发请求）。
        headers.add("Content-Type","application/vnd.ms-excel;charset=utf-8");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/force-download"))
                .body(new InputStreamResource(resource.getInputStream()));

    }
    /*
    * 基表信息录入模块
    * */
    @GetMapping("/basic")
    public ResponseEntity<InputStreamResource> downloadBasic() throws IOException{
        return download("Basicinfo.xlsx","BasicInfoDemo.xlsx");
    }
    /*
     * 学生信息录入模板下载
     * */
    @GetMapping("/student")
    public ResponseEntity<InputStreamResource> downloadStudent() throws IOException{
        return download("studentDemo.xlsx","StudentInfoDemo.xlsx");
    }
    /*
    * 宿管信息录入模板下载
    * */
    @GetMapping("/superviosr")
    public ResponseEntity<InputStreamResource> downloadSuperviosr() throws IOException{
        return download("supervisorDemo.xlsx","SuperviosrInfoDemo.xlsx");
    }
    /*
    * 辅导员信息录入模板下载
    * */
    @GetMapping("/instructor")
    public ResponseEntity<InputStreamResource> downloadInstructor() throws IOException{
        return download("instructorDemo.xlsx","InstructorInfoDemo.xlsx");
    }
    /*
    * 公寓分配模板下载
    **/
    @GetMapping("/bm")
    public ResponseEntity<InputStreamResource> downloadBM() throws IOException{
        return download("BMDemo.xlsx","BMInfoDemo.xlsx");
    }


    /*
    * 楼栋信息导出
    * */
    @PostMapping("/buildinginfo")
    public void downloadBuilding(@RequestParam(name="buildingNumber") List<String> buildingNums, HttpServletResponse response)throws Exception{
        XSSFWorkbook workbook=new XSSFWorkbook();
        XSSFRow row=null;
        XSSFCell cell=null;
        try{
            for(String buildingNum:buildingNums){
                Building building=buildService.getBuilding(buildingNum);
                Supervisor supervisor=null;
                if(building.getSupervisorId()!=null){
                    supervisor=buildService.getSupervisor(building.getSupervisorId());
                }
                List<Room> rooms=roomService.getRoomByBuildingId(building.getBuildingId());
                XSSFSheet sheet=workbook.createSheet(buildingNum+"栋");
                {
                    row = sheet.createRow(0);
                    cell = row.createCell(0);
                    cell.setCellValue("宿管姓名");
                    cell = row.createCell(1);
                    cell.setCellValue("宿管联系方式");
                    cell = row.createCell(3);
                    cell.setCellValue("楼层数");
                    cell = row.createCell(4);
                    cell.setCellValue("所住学生性别");

                    row = sheet.createRow(1);
                    if(supervisor!=null){
                        cell = row.createCell(0);
                        cell.setCellValue(supervisor.getName());
                        cell = row.createCell(1);
                        cell.setCellValue(supervisor.getTel());
                    }
                    cell = row.createCell(3);
                    cell.setCellValue(building.getNumberOfFloor()==null?1:building.getNumberOfFloor());
                    cell = row.createCell(4);
                    if(building.getGenderProperty()==null)cell.setCellValue("未指定");
                    else {
                        boolean genger = building.getGenderProperty();
                        if (genger == true) cell.setCellValue("男");
                        else cell.setCellValue("女");
                    }
                    row = sheet.createRow(3);
                    cell = row.createCell(0);
                    cell.setCellValue("楼层");
                    cell = row.createCell(1);
                    cell.setCellValue("房间号");
                    cell = row.createCell(2);
                    cell.setCellValue("床位号");
                    cell = row.createCell(3);
                    cell.setCellValue("学生姓名");
                    cell = row.createCell(4);
                    cell.setCellValue("学号");
                    cell = row.createCell(5);
                    cell.setCellValue("性别");
                    cell = row.createCell(6);
                    cell.setCellValue("学院");
                    cell = row.createCell(7);
                    cell.setCellValue("专业");
                    cell = row.createCell(8);
                    cell.setCellValue("年级");
                    cell = row.createCell(9);
                    cell.setCellValue("班级");
                    cell = row.createCell(10);
                    cell.setCellValue("联系方式");
                    cell = row.createCell(11);
                    cell.setCellValue("辅导员姓名");
                }
                int index=4;
                for(Room temp:rooms){
                    List<Bunk> bunks =bunkService.getByRoom(temp.getRoomId());
                    for(Bunk bunkTemp:bunks){
                        row=sheet.createRow(index);
                        cell=row.createCell(0);
                        cell.setCellValue(temp.getFloorNumber());
                        cell=row.createCell(1);
                        cell.setCellValue(temp.getRoomNumber());
                        cell=row.createCell(2);
                        cell.setCellValue(bunkTemp.getBunkNumber());
                        if(bunkTemp.getIsEmptyBunk()==true){
                            index++;
                            continue;
                        }
                        Student student=studentService.getByBunk(bunkTemp.getBunkId());
                        cell=row.createCell(3);
                        cell.setCellValue(student.getName());
                        cell=row.createCell(4);
                        cell.setCellValue(student.getStudentNumber());
                        cell=row.createCell(5);
                        boolean genger=student.getGender();
                        if(genger==true)cell.setCellValue("男");
                        else cell.setCellValue("女");
                        cell=row.createCell(6);
                        cell.setCellValue(student.getSchool());
                        cell=row.createCell(7);
                        cell.setCellValue(student.getMajor());
                        cell=row.createCell(8);
                        cell.setCellValue(student.getGrade());
                        cell=row.createCell(9);
                        cell.setCellValue(Tools.handleDouble(student.getStudentClass()));
                        cell=row.createCell(10);
                        cell.setCellValue(student.getTel());
                        cell=row.createCell(11);
                        if(student.getInstructorId()!=null){
                            cell.setCellValue(instructorService.getById(student.getInstructorId()).getName());
                        }
                        index++;
                    }
                }
                System.out.println(buildingNum+"栋信息写出成功");

            }
                String fileName=URLEncoder.encode("BuildingInfo.xlsx","utf-8");
                response.setHeader("content-Type", "application/vnd.ms-excel");
                //response.setHeader("content-Type","application/octet-stream");
                response.setHeader("Content-disposition", "attachment;filename=" + fileName);
                response.flushBuffer();
                ServletOutputStream outputStream=response.getOutputStream();
                workbook.write(outputStream);
                workbook.close();
                outputStream.close();

        }catch (Exception e){
            e.printStackTrace();

        }
    }
}
