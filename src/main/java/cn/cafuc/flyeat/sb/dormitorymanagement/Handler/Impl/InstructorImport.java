package cn.cafuc.flyeat.sb.dormitorymanagement.Handler.Impl;

import cn.cafuc.flyeat.sb.dormitorymanagement.Bean.ResponseBean;
import cn.cafuc.flyeat.sb.dormitorymanagement.Handler.Impl.tool.Tools;
import cn.cafuc.flyeat.sb.dormitorymanagement.Handler.Import;
import cn.cafuc.flyeat.sb.dormitorymanagement.model.Instructor;
import cn.cafuc.flyeat.sb.dormitorymanagement.model.User;
import cn.cafuc.flyeat.sb.dormitorymanagement.service.InstructorService;
import cn.cafuc.flyeat.sb.dormitorymanagement.service.UserService;
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
public class InstructorImport implements Import {
    @Autowired
    InstructorService instructorService;
    @Autowired
    UserService userService;
    @Override
    public ResponseBean importInfo(String filePath) throws IOException {
        FileInputStream fileInputStream=new FileInputStream(new File(filePath));
        XSSFWorkbook workbook=new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet=workbook.getSheetAt(0);
        List<Instructor> errorIns=new ArrayList<>();
        try {
            for (int i=1;i<=sheet.getLastRowNum();i++){
                XSSFRow row=sheet.getRow(i);
                if(row==null)break;
                Instructor instructor=new Instructor();
                setInfo(row,instructor);
                if(instructorService.getByIdNum(instructor.getInstructorNumber())==null){
                    instructorService.insert(instructor);
                    User user=new User();
                    user.setAccount(instructor.getIdentifyNumber());
                    user.setPassword("6944edcd743bacc8297cdabe0130a604");
                    user.setInstructorId(instructor.getInstructorId());
                    userService.insert(user);
                }else{
                    errorIns.add(instructor);
                }
            }
        }catch (RuntimeException e){
            e.printStackTrace();
        }
        if(errorIns.size()!=0){
            return new ResponseBean(400,"Something wrong!",errorIns);
        }else{
            return new ResponseBean(200,"success",null);
        }


    }
    private String change(String d){
        return d.substring(0,d.indexOf("."));
    }

    private void setInfo(XSSFRow row,Instructor instructor){
        instructor.setName(row.getCell(0).toString());
        instructor.setInstructorNumber(Tools.handleDouble(row.getCell(1).toString()));
        String gender=row.getCell(2).toString();
        if(gender.equals("男")){
            instructor.setGender(true);
        }else{
            instructor.setGender(false);
        }
        instructor.setSchool(row.getCell(3).toString());
        instructor.setMajor(row.getCell(4).toString());
        instructor.setTel(Tools.handleDouble(row.getCell(5).toString()));
        instructor.setIdentifyNumber(Tools.IDP(row.getCell(6).toString()));
    }
}
