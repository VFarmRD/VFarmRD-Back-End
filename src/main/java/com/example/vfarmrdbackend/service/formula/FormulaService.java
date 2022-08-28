package com.example.vfarmrdbackend.service.formula;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vfarmrdbackend.model.formula.Formula;
import com.example.vfarmrdbackend.model.log.Log;
import com.example.vfarmrdbackend.model.material.MaterialOfPhase;
import com.example.vfarmrdbackend.model.notification.Notification;
import com.example.vfarmrdbackend.model.phase.Phase;
import com.example.vfarmrdbackend.model.test.Test;
import com.example.vfarmrdbackend.model.user.User;
import com.example.vfarmrdbackend.payload.formula.request.FormulaCreateRequest;
import com.example.vfarmrdbackend.payload.formula.request.FormulaUpdateRequest;
import com.example.vfarmrdbackend.payload.formula.request.FormulaUpgradeRequest;
import com.example.vfarmrdbackend.payload.formula.response.FormulaGetAllResponse;
import com.example.vfarmrdbackend.payload.formula.response.FormulaGetResponse;
import com.example.vfarmrdbackend.payload.formula.response.FormulaStatisticsFromDateToDateResponse;
import com.example.vfarmrdbackend.payload.formula.response.FormulaStatisticsResponse;
import com.example.vfarmrdbackend.payload.material.request.MaterialOfPhaseUpdateRequest;
import com.example.vfarmrdbackend.payload.material.request.MaterialOfPhaseCreateRequest;
import com.example.vfarmrdbackend.payload.phase.request.PhaseCreateRequest;
import com.example.vfarmrdbackend.payload.phase.request.PhaseUpdateRequest;
import com.example.vfarmrdbackend.payload.tool.request.ToolInPhaseRequest;
import com.example.vfarmrdbackend.payload.material.response.MaterialOfPhaseGetResponse;
import com.example.vfarmrdbackend.payload.phase.response.PhaseGetResponse;
import com.example.vfarmrdbackend.payload.test.response.TestGetResponse;
import com.example.vfarmrdbackend.payload.tool.response.ToolInPhaseResponse;
import com.example.vfarmrdbackend.repository.formula.FormulaRepository;
import com.example.vfarmrdbackend.service.project.ProjectService;
import com.example.vfarmrdbackend.service.test.TestService;
import com.example.vfarmrdbackend.service.tool.ToolInPhaseService;
import com.example.vfarmrdbackend.service.user.UserService;
import com.example.vfarmrdbackend.service.log.LogService;
import com.example.vfarmrdbackend.service.material.MaterialOfPhaseService;
import com.example.vfarmrdbackend.service.notification.NotificationService;
import com.example.vfarmrdbackend.service.others.JwtService;
import com.example.vfarmrdbackend.service.phase.PhaseService;

@Service
public class FormulaService {
    @Autowired
    FormulaRepository formulaRepository;

    @Autowired
    PhaseService phaseService;

    @Autowired
    MaterialOfPhaseService materialOfPhaseService;

    @Autowired
    UserService userService;

    @Autowired
    TestService testService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    LogService logService;

    @Autowired
    ToolInPhaseService toolInPhaseService;

    @Autowired
    ProjectService projectService;

