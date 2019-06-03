package cn.cafuc.flyeat.sb.dormitorymanagement.controller;

import cn.cafuc.flyeat.sb.dormitorymanagement.Bean.ResponseBean;


import cn.cafuc.flyeat.sb.dormitorymanagement.Handler.factory.ErrorHanderFactory;
import cn.cafuc.flyeat.sb.dormitorymanagement.Handler.factory.ImportFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/import")
public class ImportController {
    @Autowired
    private ImportFactory importFactory;
    @Autowired
    private ErrorHanderFactory errorHanderFactory;
    @PostMapping("/upload")
    public void importEmp(String type, MultipartFile file, HttpServletResponse response) throws IOException {
        /*存储数据记录备份*/
        long currentTime=System.currentTimeMillis();//获取系统当前时间
        SimpleDateFormat format=new SimpleDateFormat("yyyy年-MM月dd日-HH时mm分ss秒");//设计时间显示格式
        Date date=new Date(currentTime);
        /*
         * System.getProperty("user.dir") 获取类的路径 + BackupFile文件夹(存储数据文件记录用)+格式处理后的时间+文件类型
         * 组成备份文件路径
         * */
        String filename=format.format(date);
        String pathName=System.getProperty("user.dir")+"/BackupFile/"+filename+".xlsx";
        System.out.println(type+":临时文件保存在："+pathName);
        Path path=Paths.get(pathName);
        if(!Files.isWritable(path)){
            //判断路径是否存在如果不存在则创建对应文件夹
            Files.createDirectories(Paths.get(System.getProperty("user.dir")+"/BackupFile"));
        }
        Files.write(path,file.getBytes());//将文件存入本地

        /*将数据写入数据库 临时表中*/
        ResponseBean responseBean= importFactory.getImportType(type).importInfo(pathName);
        /*以excel形式返回错误数据*/
        if(responseBean.getCode()==400) {
            List<Object> errorDate = (List<Object>) responseBean.getData();
            XSSFWorkbook workbook = errorHanderFactory.ErrorDateFactory(type).downloadErrorDate(errorDate);
            String fileName= URLEncoder.encode("ErrorInfo.xlsx","utf-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            response.setHeader("Accesss-Control-Allow-Origin","*");
            response.flushBuffer();
            ServletOutputStream outputStream=response.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        }

    }
}
