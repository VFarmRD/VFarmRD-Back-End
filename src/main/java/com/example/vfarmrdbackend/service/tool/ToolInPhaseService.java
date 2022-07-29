package com.example.vfarmrdbackend.service.tool;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vfarmrdbackend.model.tool.ToolInPhase;
import com.example.vfarmrdbackend.payload.tool.ToolInPhaseRequest;
import com.example.vfarmrdbackend.payload.tool.ToolInPhaseResponse;
import com.example.vfarmrdbackend.repository.tool.ToolInPhaseRepository;

@Service
public class ToolInPhaseService {
    @Autowired
    ToolInPhaseRepository toolInPhaseRepository;

    @Autowired
    ToolService toolService;

    public List<ToolInPhase> getAllToolInPhase() {
        return toolInPhaseRepository.findAll();
    }

    public List<ToolInPhaseResponse> getAllToolInPhaseWithPhase_id(int phase_id) {
        List<ToolInPhaseResponse> listResponse = new ArrayList<>();
        List<ToolInPhase> listToolinphase = toolInPhaseRepository.getToolInPhaseByPhase_id(phase_id);
        for (int i = 0; i < listToolinphase.size(); i++) {
            ToolInPhaseResponse response = new ToolInPhaseResponse();
            response.setToolinphase_id(listToolinphase.get(i).getToolinphase_id());
            response.setPhase_id(phase_id);
            response.setToolResponse(toolService.getTool(listToolinphase.get(i).getTool_id()));
            listResponse.add(response);
        }
        return listResponse;
    }

    public ToolInPhase getToolInPhase(int toolinphase_id) {
        return toolInPhaseRepository.getToolInPhaseByToolinphase_id(toolinphase_id);
    }

    public void createToolInPhase(ToolInPhaseRequest request, String jwt) {
        toolInPhaseRepository.save(new ToolInPhase(request.getTool_id(), request.getPhase_id()));
    }

    public void updateToolInPhase(int toolinphase_id, ToolInPhaseRequest request, String jwt) {
        ToolInPhase updateToolInPhase = toolInPhaseRepository.getToolInPhaseByToolinphase_id(toolinphase_id);
        updateToolInPhase.setTool_id(request.getTool_id());
        updateToolInPhase.setPhase_id(request.getPhase_id());
        toolInPhaseRepository.save(updateToolInPhase);
    }

    public void updateMultipleToolInPhase(List<ToolInPhase> listRequest, String jwt) {
        List<Integer> listToolinphase_idInPhase = toolInPhaseRepository
                .getToolInPhase_idByPhase_id(listRequest.get(0).getPhase_id());
        for (int i = 0; i < listRequest.size(); i++) {
            if (listRequest.get(i).getTool_id() != 0) {
                updateToolInPhase(listRequest.get(i).getToolinphase_id(),
                        new ToolInPhaseRequest(
                                listRequest.get(i).getTool_id(),
                                listRequest.get(i).getPhase_id()),
                        jwt);
                listToolinphase_idInPhase.remove(Integer.valueOf(listRequest.get(i).getToolinphase_id()));
            } else if (listRequest.get(i).getTool_id() == 0) {
                createToolInPhase(
                        new ToolInPhaseRequest(
                                listRequest.get(i).getTool_id(),
                                listRequest.get(i).getPhase_id()),
                        jwt);
            }
        }

        for (int i = 0; i < listToolinphase_idInPhase.size(); i++) {
            deleteToolInPhase(listToolinphase_idInPhase.get(i), jwt);
        }
    }

    public void deleteToolInPhase(int toolinphase_id, String jwt) {
        toolInPhaseRepository.delete(toolInPhaseRepository.getToolInPhaseByToolinphase_id(toolinphase_id));
    }

    public void deleteAllToolInPhaseByPhase_id(int phase_id, String jwt) {
        List<ToolInPhase> listToolinphase = toolInPhaseRepository.getToolInPhaseByPhase_id(phase_id);
        for (int i = 0; i < listToolinphase.size(); i++) {
            toolInPhaseRepository.delete(
                    toolInPhaseRepository.getToolInPhaseByToolinphase_id(listToolinphase.get(i).getToolinphase_id()));
            ;
        }
    }

}
