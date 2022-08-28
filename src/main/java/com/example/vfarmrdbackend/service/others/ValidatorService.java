package com.example.vfarmrdbackend.service.others;

import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class ValidatorService {

    public boolean validateString(String string) {
        if (string.equals("")) {
            return true;
        }
        return false;
    }

    public boolean validateInteger(int value) {
        if (value < 0) {
            return true;
        }
        return false;
    }

    public boolean validateFloat(float value) {
        if (value < 0) {
            return true;
        }
        return false;
    }

    public boolean validateDate(Date theDateBefore, Date theDateAfter) {
        if (theDateBefore.after(theDateAfter)) {
            return true;
        }
        return false;
    }

}
