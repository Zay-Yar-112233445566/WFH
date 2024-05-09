package com.WFHS.dto;

public class CheckStaffIdResponse {

	private boolean exists;

    public CheckStaffIdResponse(boolean exists) {
        this.exists = exists;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }
}
