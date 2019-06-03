package cn.cafuc.flyeat.sb.dormitorymanagement.Handler.Impl;

import cn.cafuc.flyeat.sb.dormitorymanagement.Bean.ResponseBean;
import cn.cafuc.flyeat.sb.dormitorymanagement.Handler.Import;
import cn.cafuc.flyeat.sb.dormitorymanagement.model.Supervisor;
import cn.cafuc.flyeat.sb.dormitorymanagement.service.BuildService;
import cn.cafuc.flyeat.sb.dormitorymanagement.service.SupervisorService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class SupervisorImport implements Import {
    @Autowired
    BuildService buildService;
    @Autowired
    SupervisorService supervisorService;
    @Override
    public ResponseBean importInfo(String filePath) throws IOException {
        FileInputStream fileInputStream=new FileInputStream(new File(filePath));
        XSSFWorkbook workbook=new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet=workbook.getSheetAt(0);
        List<Supervisor> errorIns=new ArrayList<>();
        try {
            for (int i=1;i<=sheet.getLastRowNum();i++){
                XSSFRow row=sheet.getRow(i);
                if(row==null)break;
                Supervisor supervisor=new Supervisor();
                setInfo(row,supervisor);
                if(supervisorService.get(supervisor.getSupervisorNumber())!=null){
                    errorIns.add(supervisor);
                }else {
                    supervisorService.insert(supervisor);
                    buildService.setWorker(row.getCell(6).toString(),supervisorService.get(supervisor.getSupervisorNumber()).getSupervisorId());
                }
            }
        }catch (RuntimeException e){
            e.printStackTrace();
        }
        if(errorIns.size()!=0){
            return new ResponseBean(400,"something wrong!",errorIns);
        }else{
            return new ResponseBean(200,"success",null);
        }
    }
    private String change(Double d){
        DecimalFormat df=new DecimalFormat("#");
        return String.valueOf(df.format(d));
    }
    private void setInfo(XSSFRow row,Supervisor supervisor){
        supervisor.setName(row.getCell(0).toString());
        supervisor.setSupervisorNumber(row.getCell(1).toString());
        String gender=row.getCell(2).toString();
        if(gender.equals("男")){
            supervisor.setGender(true);
        }else{
            supervisor.setGender(false);
        }
        supervisor.setPlace(row.getCell(3).toString());
        supervisor.setTel(change(row.getCell(4).getNumericCellValue()));
        supervisor.setIdentifyNumber(row.getCell(5).toString());
    }
}
