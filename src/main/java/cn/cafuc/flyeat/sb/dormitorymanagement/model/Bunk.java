package cn.cafuc.flyeat.sb.dormitorymanagement.model;

import lombok.Data;

@Data
public class Bunk {
    private Integer bunkId;

    private Integer roomId;

    private String bunkNumber;

    private Boolean isEmptyBunk;

    private String personId;

}