    public List<FormulaGetAllResponse> getAllFormulaByProject_id(int project_id, String formula_status, String jwt) {
        try {
            List<Formula> listFormulas = formulaRepository.getAllFormulaByProject_idAndStatus(project_id,
                    "%" + formula_status + "%");
            List<FormulaGetAllResponse> listFormulasGetAll = new ArrayList<>();
            for (int i = 0; i < listFormulas.size(); i++) {
                Formula formula = listFormulas.get(i);
                FormulaGetAllResponse formulaGetAllResponse = new FormulaGetAllResponse();
                formulaGetAllResponse.setFormula_id(formula.getFormula_id());
                formulaGetAllResponse.setProject_id(formula.getProject_id());
                formulaGetAllResponse
                        .setProject_name(projectService.getProjectByProject_id(project_id, jwt).getProject_name());
                formulaGetAllResponse.setFormula_pre_version(formula.getFormula_pre_version());
                formulaGetAllResponse.setFormula_status(formula.getFormula_status());
                formulaGetAllResponse.setFormula_version(formula.getFormula_version());
                formulaGetAllResponse.setFormula_weight(formula.getFormula_weight());
                formulaGetAllResponse.setFormula_cost(formula.getFormula_cost());
                formulaGetAllResponse.setUser_id(formula.getCreated_user_id());
                formulaGetAllResponse.setVolume(formula.getVolume());
                formulaGetAllResponse.setProduct_weight(formula.getProduct_weight());
                formulaGetAllResponse.setDensity(formula.getDensity());
                formulaGetAllResponse.setDescription(formula.getDescription());
                formulaGetAllResponse.setLoss(formula.getLoss());
                formulaGetAllResponse.setCreated_time(formula.getCreated_time());
                formulaGetAllResponse.setModified_time(formula.getModified_time());
                User user = userService.getUserInfo(formula.getCreated_user_id());
                formulaGetAllResponse.setUser_name(user.getFullname());
                listFormulasGetAll.add(formulaGetAllResponse);
            }
            return listFormulasGetAll;
        } catch (Exception e) {
            throw e;
        }
    }

    public List<FormulaGetAllResponse> getAllFormulaByUser_idAndFormula_status(int user_id, String formula_status,
            String jwt) {
        try {
            List<FormulaGetAllResponse> listFormulasGetAll = new ArrayList<>();
            List<Formula> listFormulas = formulaRepository.getAllFormulaByUser_idAndFormula_status(user_id,
                    "%" + formula_status + "%");
            for (int i = 0; i < listFormulas.size(); i++) {
                Formula formula = listFormulas.get(i);
                FormulaGetAllResponse formulaGetAllResponse = new FormulaGetAllResponse();
                formulaGetAllResponse.setFormula_id(formula.getFormula_id());
                formulaGetAllResponse.setProject_id(formula.getProject_id());
                formulaGetAllResponse
                        .setProject_name(
                                projectService.getProjectByProject_id(formula.getProject_id(), jwt).getProject_name());
                formulaGetAllResponse.setFormula_pre_version(formula.getFormula_pre_version());
                formulaGetAllResponse.setFormula_status(formula.getFormula_status());
                formulaGetAllResponse.setFormula_version(formula.getFormula_version());
                formulaGetAllResponse.setFormula_weight(formula.getFormula_weight());
                formulaGetAllResponse.setFormula_cost(formula.getFormula_cost());
                formulaGetAllResponse.setUser_id(formula.getCreated_user_id());
                formulaGetAllResponse.setVolume(formula.getVolume());
                formulaGetAllResponse.setProduct_weight(formula.getProduct_weight());
                formulaGetAllResponse.setDensity(formula.getDensity());
                formulaGetAllResponse.setDescription(formula.getDescription());
                formulaGetAllResponse.setLoss(formula.getLoss());
                formulaGetAllResponse.setCreated_time(formula.getCreated_time());
                formulaGetAllResponse.setModified_time(formula.getModified_time());
                User user = userService.getUserInfo(formula.getCreated_user_id());
                formulaGetAllResponse.setUser_name(user.getFullname());
                listFormulasGetAll.add(formulaGetAllResponse);
            }
            return listFormulasGetAll;
        } catch (Exception e) {
            throw e;
        }
    }

