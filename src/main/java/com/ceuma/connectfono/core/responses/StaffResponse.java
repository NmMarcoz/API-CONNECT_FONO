package com.ceuma.connectfono.core.responses;

import com.ceuma.connectfono.core.models.Staff;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class StaffResponse {
    private final int status;
    private final String message;
    private Staff staff;
    private List<Staff> staffs;

    public StaffResponse (int status, String message){
        this.message = message;
        this.status = status;
    }

    public StaffResponse(int status, String message, Staff staff){
        this.message = message;
        this.status = status;
        this.staff = staff;
    }
    public StaffResponse(int status, String message, Staff staff, List<Staff> staffs){
        this.message = message;
        this.status = status;
        this.staff = staff;
        this.staffs = staffs;
    }

}
