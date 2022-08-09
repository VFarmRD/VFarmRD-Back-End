package com.example.vfarmrdbackend.repository.task;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.vfarmrdbackend.model.task.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {
        @Query(value = "select * from tasks t where t.task_id = :task_id", nativeQuery = true)
        Task getTaskByTask_id(@Param("task_id") int task_id);

        @Query(value = "select * from tasks t where t.task_status like :task_status ", nativeQuery = true)
        List<Task> getAllTasksWithTask_status(@Param("task_status") String task_status);

        @Query(value = "select * from tasks t where t.user_id = :user_id and t.task_status like :task_status ", nativeQuery = true)
        List<Task> getAllTasksWithUser_id(@Param("user_id") int user_id, @Param("task_status") String task_status);

        @Query(value = "select * from tasks t where t.project_id = :project_id and t.task_status like :task_status ", nativeQuery = true)
        List<Task> getAllTasksWithProject_id(@Param("project_id") int project_id,
                        @Param("task_status") String task_status);

        @Query(value = "select * from tasks t where t.project_id = :project_id and t.user_id = :user_id and t.task_status like :task_status ;", nativeQuery = true)
        List<Task> getAllTasksWithProject_idAndUser_id(@Param("project_id") int project_id,
                        @Param("user_id") int user_id, @Param("task_status") String task_status);

        @Query(value = "select * from tasks t where t.project_id = :project_id order by t.task_id desc limit 1", nativeQuery = true)
        Task getNewestTaskByProject_id(@Param("project_id") int project_id);

        @Query(value = "SELECT COUNT(*) FROM tasks t;", nativeQuery = true)
        int getTotalTask();

        @Query(value = "SELECT COUNT(*) FROM tasks t where t.created_date between :from_date and :to_date;", nativeQuery = true)
        int getTotalTaskFromDateToDate(@Param("from_date") String from_date,
                        @Param("to_date") String to_date);

        @Query(value = "SELECT COUNT(*) FROM tasks t where month(t.created_date) = :month and year(t.created_date) = :year ;", nativeQuery = true)
        int getTotalTaskWithMonthAndYear(@Param("month") int month, @Param("year") int year);

        @Query(value = "SELECT COUNT(*) FROM tasks t where t.task_status = 'doing';", nativeQuery = true)
        int getTotalTaskDoing();

        @Query(value = "SELECT COUNT(*) FROM tasks t where (t.created_date between :from_date and :to_date) and t.task_status = 'doing';", nativeQuery = true)
        int getTotalTaskDoingFromDateToDate(@Param("from_date") String from_date,
                        @Param("to_date") String to_date);

        @Query(value = "SELECT COUNT(*) FROM tasks t where month(t.created_date) = :month and year(t.created_date) = :year and t.task_status = 'doing';", nativeQuery = true)
        int getTotalTaskDoingWithMonthAndYear(@Param("month") int month, @Param("year") int year);

        @Query(value = "SELECT COUNT(*) FROM tasks t where t.project_id in (select distinct f.project_id from formulas f where f.formula_id in (select p.formula_id from products p));", nativeQuery = true)
        int getTotalTaskDone();

        @Query(value = "SELECT COUNT(*) FROM tasks t where (t.created_date between :from_date and :to_date) and t.project_id in (select distinct f.project_id from formulas f where f.formula_id in (select p.formula_id from products p));", nativeQuery = true)
        int getTotalTaskDoneFromDateToDate(@Param("from_date") String from_date,
                        @Param("to_date") String to_date);

        @Query(value = "SELECT COUNT(*) FROM tasks t where month(t.created_date) = :month and year(t.created_date) = :year and t.project_id in (select distinct f.project_id from formulas f where f.formula_id in (select p.formula_id from products p));", nativeQuery = true)
        int getTotalTaskDoneWithMonthAndYear(@Param("month") int month, @Param("year") int year);

        @Query(value = "SELECT COUNT(*) FROM tasks t where t.task_status = 'overtime';", nativeQuery = true)
        int getTotalTaskOvertime();

        @Query(value = "SELECT COUNT(*) FROM tasks t where (t.created_date between :from_date and :to_date) and t.task_status = 'overtime';", nativeQuery = true)
        int getTotalTaskOvertimeFromDateToDate(@Param("from_date") String from_date,
                        @Param("to_date") String to_date);

        @Query(value = "SELECT COUNT(*) FROM tasks t where month(t.created_date) = :month and year(t.created_date) = :year and t.task_status = 'overtime';", nativeQuery = true)
        int getTotalTaskOvertimeWithMonthAndYear(@Param("month") int month, @Param("year") int year);
}
