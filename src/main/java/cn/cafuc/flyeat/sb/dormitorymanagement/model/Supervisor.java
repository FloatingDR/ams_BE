package cn.cafuc.flyeat.sb.dormitorymanagement.model;

import lombok.Data;

@Data
public class Supervisor {
    private Integer supervisorId;

    private String name;

    private String supervisorNumber;

    private Boolean gender;

    private String place;

    private String tel;

    private String identifyNumber;

    private String watchSpan;
}