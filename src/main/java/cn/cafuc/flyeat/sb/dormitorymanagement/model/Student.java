package cn.cafuc.flyeat.sb.dormitorymanagement.model;

import lombok.Data;

@Data
public class Student {
    private Integer studentId;

    private Integer bunkId;

    private Integer instructorId;

    private String name;

    private String studentNumber;

    private Boolean gender;

    private String school;

    private String major;

    private String grade;

    private String studentClass;

    private String nation;

    private String place;

    private String tel;

    private String photo;

    private String status;

    private String identifyNumber;

    private String build;//楼栋号
    private String roomNo;//房间号
    private String bunkNo;//床位号
    private String sex;
}