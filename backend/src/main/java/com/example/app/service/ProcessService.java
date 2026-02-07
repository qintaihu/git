package com.example.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.runtime.TaskRuntime;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessService {

    private final ProcessRuntime processRuntime;
    private final TaskRuntime taskRuntime;

    public ProcessInstance startProcess(String processDefinitionKey) {
        log.info("Starting process: {}", processDefinitionKey);
        ProcessInstance instance = processRuntime.start(
                processRuntime.processDefinitionQuery()
                        .processDefinitionKey(processDefinitionKey)
                        .getResultMetadata()
                        .getOnPage(0)
                        .getContent()
                        .get(0)
        );
        log.info("Process started with id: {}", instance.getId());
        return instance;
    }

    public List<Task> getMyTasks() {
        log.info("Fetching user's tasks");
        return taskRuntime.tasks(
                taskRuntime.taskQuery()
                        .getResultMetadata()
                        .getOnPage(0)
        ).getContent();
    }

    public void completeTask(String taskId) {
        log.info("Completing task: {}", taskId);
        Task task = taskRuntime.task(taskId);
        taskRuntime.claim(task);
        taskRuntime.complete(task);
        log.info("Task completed: {}", taskId);
    }

}
