package com.example.vfarmrdbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vfarmrdbackend.model.ToolInPhase;
import com.example.vfarmrdbackend.payload.ToolInPhaseRequest;
import com.example.vfarmrdbackend.repository.ToolInPhaseRepository;

@Service
public class ToolInPhaseService {
    @Autowired
    ToolInPhaseRepository toolInPhaseRepository;

    public List<ToolInPhase> getAllToolInPhase() {
        return toolInPhaseRepository.findAll();
    }

    public ToolInPhase getToolInPhase(int toolinphase_id) {
        return toolInPhaseRepository.getToolInPhaseByToolinphase_id(toolinphase_id);
    }

    public void createToolInPhase(ToolInPhaseRequest request, String jwt) {
        toolInPhaseRepository.save(new ToolInPhase(request.getTool_id(), request.getTool_id()));
    }

    public void updateToolInPhase(int toolinphase_id, ToolInPhaseRequest request, String jwt) {
        ToolInPhase updateToolInPhase = toolInPhaseRepository.getToolInPhaseByToolinphase_id(toolinphase_id);
        updateToolInPhase.setTool_id(request.getTool_id());
        updateToolInPhase.setPhase_id(request.getPhase_id());
        toolInPhaseRepository.save(updateToolInPhase);
    }

    public void deleteToolInPhase(int toolinphase_id, String jwt) {
        toolInPhaseRepository.delete(toolInPhaseRepository.getToolInPhaseByToolinphase_id(toolinphase_id));
    }

}
