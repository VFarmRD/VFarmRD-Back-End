package com.example.vfarmrdbackend.payload.formula;

public class FormulaStatisticsResponse {
    private int total_formula;
    private int total_formula_pending;
    private int total_formula_on_progress;
    private int total_formula_approved;

    public FormulaStatisticsResponse() {
    }

    public FormulaStatisticsResponse(int total_formula, int total_formula_pending, int total_formula_on_progress,
            int total_formula_approved) {
        this.total_formula = total_formula;
        this.total_formula_pending = total_formula_pending;
        this.total_formula_on_progress = total_formula_on_progress;
        this.total_formula_approved = total_formula_approved;
    }

    public int getTotal_formula() {
        return total_formula;
    }

    public void setTotal_formula(int total_formula) {
        this.total_formula = total_formula;
    }

    public int getTotal_formula_pending() {
        return total_formula_pending;
    }

    public void setTotal_formula_pending(int total_formula_pending) {
        this.total_formula_pending = total_formula_pending;
    }

    public int getTotal_formula_on_progress() {
        return total_formula_on_progress;
    }

    public void setTotal_formula_on_progress(int total_formula_on_progress) {
        this.total_formula_on_progress = total_formula_on_progress;
    }

    public int getTotal_formula_approved() {
        return total_formula_approved;
    }

    public void setTotal_formula_approved(int total_formula_approved) {
        this.total_formula_approved = total_formula_approved;
    }

}
