package cn.cafuc.flyeat.sb.dormitorymanagement.model;

import lombok.Data;

import java.util.Comparator;

@Data
public class StudentInfo implements Comparator<StudentInfo> {
    private Integer infoId;

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

    private String state;

    private String status;

    private String identifyNumber;

    private String buildingNumber;

    private String floorNumber;

    private String roomNumber;

    private String bunkNumber;

    private String region;

    private String style;

    private String sex;


    @Override
    public String toString() {
        return "StudentInfo{" +
                "infoId=" + infoId +
                ", name='" + name + '\'' +
                ", studentNumber='" + studentNumber + '\'' +
                ", gender=" + gender +
                ", school='" + school + '\'' +
                ", major='" + major + '\'' +
                ", grade='" + grade + '\'' +
                ", studentClass='" + studentClass + '\'' +
                ", nation='" + nation + '\'' +
                ", place='" + place + '\'' +
                ", tel='" + tel + '\'' +
                ", photo='" + photo + '\'' +
                ", state='" + state + '\'' +
                ", status='" + status + '\'' +
                ", identifyNumber='" + identifyNumber + '\'' +
                ", buildingNumber='" + buildingNumber + '\'' +
                ", floorNumber='" + floorNumber + '\'' +
                ", roomNumber='" + roomNumber + '\'' +
                ", bunkNumber='" + bunkNumber + '\'' +
                ", region='" + region + '\'' +
                ", style='" + style + '\'' +
                '}';
    }

    @Override
    public int compare(StudentInfo o1, StudentInfo o2) {
        return o1.getBuildingNumber().compareTo(o2.getBuildingNumber());
    }
}