package com.ceuma.connectfono.responses;

import com.ceuma.connectfono.models.Patient;
import com.ceuma.connectfono.models.Staff;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class StaffResponse {
    private final int status;
    private final String message;
    private Patient staff;
    private List<Patient> staffs;

    public StaffResponse (int status, String message){
        this.message = message;
        this.status = status;
    }

    public StaffResponse(int status, String message, Staff staff){
        this.message = message;
        this.status = status;
        this.staff = this.staff;
    }
    public StaffResponse(int status, String message, Staff staff, List<Staff> staffs){
        this.message = message;
        this.status = status;
        this.staff = this.staff;
        this.staffs = this.staffs;
    }

}