    public FormulaGetResponse getFormulaByFormula_id(int formula_id, String jwt) {
        try {
            Formula formula = formulaRepository.getFormulaByFormula_id(formula_id);
            FormulaGetResponse formulaGetResponse = new FormulaGetResponse();
            formulaGetResponse.setProject_id(formula.getProject_id());
            formulaGetResponse.setFormula_version(formula.getFormula_version());
            formulaGetResponse.setFormula_weight(formula.getFormula_weight());
            formulaGetResponse.setFormula_cost(formula.getFormula_cost());
            formulaGetResponse.setUser_id(formula.getCreated_user_id());
            formulaGetResponse.setVolume(formula.getVolume());
            formulaGetResponse.setProduct_weight(formula.getProduct_weight());
            formulaGetResponse.setDensity(formula.getDensity());
            formulaGetResponse.setDescription(formula.getDescription());
            formulaGetResponse.setLoss(formula.getLoss());
            formulaGetResponse.setDeny_reason(formula.getDeny_reason());
            User user = userService.getUserInfo(formula.getCreated_user_id());
            formulaGetResponse.setUser_name(user.getFullname());
            formulaGetResponse.setCreated_time(formula.getCreated_time());
            formulaGetResponse.setModified_time(formula.getModified_time());
            List<Phase> listPhases = phaseService.getAllPhaseByFormula_id(formula_id);
            List<PhaseGetResponse> listPhaseGetResponse = new ArrayList<>();
            for (int i = 0; i < listPhases.size(); i++) {
                PhaseGetResponse phaseGetResponse = new PhaseGetResponse();
                phaseGetResponse.setPhase_id(listPhases.get(i).getPhase_id());
                phaseGetResponse.setPhase_name(String.valueOf(i + 1));
                phaseGetResponse.setPhase_description(listPhases.get(i).getPhase_description());
                List<MaterialOfPhase> listMaterialOfPhases = materialOfPhaseService
                        .getAllMaterialOfPhase(listPhases.get(i).getPhase_id());
                List<MaterialOfPhaseGetResponse> listMaterialOfPhasesResponse = new ArrayList<>();
                for (int j = 0; j < listMaterialOfPhases.size(); j++) {
                    MaterialOfPhaseGetResponse materialOfPhaseResponse = new MaterialOfPhaseGetResponse();
                    materialOfPhaseResponse.setMop_id(listMaterialOfPhases.get(j).getMop_id());
                    materialOfPhaseResponse.setMaterial_id(listMaterialOfPhases.get(j).getMaterial_id());
                    materialOfPhaseResponse.setMaterial_percent(listMaterialOfPhases.get(j).getMaterial_percent());
                    materialOfPhaseResponse.setMaterial_cost(listMaterialOfPhases.get(j).getMaterial_cost());
                    materialOfPhaseResponse.setMaterial_weight(listMaterialOfPhases.get(j).getMaterial_weight());
                    materialOfPhaseResponse
                            .setMaterial_description(listMaterialOfPhases.get(j).getMaterial_description());
                    listMaterialOfPhasesResponse.add(materialOfPhaseResponse);
                }
                phaseGetResponse.setMaterialOfPhaseGetResponse(listMaterialOfPhasesResponse);
                List<ToolInPhaseResponse> listToolInPhaseResponse = toolInPhaseService
                        .getAllToolInPhaseWithPhase_id(listPhases.get(i).getPhase_id());
                phaseGetResponse.setListToolInPhaseResponse(listToolInPhaseResponse);
                listPhaseGetResponse.add(phaseGetResponse);
            }
            formulaGetResponse.setPhaseGetResponse(listPhaseGetResponse);
            List<TestGetResponse> listTestResponse = new ArrayList<>();
            List<Test> listTest = testService.getAllTestWithFormula_id(formula_id, jwt);
            for (int i = 0; i < listTest.size(); i++) {
                Test test = listTest.get(i);
                listTestResponse.add(testService.getTestWithTest_id(test.getTest_id(), jwt));
            }
            formulaGetResponse.setListTestResponse(listTestResponse);
            String test_status = "Not yet!";
            if (testService.getAllPassTestWithFormula_id(formula_id, jwt) != 0) {
                test_status = "Passed!";
            }
            if (testService.getAllNotPassTestWithFormula_id(formula_id, jwt) != 0) {
                test_status = "Failed!";
            }
            formulaGetResponse.setTest_status(test_status);
            return formulaGetResponse;
        } catch (Exception e) {
            throw e;
        }
    }

