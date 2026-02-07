package com.example.app.controller;

import com.example.app.service.ProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
// import org.activiti.api.process.model.ProcessInstance;
// import org.activiti.api.task.model.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/process")
@RequiredArgsConstructor
public class ProcessController {

    private final ProcessService processService;

    // Activiti workflow endpoints - temporarily disabled
    // Will be re-enabled when Activiti is properly configured

    // @PostMapping("/start")
    // public ResponseEntity<?> startProcess(@RequestBody StartProcessRequest request) {
    //     try {
    //         ProcessInstance instance = processService.startProcess(request.getProcessDefinitionKey());
    //         Map<String, Object> response = new HashMap<>();
    //         response.put("code", 200);
    //         response.put("message", "Process started successfully");
    //         response.put("data", new ProcessInstanceResponse(instance.getId(), instance.getName()));
    //         return ResponseEntity.ok(response);
    //     } catch (Exception e) {
    //         log.error("Failed to start process: {}", e.getMessage());
    //         Map<String, Object> response = new HashMap<>();
    //         response.put("code", 500);
    //         response.put("message", "Failed to start process");
    //         response.put("data", null);
    //         return ResponseEntity.status(500).body(response);
    //     }
    // }

    // @GetMapping("/my-tasks")
    // public ResponseEntity<?> getMyTasks() {
    //     try {
    //         List<Task> tasks = processService.getMyTasks();
    //         Map<String, Object> response = new HashMap<>();
    //         response.put("code", 200);
    //         response.put("message", "Tasks retrieved successfully");
    //         response.put("data", tasks);
    //         return ResponseEntity.ok(response);
    //     } catch (Exception e) {
    //         log.error("Failed to retrieve tasks: {}", e.getMessage());
    //         Map<String, Object> response = new HashMap<>();
    //         response.put("code", 500);
    //         response.put("message", "Failed to retrieve tasks");
    //         response.put("data", null);
    //         return ResponseEntity.status(500).body(response);
    //     }
    // }

    // @PostMapping("/task/{taskId}/complete")
    // public ResponseEntity<?> completeTask(@PathVariable String taskId) {
    //     try {
    //         processService.completeTask(taskId);
    //         Map<String, Object> response = new HashMap<>();
    //         response.put("code", 200);
    //         response.put("message", "Task completed successfully");
    //         response.put("data", null);
    //         return ResponseEntity.ok(response);
    //     } catch (Exception e) {
    //         log.error("Failed to complete task: {}", e.getMessage());
    //         Map<String, Object> response = new HashMap<>();
    //         response.put("code", 500);
    //         response.put("message", "Failed to complete task");
    //         response.put("data", null);
    //         return ResponseEntity.status(500).body(response);
    //     }
    // }

    // public static class StartProcessRequest {
    //     private String processDefinitionKey;
    //
    //     public StartProcessRequest() {}
    //     public String getProcessDefinitionKey() { return processDefinitionKey; }
    //     public void setProcessDefinitionKey(String key) { this.processDefinitionKey = key; }
    // }

    // public static class ProcessInstanceResponse {
    //     private String id;
    //     private String name;
    //
    //     public ProcessInstanceResponse(String id, String name) {
    //         this.id = id;
    //         this.name = name;
    //     }
    //
    //     public String getId() { return id; }
    //     public String getName() { return name; }
    // }

}
