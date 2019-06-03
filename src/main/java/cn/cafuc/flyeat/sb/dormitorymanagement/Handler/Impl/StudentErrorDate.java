package cn.cafuc.flyeat.sb.dormitorymanagement.Handler.Impl;

import cn.cafuc.flyeat.sb.dormitorymanagement.Handler.ErrorHander;
import cn.cafuc.flyeat.sb.dormitorymanagement.Handler.Impl.tool.Tools;
import cn.cafuc.flyeat.sb.dormitorymanagement.model.Student;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.tools.Tool;
import java.io.IOException;
import java.util.List;

@Service
public class StudentErrorDate implements ErrorHander {
    @Override
    public XSSFWorkbook downloadErrorDate(List<Object> errorDate)throws IOException {
        XSSFWorkbook workbook=new XSSFWorkbook();
        XSSFSheet sheet=workbook.createSheet("学生错误信息反馈");
        XSSFRow row=sheet.createRow(0);
        createHead(row);
        try {
            for(int i=1;i<=errorDate.size();i++){
                row=sheet.createRow(i);
                createBody(row,(XSSFRow) errorDate.get(i-1));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return workbook;

    }
    private void createHead(XSSFRow row){
        row.createCell(0).setCellValue("学生姓名");
        row.createCell(1).setCellValue("学号");
        row.createCell(2).setCellValue("性别");
        row.createCell(3).setCellValue("楼栋");
        row.createCell(4).setCellValue("房间");
        row.createCell(5).setCellValue("床位");
        row.createCell(6).setCellValue("学院");
        row.createCell(7).setCellValue("专业");
        row.createCell(8).setCellValue("年级");
        row.createCell(9).setCellValue("班级");
        row.createCell(10).setCellValue("名族");
        row.createCell(11).setCellValue("籍贯");
        row.createCell(12).setCellValue("电话");
        row.createCell(13).setCellValue("职位");
        row.createCell(14).setCellValue("身份证号");
        row.createCell(15).setCellValue("辅导员");
        row.createCell(16).setCellValue("错误信息");

    }
    private void createBody(XSSFRow row, XSSFRow errorRow)throws Exception{
        try {
            row.createCell(0).setCellValue(errorRow.getCell(0).toString());
            row.createCell(1).setCellValue(Tools.handleDouble(errorRow.getCell(1).toString()));
            row.createCell(2).setCellValue(errorRow.getCell(2).toString());
            row.createCell(3).setCellValue(errorRow.getCell(3).toString());
            row.createCell(4).setCellValue(errorRow.getCell(4).toString());
            row.createCell(5).setCellValue(errorRow.getCell(5).toString());
            row.createCell(6).setCellValue(errorRow.getCell(6).toString());
            row.createCell(7).setCellValue(errorRow.getCell(7).toString());
            row.createCell(8).setCellValue(errorRow.getCell(8).toString());
            row.createCell(9).setCellValue(errorRow.getCell(9).toString());
            row.createCell(10).setCellValue(errorRow.getCell(10).toString());
            row.createCell(11).setCellValue(errorRow.getCell(11).toString());
            row.createCell(12).setCellValue(Tools.handleDouble(errorRow.getCell(12).toString()));
            row.createCell(13).setCellValue(errorRow.getCell(13).toString());
            row.createCell(14).setCellValue(Tools.handleDouble(errorRow.getCell(14).toString()));
            row.createCell(15).setCellValue(errorRow.getCell(15).toString());
            row.createCell(16).setCellValue(errorRow.getCell(16).toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
