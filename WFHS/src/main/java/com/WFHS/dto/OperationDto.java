package com.WFHS.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OperationDto {

    private int id;
    private Date issueDate;
    private String comment;
    private String operationName;
    private int status;
    private int notificationStatus;
    private int userId; // Assuming you only need the ID of the user
    private int applicantFormId; // Assuming you only need the ID of the applicant form
}
