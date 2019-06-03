package cn.cafuc.flyeat.sb.dormitorymanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Building {
    private Integer buildingId;

    private Integer noticeId;

    private Integer supervisorId;

    private String buildingNumber;

    private Integer numberOfFloor;

    private Boolean genderProperty;

    private Boolean region;

    private String style;


}