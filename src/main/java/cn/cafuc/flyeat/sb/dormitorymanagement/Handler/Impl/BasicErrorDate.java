package cn.cafuc.flyeat.sb.dormitorymanagement.Handler.Impl;

import cn.cafuc.flyeat.sb.dormitorymanagement.Handler.ErrorHander;
import cn.cafuc.flyeat.sb.dormitorymanagement.Handler.Impl.tool.Tools;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.stereotype.Service;

import javax.tools.Tool;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

@Service
public class BasicErrorDate implements ErrorHander {
    @Override
    public XSSFWorkbook downloadErrorDate(List<Object> errorDate)throws IOException {
        XSSFWorkbook workbook=new XSSFWorkbook();
        XSSFSheet sheet=workbook.createSheet("错误信息反馈");
        try {
            int f=0;
            int j=0;
            XSSFRow row=null;
            String name=null;
            for (int i=0;i<errorDate.size();i++){
                row=sheet.createRow(j++);
                if(((XSSFRow)errorDate.get(i)).getCell(0)!=null){
                    name=((XSSFRow)errorDate.get(i)).getCell(0).toString();
                    if(name.equals("学生"))f=1;
                    else if(name.equals("老师")){
                        f=2;
                    /*    j+=1;
                        row=sheet.createRow(j);
                    */}
                    else if(name.equals("宿管")){
                        f=3;
                       /* j+=1;
                        row=sheet.createRow(j);*/
                    }
                }
                if(f==1)createstuBody(row,(XSSFRow)errorDate.get(i));
                else if(f==2)createInsBody(row,(XSSFRow)errorDate.get(i));
                else if(f==3)createSupBody(row,(XSSFRow)errorDate.get(i));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return workbook;
    }

    private void createstuBody(XSSFRow row,XSSFRow errorDate) throws Exception{
        try {
            if(errorDate.getCell(0)!=null)row.createCell(0).setCellValue(errorDate.getCell(0).toString());
            if(errorDate.getCell(1)!=null)row.createCell(1).setCellValue(Tools.handleDouble(errorDate.getCell(1).toString()));
            if(errorDate.getCell(2)!=null)row.createCell(2).setCellValue(errorDate.getCell(2).toString());
            if(errorDate.getCell(3)!=null) row.createCell(3).setCellValue(errorDate.getCell(3).toString());
            if(errorDate.getCell(4)!=null)row.createCell(4).setCellValue(errorDate.getCell(4).toString());
            if(errorDate.getCell(5)!=null) row.createCell(5).setCellValue(errorDate.getCell(5).toString());
            if(errorDate.getCell(6)!=null)row.createCell(6).setCellValue(errorDate.getCell(6).toString());
            if(errorDate.getCell(7)!=null) row.createCell(7).setCellValue(errorDate.getCell(7).toString());
            if(errorDate.getCell(8)!=null)row.createCell(8).setCellValue(errorDate.getCell(8).toString());
            if(errorDate.getCell(9)!=null)row.createCell(9).setCellValue(Tools.handleDouble(errorDate.getCell(9).toString()));
            if(errorDate.getCell(10)!=null)row.createCell(10).setCellValue(Tools.handleDouble(errorDate.getCell(10).toString()));
            if(errorDate.getCell(11)!=null)row.createCell(11).setCellValue(Tools.handleDouble(errorDate.getCell(11).toString()));
            if(errorDate.getCell(12)!=null)row.createCell(12).setCellValue(Tools.handleDouble(errorDate.getCell(12).toString()));
            if(errorDate.getCell(13)!=null)row.createCell(13).setCellValue(Tools.handleDouble(errorDate.getCell(13).toString()));
            if(errorDate.getCell(14)!=null)row.createCell(14).setCellValue(Tools.handleDouble(errorDate.getCell(14).toString()));
            if(errorDate.getCell(15)!=null)row.createCell(15).setCellValue(Tools.handleDouble(errorDate.getCell(15).toString()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void createInsBody(XSSFRow row,XSSFRow errorDate) throws Exception{
        try {
            row.createCell(0).setCellValue(errorDate.getCell(0).toString());
            row.createCell(1).setCellValue(errorDate.getCell(1).toString());
            row.createCell(2).setCellValue(errorDate.getCell(2).toString());
            row.createCell(3).setCellValue(Tools.handleDouble(errorDate.getCell(3).toString()));
            row.createCell(4).setCellValue(Tools.handleDouble(errorDate.getCell(4).toString()));
            row.createCell(5).setCellValue(errorDate.getCell(5).toString());
            row.createCell(6).setCellValue(errorDate.getCell(6).toString());
            row.createCell(7).setCellValue(errorDate.getCell(7).toString());
            row.createCell(8).setCellValue(errorDate.getCell(8).toString());
            row.createCell(9).setCellValue(errorDate.getCell(9).toString());

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void createSupBody(XSSFRow row,XSSFRow errorDate) throws Exception{
        try {
            row.createCell(0).setCellValue(errorDate.getCell(0).toString());
            row.createCell(1).setCellValue(errorDate.getCell(1).toString());
            row.createCell(2).setCellValue(errorDate.getCell(2).toString());
            row.createCell(3).setCellValue(errorDate.getCell(3).toString());
            row.createCell(4).setCellValue(errorDate.getCell(4).toString());
            row.createCell(5).setCellValue(Tools.handleDouble(errorDate.getCell(5).toString()));
            row.createCell(6).setCellValue(Tools.handleDouble(errorDate.getCell(6).toString()));
            row.createCell(7).setCellValue(errorDate.getCell(7).toString());
            row.createCell(8).setCellValue(errorDate.getCell(8).toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
