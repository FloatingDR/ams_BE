package cn.cafuc.flyeat.sb.dormitorymanagement.Handler;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface ErrorHander {

    public XSSFWorkbook downloadErrorDate(List<Object> errorDate)throws IOException;
}
