package cn.cafuc.flyeat.sb.dormitorymanagement.Handler.Impl;

import cn.cafuc.flyeat.sb.dormitorymanagement.Handler.ErrorHander;
import cn.cafuc.flyeat.sb.dormitorymanagement.model.Supervisor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SuperErrorDate implements ErrorHander {
    @Override
    public XSSFWorkbook downloadErrorDate(List<Object> errorDate)throws IOException {
        XSSFWorkbook workbook=new XSSFWorkbook();
        XSSFSheet sheet=null;
        XSSFRow row=null;
        sheet=workbook.createSheet("宿管错误信息反馈表");
        row=sheet.createRow(0);
        createHead(row);
        for(int i=1;i<=errorDate.size();i++){
            row=sheet.createRow(i);
            createBody(row,(Supervisor)errorDate.get(i-1));
        }
        return workbook;
    }
    private void createHead(XSSFRow row){
        row.createCell(0).setCellValue("姓名");
        row.createCell(1).setCellValue("工号");
        row.createCell(2).setCellValue("性别");
        row.createCell(3).setCellValue("籍贯");
        row.createCell(4).setCellValue("电话");
        row.createCell(5).setCellValue("身份证");
    }
    private void createBody(XSSFRow row,Supervisor supervisor){
        row.createCell(0).setCellValue(supervisor.getName());
        row.createCell(1).setCellValue(supervisor.getSupervisorNumber());
        row.createCell(2).setCellValue(supervisor.getGender()==true?"男":"女");
        row.createCell(3).setCellValue(supervisor.getPlace());
        row.createCell(4).setCellValue(supervisor.getTel());
        row.createCell(5).setCellValue(supervisor.getIdentifyNumber());
    }
}