    public int createFormula(FormulaCreateRequest formulaCreateRequest, String jwt) {
        try {
            String latest_formula_version = formulaRepository
                    .getLatestFormula_versionOfProject(formulaCreateRequest.getProject_id());
            String formula_version = "";
            if (latest_formula_version == null) {
                formula_version = "1";
            } else if (latest_formula_version.length() == 1) {
                formula_version = String.valueOf(Integer.valueOf(latest_formula_version) + 1);
            } else if (latest_formula_version.length() > 1) {
                formula_version = String.valueOf(Integer.valueOf(latest_formula_version.split("\\.")[0]) + 1);
            }
            Formula formula = new Formula(formulaCreateRequest.getProject_id(),
                    JwtService.getUser_idFromToken(jwt),
                    "none",
                    formula_version,
                    "on process",
                    formulaCreateRequest.getFormula_cost(),
                    formulaCreateRequest.getFormula_weight(),
                    new Date(),
                    formulaCreateRequest.getVolume(),
                    formulaCreateRequest.getProduct_weight(),
                    formulaCreateRequest.getDensity(),
                    formulaCreateRequest.getDescription(),
                    formulaCreateRequest.getLoss());
            formulaRepository.save(formula);
            for (int i = 0; i < formulaCreateRequest.getPhaseCreateRequest().size(); i++) {
                PhaseCreateRequest phaseCreateRequest = formulaCreateRequest.getPhaseCreateRequest().get(i);
                int formula_id = formulaRepository.getLatestFormula_idOfProject(formulaCreateRequest.getProject_id());
                phaseService.createPhase(formula_id, phaseCreateRequest, jwt);
                int newest_phase_id = phaseService.getNewestPhase_idOfFormula(formula_id, jwt);
                for (int j = 0; j < phaseCreateRequest.getMaterialOfPhaseCreateRequest().size(); j++) {
                    materialOfPhaseService.createMaterialOfPhase(newest_phase_id, phaseCreateRequest
                            .getMaterialOfPhaseCreateRequest().get(j), jwt);
                }
                if (phaseCreateRequest.getListTool_id().size() > 0 &&
                        phaseCreateRequest.getListTool_id().get(0) != 0) {
                    for (int j = 0; j < phaseCreateRequest.getListTool_id().size(); j++) {
                        toolInPhaseService
                                .createToolInPhase(new ToolInPhaseRequest(phaseCreateRequest.getListTool_id().get(j),
                                        newest_phase_id), jwt);
                    }
                }
            }
            logService.createLog(new Log(JwtService.getUser_idFromToken(jwt),
                    "FORMULA",
                    "CREATE",
                    String.valueOf(
                            formulaRepository.getLatestFormula_idOfProject(formulaCreateRequest.getProject_id())),
                    new Date()));
            return formulaRepository.getFormula_idByProject_idAndVersion(formula.getProject_id(),
                    formula.getFormula_version());
        } catch (Exception e) {
            throw e;
        }
    }

