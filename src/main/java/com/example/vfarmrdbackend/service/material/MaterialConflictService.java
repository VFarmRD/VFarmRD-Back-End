package com.example.vfarmrdbackend.service.material;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vfarmrdbackend.model.log.Log;
import com.example.vfarmrdbackend.model.material.MaterialConflict;
import com.example.vfarmrdbackend.payload.material.MaterialConflictCreateRequest;
import com.example.vfarmrdbackend.payload.material.MaterialConflictUpdateRequest;
import com.example.vfarmrdbackend.repository.material.MaterialConflictRepository;
import com.example.vfarmrdbackend.service.log.LogService;
import com.example.vfarmrdbackend.service.others.JwtService;

@Service
public class MaterialConflictService {
    @Autowired
    MaterialConflictRepository materialConflictRepository;

    @Autowired
    LogService logService;

    public List<MaterialConflict> getAllMaterialConflict() {
        return materialConflictRepository.findAll();
    }

    public MaterialConflict getMaterialConflictById(int materialconflict_id) {
        return materialConflictRepository.getMaterialConflictById(materialconflict_id);
    }

    public List<MaterialConflict> getMaterialConflictByFirstMaterialId(String first_material_id) {
        return materialConflictRepository.getMaterialConflictByFirstMaterialId(first_material_id);
    }

    public void createMaterialConflict(List<MaterialConflictCreateRequest> listRequest, String jwt) {
        for (int i = 0; i < listRequest.size(); i++) {
            MaterialConflictCreateRequest request = listRequest.get(i);
            MaterialConflict firstNewMaterialConflict = new MaterialConflict();
            firstNewMaterialConflict.setFirst_material_id(request.getFirst_material_id());
            firstNewMaterialConflict.setSecond_material_id(request.getSecond_material_id());
            firstNewMaterialConflict.setDescription(request.getDescription());
            materialConflictRepository.save(firstNewMaterialConflict);
            MaterialConflict secondNewMaterialConflict = new MaterialConflict();
            secondNewMaterialConflict.setFirst_material_id(request.getSecond_material_id());
            secondNewMaterialConflict.setSecond_material_id(request.getFirst_material_id());
            secondNewMaterialConflict.setDescription(request.getDescription());
            materialConflictRepository.save(secondNewMaterialConflict);
            logService.createLog(new Log(JwtService.getUser_idFromToken(jwt),
                    "MATERIAL CONFLICT",
                    "CREATE",
                    String.valueOf(materialConflictRepository.getMaterialConflictByTwoMaterial_id(
                            request.getFirst_material_id(),
                            request.getSecond_material_id())),
                    new Date()));
            logService.createLog(new Log(JwtService.getUser_idFromToken(jwt),
                    "MATERIAL CONFLICT",
                    "CREATE",
                    String.valueOf(materialConflictRepository.getMaterialConflictByTwoMaterial_id(
                            request.getSecond_material_id(),
                            request.getFirst_material_id())),
                    new Date()));

        }
    }

    public void updateMaterialConflict(List<MaterialConflictUpdateRequest> listRequest, String jwt) {
        String material_id = listRequest.get(0).getFirst_material_id();
        List<MaterialConflict> listMaterialConflict = materialConflictRepository
                .getMaterialConflictByMaterial_id(material_id);
        List<Integer> listMaterialConflict_id = materialConflictRepository
                .getMaterialConflictIdBMaterial_id(material_id);
        List<MaterialConflictCreateRequest> listCreateRequest = new ArrayList<>();
        for (int i = 0; i < listRequest.size(); i++) {
            if (listRequest.get(i).getMaterialconflict_id() != 0) {
                for (int j = 0; j < listMaterialConflict.size(); j++) {
                    if (listMaterialConflict.get(j).getFirst_material_id() == material_id) {
                        MaterialConflict updateConflict = materialConflictRepository
                                .getMaterialConflictById(listRequest.get(j).getMaterialconflict_id());
                        updateConflict.setFirst_material_id(listRequest.get(i).getFirst_material_id());
                        updateConflict.setSecond_material_id(listRequest.get(i).getSecond_material_id());
                        updateConflict.setDescription(listRequest.get(i).getDescription());
                        materialConflictRepository.save(updateConflict);
                        logService.createLog(new Log(JwtService.getUser_idFromToken(jwt),
                                "MATERIAL CONFLICT",
                                "UPDATE",
                                String.valueOf(materialConflictRepository.getMaterialConflictByTwoMaterial_id(
                                        listRequest.get(i).getFirst_material_id(),
                                        listRequest.get(i).getSecond_material_id())),
                                new Date()));
                    } else if (listMaterialConflict.get(j).getSecond_material_id() == material_id) {
                        MaterialConflict updateConflict = materialConflictRepository
                                .getMaterialConflictById(listRequest.get(j).getMaterialconflict_id());
                        updateConflict.setFirst_material_id(listRequest.get(i).getSecond_material_id());
                        updateConflict.setSecond_material_id(listRequest.get(i).getFirst_material_id());
                        updateConflict.setDescription(listRequest.get(i).getDescription());
                        materialConflictRepository.save(updateConflict);
                        logService.createLog(new Log(JwtService.getUser_idFromToken(jwt),
                                "MATERIAL CONFLICT",
                                "UPDATE",
                                String.valueOf(materialConflictRepository.getMaterialConflictByTwoMaterial_id(
                                        listRequest.get(i).getSecond_material_id(),
                                        listRequest.get(i).getFirst_material_id())),
                                new Date()));
                    }
                    listMaterialConflict_id.remove(Integer.valueOf(listRequest.get(i).getMaterialconflict_id()));
                }
            } else if (listRequest.get(i).getMaterialconflict_id() == 0) {
                MaterialConflictCreateRequest createRequest = new MaterialConflictCreateRequest();
                createRequest.setFirst_material_id(listRequest.get(i).getFirst_material_id());
                createRequest.setSecond_material_id(listRequest.get(i).getSecond_material_id());
                createRequest.setDescription(listRequest.get(i).getDescription());
                listCreateRequest.add(createRequest);
            }
        }
        for (int i = 0; i < listCreateRequest.size(); i++) {
            createMaterialConflict(listCreateRequest, jwt);
        }
        for (int i = 0; i < listMaterialConflict_id.size(); i++) {
            deleteMaterialConflict(listMaterialConflict_id.get(i), jwt);
        }
    }

    public boolean deleteMaterialConflict(int materialconflict_id, String jwt) {
        MaterialConflict deleteConflict = materialConflictRepository.getMaterialConflictById(materialconflict_id);
        if (deleteConflict != null) {
            materialConflictRepository.delete(deleteConflict);
            logService.createLog(new Log(JwtService.getUser_idFromToken(jwt),
                    "MATERIAL CONFLICT",
                    "DELETE",
                    String.valueOf(materialconflict_id),
                    new Date()));
            return true;
        }
        return false;
    }
}
