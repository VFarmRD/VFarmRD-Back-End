package com.example.vfarmrdbackend.service.tool;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vfarmrdbackend.model.tool.ToolInPhase;
import com.example.vfarmrdbackend.payload.tool.request.ToolInPhaseRequest;
import com.example.vfarmrdbackend.payload.tool.response.ToolInPhaseResponse;
import com.example.vfarmrdbackend.repository.tool.ToolInPhaseRepository;

@Service
public class ToolInPhaseService {
    @Autowired
    ToolInPhaseRepository toolInPhaseRepository;

    @Autowired
    ToolService toolService;

    public List<ToolInPhase> getAllToolInPhase() {
        try {
            return toolInPhaseRepository.findAll();
        } catch (Exception e) {
            throw e;
        }
    }

    public List<ToolInPhaseResponse> getAllToolInPhaseWithPhase_id(int phase_id) {
        try {
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
        } catch (Exception e) {
            throw e;
        }
    }

    public ToolInPhase getToolInPhase(int toolinphase_id) {
        try {
            return toolInPhaseRepository.getToolInPhaseByToolinphase_id(toolinphase_id);
        } catch (Exception e) {
            throw e;
        }
    }

    public void createToolInPhase(ToolInPhaseRequest request, String jwt) {
        try {
            toolInPhaseRepository.save(new ToolInPhase(request.getTool_id(), request.getPhase_id()));
        } catch (Exception e) {
            throw e;
        }
    }

    public void updateToolInPhase(int toolinphase_id, ToolInPhaseRequest request, String jwt) {
        try {
            ToolInPhase updateToolInPhase = toolInPhaseRepository.getToolInPhaseByToolinphase_id(toolinphase_id);
            updateToolInPhase.setTool_id(request.getTool_id());
            updateToolInPhase.setPhase_id(request.getPhase_id());
            toolInPhaseRepository.save(updateToolInPhase);
        } catch (Exception e) {
            throw e;
        }
    }

    public void updateMultipleToolInPhase(List<ToolInPhase> listRequest, String jwt) {
        try {
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
        } catch (Exception e) {
            throw e;
        }
    }

    public void deleteToolInPhase(int toolinphase_id, String jwt) {
        try {
            toolInPhaseRepository.delete(toolInPhaseRepository.getToolInPhaseByToolinphase_id(toolinphase_id));
        } catch (Exception e) {
            throw e;
        }
    }

    public void deleteAllToolInPhaseByPhase_id(int phase_id, String jwt) {
        try {
            List<ToolInPhase> listToolinphase = toolInPhaseRepository.getToolInPhaseByPhase_id(phase_id);
            for (int i = 0; i < listToolinphase.size(); i++) {
                toolInPhaseRepository.delete(
                        toolInPhaseRepository
                                .getToolInPhaseByToolinphase_id(listToolinphase.get(i).getToolinphase_id()));
                ;
            }
        } catch (Exception e) {
            throw e;
        }
    }

}
