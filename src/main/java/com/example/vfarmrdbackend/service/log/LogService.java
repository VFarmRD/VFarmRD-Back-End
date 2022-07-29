package com.example.vfarmrdbackend.service.log;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vfarmrdbackend.model.log.Log;
import com.example.vfarmrdbackend.payload.log.LogGetResponse;
import com.example.vfarmrdbackend.repository.log.LogRepository;
import com.example.vfarmrdbackend.repository.user.UserRepository;

@Service
public class LogService {

    @Autowired
    LogRepository logRepository;

    @Autowired
    UserRepository userRepository;

    public List<LogGetResponse> getAllLogs() {
        List<Log> listLog = logRepository.findAll();
        List<LogGetResponse> listResponse = new ArrayList<>();
        for (int i = 0; i < listLog.size(); i++) {
            Log log = listLog.get(i);
            LogGetResponse response = new LogGetResponse();
            response.setLog_id(log.getLog_id());
            response.setObject_id(log.getObject_id());
            response.setObject_type(log.getObject_type());
            response.setUser_id(log.getUser_id());
            response.setUser_fullname(userRepository.getUserByUser_id(log.getUser_id()).getFullname());
            listResponse.add(response);
        }
        return listResponse;

    }

    public LogGetResponse getLog(int log_id) {
        Log log = logRepository.getLogById(log_id);
        LogGetResponse response = new LogGetResponse();
        response.setLog_id(log.getLog_id());
        response.setObject_id(log.getObject_id());
        response.setObject_type(log.getObject_type());
        response.setUser_id(log.getUser_id());
        response.setUser_fullname(userRepository.getUserByUser_id(log.getUser_id()).getFullname());
        return response;
    }

    public void createLog(Log log) {
        logRepository.save(log);
    }

    public boolean deleteLog(int log_id) {
        Log log = logRepository.getLogById(log_id);
        if (log != null) {
            logRepository.delete(log);
            return true;
        }
        return false;
    }
}
