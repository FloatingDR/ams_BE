package cn.cafuc.flyeat.sb.dormitorymanagement.Handler.Impl;

import cn.cafuc.flyeat.sb.dormitorymanagement.Handler.ErrorHander;
import cn.cafuc.flyeat.sb.dormitorymanagement.model.Instructor;
import cn.cafuc.flyeat.sb.dormitorymanagement.model.Student;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class InsErrorDate implements ErrorHander {
    @Override
    public XSSFWorkbook downloadErrorDate(List<Object> errorDate) throws IOException {
        XSSFWorkbook workbook =new XSSFWorkbook();
        XSSFSheet sheet=workbook.createSheet("宿管错误信息反馈");
        XSSFRow row=sheet.createRow(0);
        creatHead(row);
        for(int i=1;i<=errorDate.size();i++){
            row=sheet.createRow(i);
            Instructor instructor=(Instructor)errorDate.get(i-1);
            creatBody(row,instructor);
        }
        return workbook;
    }
    private void creatHead(XSSFRow row){
        row.createCell(0).setCellValue("姓名");
        row.createCell(1).setCellValue("工号");
        row.createCell(2).setCellValue("性别");
        row.createCell(3).setCellValue("学院");
        row.createCell(4).setCellValue("专业");
    }
    private void creatBody(XSSFRow row, Instructor instructor){
        row.createCell(0).setCellValue(instructor.getName());
        row.createCell(1).setCellValue(instructor.getInstructorNumber());
        row.createCell(2).setCellValue(instructor.getGender()==true?"男":"女");
        row.createCell(3).setCellValue(instructor.getSchool());
        row.createCell(4).setCellValue(instructor.getMajor());
    }
}
