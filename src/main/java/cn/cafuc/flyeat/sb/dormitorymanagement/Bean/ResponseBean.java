package cn.cafuc.flyeat.sb.dormitorymanagement.Bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor//LomBok插件  自动生成一个全参的构造函数
public class ResponseBean {
    private int code;
    private String msg;
    private Object data;


}
