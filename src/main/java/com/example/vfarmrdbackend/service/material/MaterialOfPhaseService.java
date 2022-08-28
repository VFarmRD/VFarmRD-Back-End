package com.example.vfarmrdbackend.service.material;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.vfarmrdbackend.model.material.MaterialOfPhase;
import com.example.vfarmrdbackend.model.file.File;
import com.example.vfarmrdbackend.payload.material.request.MaterialOfPhaseUpdateRequest;
import com.example.vfarmrdbackend.payload.material.response.MaterialStatisticResponse;
import com.example.vfarmrdbackend.payload.material.request.MaterialOfPhaseCreateRequest;
import com.example.vfarmrdbackend.repository.material.MaterialOfPhaseRepository;
import com.example.vfarmrdbackend.service.file.FileService;

@Service
public class MaterialOfPhaseService {
    @Autowired
    MaterialOfPhaseRepository materialOfPhaseRepository;

    @Autowired
    FileService fileService;

    public List<MaterialOfPhase> getAllMaterialOfPhase(int phase_id) {
        try {
            return materialOfPhaseRepository.getAllMaterialOfOnePhase(phase_id);
        } catch (Exception e) {
            throw e;
        }
    }

    public MaterialOfPhase getMaterialOfPhase(int mop_id) {
        try {
            return materialOfPhaseRepository.getOneMaterialOfOnePhase(mop_id);
        } catch (Exception e) {
            throw e;
        }
    }

    public void createMaterialOfPhase(int phase_id, MaterialOfPhaseCreateRequest materialOfPhaseCreateRequest,
            String jwt) {
        try {
            MaterialOfPhase materialOfPhase = new MaterialOfPhase(phase_id,
                    materialOfPhaseCreateRequest.getMaterial_id(),
                    materialOfPhaseCreateRequest.getMaterial_cost(),
                    materialOfPhaseCreateRequest.getMaterial_weight(),
                    materialOfPhaseCreateRequest.getMaterial_percent(),
                    materialOfPhaseCreateRequest.getMaterial_description());
            materialOfPhaseRepository.save(materialOfPhase);
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean updateMaterialOfPhase(MaterialOfPhaseUpdateRequest materialOfPhaseUpdateRequest, String jwt) {
        try {
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
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean deleteMaterialOfPhase(int mop_id, String jwt) {
        try {
            MaterialOfPhase materialOfPhase = materialOfPhaseRepository
                    .getOneMaterialOfOnePhase(mop_id);
            if (materialOfPhase != null) {
                materialOfPhaseRepository.delete(materialOfPhase);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw e;
        }
    }

    public List<String> getAllMaterial_idWithProject_id(int project_id, String jwt) {
        try {
            return materialOfPhaseRepository.getAllMaterial_idWithProject_id(project_id);
        } catch (Exception e) {
            throw e;
        }
    }

    public Map<String, List<Integer>> uploadFileForMaterial(String jwt, List<MultipartFile> listFile,
            String material_id) throws Exception {
        try {
            return fileService.uploadFile(listFile, "materials", material_id, jwt);
        } catch (Exception e) {
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
            throw e;
        }
    }

    public Stream<File> getFileByMaterial_id(String material_id, String jwt) {
        try {
            return fileService.getFileByMaterial_id(material_id, jwt);
        } catch (Exception e) {
            throw e;
        }
    }

}