    public int updateFormula(int formula_id, FormulaUpdateRequest formulaUpdateRequest, String jwt) {
        try {
            Formula updateFormula = formulaRepository.getFormulaByFormula_id(formula_id);
            if (updateFormula != null && !updateFormula.getFormula_status().equals("approved")) {
                updateFormula.setFormula_cost(formulaUpdateRequest.getFormula_cost());
                updateFormula.setFormula_weight(formulaUpdateRequest.getFormula_weight());
                updateFormula.setVolume(formulaUpdateRequest.getVolume());
                updateFormula.setProduct_weight(formulaUpdateRequest.getProduct_weight());
                updateFormula.setDensity(formulaUpdateRequest.getDensity());
                updateFormula.setDescription(formulaUpdateRequest.getDescription());
                updateFormula.setLoss(formulaUpdateRequest.getLoss());
                List<PhaseUpdateRequest> listPhaseUpdate = formulaUpdateRequest.getPhaseUpdateRequest();
                List<Integer> listOldPhase_id = phaseService.getAllPhase_idOfFormula(formula_id, jwt);
                for (int i = 0; i < listPhaseUpdate.size(); i++) {
                    PhaseUpdateRequest phaseUpdateRequest = listPhaseUpdate.get(i);
                    int phase_id = phaseUpdateRequest.getPhase_id();
                    if (phase_id != 0) {
                        phaseService.updatePhase(phaseUpdateRequest, jwt);
                        listOldPhase_id.remove(Integer.valueOf(phase_id));
                        toolInPhaseService.deleteAllToolInPhaseByPhase_id(phase_id, jwt);
                    } else if (phase_id == 0) {
                        PhaseCreateRequest phaseCreateRequest = new PhaseCreateRequest();
                        phaseCreateRequest.setPhase_description(phaseUpdateRequest.getPhase_description());
                        phaseCreateRequest.setPhase_index(phaseCreateRequest.getPhase_index());
                        List<MaterialOfPhaseUpdateRequest> listMaterialUpdateInput = phaseUpdateRequest
                                .getMaterialOfPhaseUpdateRequest();
                        phaseService.createPhase(formula_id, phaseCreateRequest, jwt);
                        phase_id = phaseService.getNewestPhase_idOfFormula(formula_id, jwt);
                        for (int j = 0; j < listMaterialUpdateInput.size(); j++) {
                            MaterialOfPhaseUpdateRequest materialOfPhaseUpdate = listMaterialUpdateInput.get(j);
                            MaterialOfPhaseCreateRequest materialOfPhaseCreate = new MaterialOfPhaseCreateRequest();
                            materialOfPhaseCreate.setMaterial_id(materialOfPhaseUpdate.getMaterial_id());
                            materialOfPhaseCreate.setMaterial_cost(materialOfPhaseUpdate.getMaterial_cost());
                            materialOfPhaseCreate.setMaterial_percent(materialOfPhaseUpdate.getMaterial_percent());
                            materialOfPhaseCreate.setMaterial_weight(materialOfPhaseUpdate.getMaterial_weight());
                            materialOfPhaseCreate
                                    .setMaterial_description(materialOfPhaseUpdate.getMaterial_description());
                            materialOfPhaseService.createMaterialOfPhase(phase_id, materialOfPhaseCreate, jwt);
                        }
                    }
                    if (listPhaseUpdate.get(i).getListTool_id().size() > 0 &&
                            listPhaseUpdate.get(i).getListTool_id().get(0) != 0) {
                        for (int j = 0; j < listPhaseUpdate.get(i).getListTool_id().size(); j++) {
                            toolInPhaseService.createToolInPhase(
                                    new ToolInPhaseRequest(
                                            listPhaseUpdate.get(i).getListTool_id().get(j),
                                            phase_id),
                                    jwt);
                        }
                    }
                }
                if (listOldPhase_id.size() > 0) {
                    for (int i = 0; i < listOldPhase_id.size(); i++) {
                        phaseService.deletePhase(listOldPhase_id.get(i), jwt);
                    }
                }
                formulaRepository.save(updateFormula);
                logService.createLog(new Log(JwtService.getUser_idFromToken(jwt),
                        "FORMULA",
                        "UPDATE",
                        String.valueOf(formula_id),
                        new Date()));
                return formulaRepository.getFormula_idByProject_idAndVersion(updateFormula.getProject_id(),
                        updateFormula.getFormula_version());
            }
            return 0;
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean setFormula_status(int formula_id, String status, String jwt) {
        try {
            Formula formula = formulaRepository.getFormulaByFormula_id(formula_id);
            if (formula != null) {
                if (!status.equals("on process") && !status.equals("approved")
                        && !status.equals("pending") && !status.equals("canceled")) {
                    return false;
                }
                if (status.equals("approved") || status.equals("on process")) {
                    if (!formula.getFormula_status().equals("pending")) {
                        return false;
                    }
                }
                formula.setFormula_status(status);
                formula.setModified_time(new Date());
                formulaRepository.save(formula);
                if (status.equals("approved")) {
                    notificationService.createNotification(new Notification(
                            formula.getCreated_user_id(),
                            "Thông qua!",
                            "Công thức phiên bản " + formula.getFormula_version() + " thuộc dự án " +
                                    projectService.getProjectByProject_id(formula.getProject_id(), jwt)
                                            .getProject_name()
                                    +
                                    " đã được chấp thuận!",
                            new Date()));
                    logService.createLog(new Log(JwtService.getUser_idFromToken(jwt),
                            "FORMULA",
                            "ACCEPT",
                            String.valueOf(formula_id),
                            new Date()));
                } else if (status.equals("on process")) {
                    notificationService.createNotification(new Notification(
                            formula.getCreated_user_id(),
                            "Từ chối!",
                            "Công thức phiên bản " + formula.getFormula_version() + " thuộc dự án " +
                                    projectService.getProjectByProject_id(formula.getProject_id(), jwt)
                                            .getProject_name()
                                    +
                                    " đã bị từ chối!",
                            new Date()));
                    logService.createLog(new Log(JwtService.getUser_idFromToken(jwt),
                            "FORMULA",
                            "DENY",
                            String.valueOf(formula_id),
                            new Date()));
                } else if (status.equals("pending")) {
                    logService.createLog(new Log(JwtService.getUser_idFromToken(jwt),
                            "FORMULA",
                            "SUBMIT",
                            String.valueOf(formula_id),
                            new Date()));
                }
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public int upgradeFormula(int formula_id, FormulaUpgradeRequest formulaUpgradeRequest, String jwt) {
        try {
            Formula formula = formulaRepository.getFormulaByFormula_id(formula_id);
            if (formula != null) {
                Formula newFormula = new Formula(formula.getProject_id(),
                        JwtService.getUser_idFromToken(jwt),
                        formula.getFormula_version(),
                        formula.getFormula_version() + "." + String
                                .valueOf(formulaRepository.totalFormulaHaveMatchPreVersion(formula.getProject_id(),
                                        formula.getFormula_version()) + 1),
                        "on process",
                        formulaUpgradeRequest.getFormula_cost(),
                        formulaUpgradeRequest.getFormula_weight(),
                        new Date(),
                        formulaUpgradeRequest.getVolume(),
                        formulaUpgradeRequest.getProduct_weight(),
                        formulaUpgradeRequest.getDensity(),
                        formulaUpgradeRequest.getDescription(),
                        formulaUpgradeRequest.getLoss());
                formulaRepository.save(newFormula);
                for (int i = 0; i < formulaUpgradeRequest.getPhaseCreateRequest().size(); i++) {
                    PhaseCreateRequest phaseCreateRequest = formulaUpgradeRequest.getPhaseCreateRequest().get(i);
                    phaseService.createPhase(formulaRepository.getLatestFormula_idOfProject(formula.getProject_id()),
                            phaseCreateRequest, jwt);
                    int newest_phase_id = phaseService.getNewestPhase_idOfFormula(
                            formulaRepository.getFormula_idByProject_idAndVersion(newFormula.getProject_id(),
                                    newFormula.getFormula_version()),
                            jwt);
                    for (int j = 0; j < phaseCreateRequest.getMaterialOfPhaseCreateRequest().size(); j++) {
                        materialOfPhaseService.createMaterialOfPhase(newest_phase_id,
                                phaseCreateRequest.getMaterialOfPhaseCreateRequest().get(j), jwt);
                    }
                    if (phaseCreateRequest.getListTool_id().size() > 0 &&
                            phaseCreateRequest.getListTool_id().get(0) != 0) {
                        for (int j = 0; j < phaseCreateRequest.getListTool_id().size(); j++) {
                            toolInPhaseService
                                    .createToolInPhase(
                                            new ToolInPhaseRequest(phaseCreateRequest.getListTool_id().get(j),
                                                    newest_phase_id),
                                            jwt);
                        }
                    }
                }
                logService.createLog(new Log(JwtService.getUser_idFromToken(jwt),
                        "FORMULA",
                        "UPGRADE",
                        String.valueOf(formula_id),
                        new Date()));
                return formulaRepository.getFormula_idByProject_idAndVersion(newFormula.getProject_id(),
                        newFormula.getFormula_version());
            } else {
                return 0;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean denyFormulaWithReason(int formula_id, String deny_reason, String jwt) {
        try {
            Formula formula = formulaRepository.getFormulaByFormula_id(formula_id);
            if (formula != null) {
                if (formula.getFormula_status().equals("pending")) {
                    formula.setDeny_reason(deny_reason);
                    formula.setFormula_status("on process");
                    formula.setModified_time(new Date());
                    formulaRepository.save(formula);
                    notificationService.createNotification(new Notification(
                            formula.getCreated_user_id(),
                            "Từ chối!",
                            "Công thức phiên bản " + formula.getFormula_version() + " thuộc dự án " +
                                    projectService.getProjectByProject_id(formula.getProject_id(), jwt)
                                            .getProject_name()
                                    +
                                    " đã bị từ chối!",
                            new Date()));
                    logService.createLog(new Log(JwtService.getUser_idFromToken(jwt),
                            "FORMULA",
                            "DENY",
                            String.valueOf(formula_id),
                            new Date()));
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            throw e;
        }
    }

    public FormulaStatisticsResponse getFormulaStatisticsWithMonthAndYear(String jwt, int month, int year) {
        try {
            return new FormulaStatisticsResponse(formulaRepository.getTotalFormulaWithMonthAndYear(month, year),
                    formulaRepository.getTotalFormulaPendingWithMonthAndYear(month, year),
                    formulaRepository.getTotalFormulaOnProcessWithMonthAndYear(month, year),
                    formulaRepository.getTotalFormulaApprovedWithMonthAndYear(month, year));
        } catch (Exception e) {
            throw e;
        }
    }

    public List<FormulaStatisticsFromDateToDateResponse> getFormulaStatisticsFromDateToDate(String jwt,
            String from_date,
            String to_date) {
        try {
            LocalDate start = LocalDate.parse(from_date.split(" ")[0]);
            LocalDate end = LocalDate.parse(to_date.split(" ")[0]);
            List<LocalDate> totalDates = new ArrayList<>();
            while (!start.isAfter(end)) {
                totalDates.add(start);
                start = start.plusDays(1);
            }
            List<FormulaStatisticsFromDateToDateResponse> listResponses = new ArrayList<>();
            for (int i = 0; i < totalDates.size(); i++) {
                if (formulaRepository.getTotalFormulaFromDateToDate(totalDates.get(i) + " 00:00:00",
                        totalDates.get(i) + " 23:59:59") != 0) {
                    listResponses.add(new FormulaStatisticsFromDateToDateResponse(
                            totalDates.get(i).toString(),
                            formulaRepository.getTotalFormulaFromDateToDate(totalDates.get(i) + " 00:00:00",
                                    totalDates.get(i) + " 23:59:59"),
                            formulaRepository.getTotalFormulaPendingFromDateToDate(totalDates.get(i) + " 00:00:00",
                                    totalDates.get(i) + " 23:59:59"),
                            formulaRepository.getTotalFormulaOnProcessFromDateToDate(totalDates.get(i) + " 00:00:00",
                                    totalDates.get(i) + " 23:59:59"),
                            formulaRepository.getTotalFormulaApprovedFromDateToDate(totalDates.get(i) + " 00:00:00",
                                    totalDates.get(i) + " 23:59:59")));
                }
            }
            return listResponses;
        } catch (Exception e) {
            throw e;
        }
    }

    public FormulaStatisticsResponse getFormulaStatisticsOfAllTime(String jwt) {
        try {
            return new FormulaStatisticsResponse(formulaRepository.getTotalFormula(),
                    formulaRepository.getTotalFormulaPending(),
                    formulaRepository.getTotalFormulaOnProcess(),
                    formulaRepository.getTotalFormulaApproved());
        } catch (Exception e) {
            throw e;
        }
    }
}
