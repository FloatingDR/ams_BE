package cn.cafuc.flyeat.sb.dormitorymanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class Room {
    private Integer roomId;

    private Integer buildingId;

    private Integer noticeId;

    private Integer roomPropertyId;

    private Integer floorNumber;

    private String roomNumber;

    private Integer numberOfBunk;

    private int emptyBunkNum; //空床位数

    private List<Bunk> emptyBunk; //空床位信息

}