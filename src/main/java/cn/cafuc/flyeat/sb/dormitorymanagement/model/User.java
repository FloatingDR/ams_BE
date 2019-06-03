package cn.cafuc.flyeat.sb.dormitorymanagement.model;

import lombok.Data;
@Data
public class User {
    private Integer userId;
    private Integer supervisorId;
    private Integer instructorId;
    private Integer studentId;
    private String account;
    private String password;

}
