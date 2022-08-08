package com.example.vfarmrdbackend.service.material;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.vfarmrdbackend.model.material.MaterialOfPhase;
import com.example.vfarmrdbackend.model.error.ErrorModel;
import com.example.vfarmrdbackend.model.file.File;
import com.example.vfarmrdbackend.payload.material.MaterialOfPhaseCreateRequest;
import com.example.vfarmrdbackend.payload.material.MaterialOfPhaseUpdateRequest;
import com.example.vfarmrdbackend.payload.material.MaterialStatisticResponse;
import com.example.vfarmrdbackend.repository.material.MaterialOfPhaseRepository;
import com.example.vfarmrdbackend.service.error.ErrorService;
import com.example.vfarmrdbackend.service.file.FileService;
import com.example.vfarmrdbackend.service.others.JwtService;

@Service
public class MaterialOfPhaseService {
    @Autowired
    MaterialOfPhaseRepository materialOfPhaseRepository;

    @Autowired
    ErrorService errorService;

    @Autowired
    FileService fileService;

    public List<MaterialOfPhase> getAllMaterialOfPhase(int phase_id) {
        return materialOfPhaseRepository.getAllMaterialOfOnePhase(phase_id);
    }

    public MaterialOfPhase getMaterialOfPhase(int mop_id) {
        return materialOfPhaseRepository.getOneMaterialOfOnePhase(mop_id);
    }

    public void createMaterialOfPhase(int phase_id, MaterialOfPhaseCreateRequest materialOfPhaseCreateRequest,
            String jwt) {
        MaterialOfPhase materialOfPhase = new MaterialOfPhase();
        materialOfPhase.setPhase_id(phase_id);
        materialOfPhase.setMaterial_id(materialOfPhaseCreateRequest.getMaterial_id());
        materialOfPhase.setMaterial_cost(materialOfPhaseCreateRequest.getMaterial_cost());
        materialOfPhase.setMaterial_weight(materialOfPhaseCreateRequest.getMaterial_weight());
        materialOfPhase.setMaterial_percent(materialOfPhaseCreateRequest.getMaterial_percent());
        materialOfPhase.setMaterial_description(materialOfPhaseCreateRequest.getMaterial_description());
        materialOfPhaseRepository.save(materialOfPhase);
    }

    public boolean updateMaterialOfPhase(MaterialOfPhaseUpdateRequest materialOfPhaseUpdateRequest, String jwt) {
        MaterialOfPhase materialOfPhase = materialOfPhaseRepository
                .getOneMaterialOfOnePhase(materialOfPhaseUpdateRequest.getMop_id());
        if (materialOfPhase != null) {
            materialOfPhase.setMaterial_id(materialOfPhaseUpdateRequest.getMaterial_id());
            materialOfPhase.setMaterial_cost(materialOfPhaseUpdateRequest.getMaterial_cost());
            materialOfPhase.setMaterial_weight(materialOfPhaseUpdateRequest.getMaterial_weight());
            materialOfPhase.setMaterial_percent(materialOfPhaseUpdateRequest.getMaterial_percent());
            materialOfPhase.setMaterial_description(materialOfPhaseUpdateRequest.getMaterial_description());
            materialOfPhaseRepository.save(materialOfPhase);
            return true;
        }
        return false;
    }

    public boolean deleteMaterialOfPhase(int mop_id, String jwt) {
        MaterialOfPhase materialOfPhase = materialOfPhaseRepository
                .getOneMaterialOfOnePhase(mop_id);
        if (materialOfPhase != null) {
            materialOfPhaseRepository.delete(materialOfPhase);
            return true;
        }
        return false;
    }

    public void deleteAllMaterialsOfPhaseByPhase_id(int phase_id) {
        List<MaterialOfPhase> listMOP = materialOfPhaseRepository.getAllMaterialOfOnePhase(phase_id);
        for (int i = 0; i < listMOP.size(); i++) {
            materialOfPhaseRepository.delete(listMOP.get(i));
        }
    }

    public List<String> getAllMaterial_idWithProject_id(int project_id, String jwt) {
        try {
            return materialOfPhaseRepository.getAllMaterial_idWithProject_id(project_id);
        } catch (Exception e) {
            errorService.createError(new ErrorModel(
                    JwtService.getUser_idFromToken(jwt),
                    "MATERIAL GET ALL WITH PROJECT ID",
                    e.getMessage(),
                    new Date()));
            throw e;
        }
    }

    public Map<String, List<Integer>> uploadFileForMaterial(String jwt, List<MultipartFile> listFile,
            String material_id) throws Exception {
        try {
            return fileService.uploadFile(listFile, "materials", material_id, jwt);
        } catch (Exception e) {
            errorService.createError(new ErrorModel(
                    JwtService.getUser_idFromToken(jwt),
                    "MATERIAL UPLOAD FILE",
                    e.getMessage(),
                    new Date()));
            throw e;
        }
    }

    public MaterialStatisticResponse getMaterialStatisticsOfAllTime(String jwt) {
        try {
            return new MaterialStatisticResponse(materialOfPhaseRepository.getTotalMaterialUsed(),
                    materialOfPhaseRepository.getMostMaterial_idUsed(),
                    materialOfPhaseRepository.getMostMaterial_idUsedTime(),
                    materialOfPhaseRepository.getTop10MaterialMostUsedByPercent(),
                    materialOfPhaseRepository.getTop10MaterialMostUsedTimeByPercent(),
                    materialOfPhaseRepository.getTop10MaterialMostUsedByWeight(),
                    materialOfPhaseRepository.getTop10MaterialMostUsedTimeByWeight());
        } catch (Exception e) {
            errorService.createError(new ErrorModel(
                    JwtService.getUser_idFromToken(jwt),
                    "MATERIAL STATISTIC OF ALL TIME",
                    e.getMessage(),
                    new Date()));
            throw e;
        }
    }

    public Stream<File> getFileByMaterial_id(String material_id, String jwt) {
        try {
            return fileService.getFileByMaterial_id(material_id, jwt);
        } catch (Exception e) {
            errorService.createError(new ErrorModel(
                    JwtService.getUser_idFromToken(jwt),
                    "FILE GET MATERIAL",
                    e.getMessage(),
                    new Date()));
            throw e;
        }
    }

}
