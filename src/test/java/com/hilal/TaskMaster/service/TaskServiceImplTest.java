package com.hilal.TaskMaster.service;

import com.hilal.TaskMaster.entity.*;
import com.hilal.TaskMaster.entity.dto.TaskRequestDto;
import com.hilal.TaskMaster.entity.dto.TaskResponseDto;
import com.hilal.TaskMaster.exception.customExceptions.BadRequestException;
import com.hilal.TaskMaster.exception.customExceptions.ResourceNotFoundException;
import com.hilal.TaskMaster.repository.TaskRepository;
import com.hilal.TaskMaster.service.impl.TaskServiceImpl;
import com.hilal.TaskMaster.service.impl.TeamServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TeamServiceImpl teamService;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    public void test_createtask_valid(){
        TaskRequestDto taskRequestDto = TaskRequestDto.builder()
                .title("test").status(Status.CANCELLED).priority(Priority.URGENT)
                .build();
        Users user = Users.builder()
                .userId(1L)
                .email("testuser")
                .userName("testuser")
                .age(21)
                .build();

        Mockito.when(taskRepository.save(Mockito.any(Tasks.class)))
                .thenAnswer(invocationOnMock -> {
                    Tasks t = invocationOnMock.getArgument(0);
                    t.setTaskId(1L);
                    return t;
                });
        TaskResponseDto taskResponse = taskService.createTask(taskRequestDto,user);
        Assertions.assertEquals(taskResponse.getTitle(),"test");
        Assertions.assertEquals(taskResponse.getCreatedBy(),user.getUserName());
    }

    @Test
    public void test_createtask_invalid(){
        Assertions.assertThrows(BadRequestException.class,()->{
            TaskResponseDto taskResponseDto = taskService.createTask(null,null);
        });
    }

    @Test
    public void test_gettaskbyid_valid(){
        Tasks task = Tasks.builder()
                        .taskId(1L)
                        .title("test")
                        .status(Status.CANCELLED)
                        .build();
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        Tasks t = taskService.getTaskById(1L);
        Assertions.assertEquals(t.getTaskId(),1L);
        Assertions.assertEquals(t.getTitle(),task.getTitle());
    }

    @Test
    public void test_gettaskbyid_invalid(){
        Assertions.assertThrows(BadRequestException.class,()->{
            Tasks t = taskService.getTaskById(0L);
        });
    }

    @Test
    public void test_gettaskbyid_notfound(){
        Assertions.assertThrows(ResourceNotFoundException.class,()->{
            Tasks t = taskService.getTaskById(1L);
        });
    }

    @Test
    public void test_gettaskdtobyid_valid(){
        Users user = Users.builder()
                .userId(1L)
                .email("testuser")
                .userName("testuser")
                .age(21)
                .build();
        Tasks task = Tasks.builder()
                .taskId(1L)
                .title("test")
                .status(Status.CANCELLED)
                .createdBy(user)
                .build();
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        TaskResponseDto taskResponseDto = taskService.getTaskDTOById(1L);
        Assertions.assertEquals(taskResponseDto.getTaskId(),1L);
        Assertions.assertEquals(taskResponseDto.getTitle(),task.getTitle());
        Assertions.assertEquals(taskResponseDto.getCreatedBy(),user.getUserName());
    }

    @Test
    public void test_gettaskdtobyid_notfound(){
        Assertions.assertThrows(ResourceNotFoundException.class,()->{
            TaskResponseDto taskResponseDto = taskService.getTaskDTOById(1L);
        });
    }

    @Test
    public void test_gettaskdtobyid_invalid(){
        Assertions.assertThrows(BadRequestException.class,()->{
            TaskResponseDto taskResponseDto = taskService.getTaskDTOById(0L);
        });
    }

    @Test
    public void test_assigntask_valid(){
        Users user = Users.builder()
                .userId(1L)
                .email("testuser")
                .userName("testuser")
                .age(21)
                .build();
        Tasks task = Tasks.builder()
                .taskId(1L)
                .title("test")
                .status(Status.CANCELLED)
                .createdBy(user)
                .build();
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        Mockito.when(taskRepository.save(Mockito.any(Tasks.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        TaskResponseDto taskResponseDto = taskService.assignTask(1L,user);
        Assertions.assertEquals(taskResponseDto.getTaskId(),1L);
        Assertions.assertEquals(taskResponseDto.getAssignedUser(),user.getUserName());
    }

    @Test
    public void test_assigntask_notfound(){
        Users user = Users.builder()
                .userId(1L)
                .email("testuser")
                .userName("testuser")
                .age(21)
                .build();
        Assertions.assertThrows(ResourceNotFoundException.class,()->{
            TaskResponseDto taskResponseDto = taskService.assignTask(1L,user);
        });
    }

    @Test
    public void test_assigntask_invalid(){
        Users user = Users.builder()
                .userId(1L)
                .email("testuser")
                .userName("testuser")
                .age(21)
                .build();
        Assertions.assertThrows(BadRequestException.class,()->{
            TaskResponseDto taskResponseDto = taskService.assignTask(0L,user);
        });
    }

    @Test
    public void test_assigntask_usernull(){
        Assertions.assertThrows(BadRequestException.class,()->{
            TaskResponseDto taskResponseDto = taskService.assignTask(1L,null);
        });
    }

    @Test
    public void test_updatetask_valid(){
        Users user = Users.builder()
                .userId(1L)
                .email("testuser")
                .userName("testuser")
                .age(21)
                .build();
        TaskRequestDto taskRequestDto = TaskRequestDto.builder()
                .title("updatedTitle").status(Status.IN_PROGRESS).priority(Priority.HIGH)
                .build();
        Tasks task = Tasks.builder()
                .taskId(1L)
                .title("test")
                .status(Status.CANCELLED)
                .createdBy(user)
                .build();
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        Mockito.when(taskRepository.save(Mockito.any(Tasks.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        TaskResponseDto taskResponseDto = taskService.updateTask(1L,taskRequestDto);
        Assertions.assertEquals(taskResponseDto.getTaskId(),1L);
        Assertions.assertEquals(taskResponseDto.getTitle(),"updatedTitle");
        Assertions.assertEquals(taskResponseDto.getStatus(),Status.IN_PROGRESS);
    }

    @Test
    public void test_updatetask_notfound() {
        TaskRequestDto taskRequestDto = TaskRequestDto.builder()
                .title("updatedTitle").status(Status.IN_PROGRESS).priority(Priority.HIGH)
                .build();
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            TaskResponseDto taskResponseDto = taskService.updateTask(1L, taskRequestDto);
        });
    }

    @Test
    public void test_updatetask_invalid() {
        TaskRequestDto taskRequestDto = TaskRequestDto.builder()
                .title("updatedTitle").status(Status.IN_PROGRESS).priority(Priority.HIGH)
                .build();
        Assertions.assertThrows(BadRequestException.class, () -> {
            TaskResponseDto taskResponseDto = taskService.updateTask(0L, taskRequestDto);
        });
    }

    @Test
    public void test_updatetask_requestnull() {
        Assertions.assertThrows(BadRequestException.class, () -> {
            TaskResponseDto taskResponseDto = taskService.updateTask(1L, null);
        });
    }

    @Test
    public void test_deletetask_valid(){
        Users user = Users.builder()
                .userId(1L)
                .email("testuser")
                .userName("testuser")
                .age(21)
                .build();
        Tasks task = Tasks.builder()
                .taskId(1L)
                .title("test")
                .status(Status.CANCELLED)
                .createdBy(user)
                .build();
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        String response = taskService.deleteTask(1L);
        Assertions.assertEquals(response,"Task with ID: 1 deleted");
    }

    @Test
    public void test_deletetask_notfound() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            String response = taskService.deleteTask(1L);
        });
    }

    @Test
    public void test_deletetask_invalid() {
        Assertions.assertThrows(BadRequestException.class, () -> {
            String response = taskService.deleteTask(0L);
        });
    }


    @Test
    public void testAddTaskToTeam_valid() {
        Users user = Users.builder().userId(1L).userName("creator").build();
        Teams team = Teams.builder().teamId(1L).teamName("TeamA").createdBy(user).build();
        Tasks task = Tasks.builder().taskId(1L).createdBy(user).title("t").build();

        Mockito.when(teamService.getTeamById(1L)).thenReturn(team);
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        Mockito.when(taskRepository.save(Mockito.any(Tasks.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TaskResponseDto dto = taskService.addTaskToTeam(1L, 1L);
        Assertions.assertEquals(1L, dto.getTaskId());
        Assertions.assertEquals("TeamA", dto.getTeamName());
    }

    @Test
    public void testGetTasksByStatus() {
        Users user = Users.builder().userId(1L).userName("creator").build();
        Tasks t1 = Tasks.builder().taskId(1L).status(Status.CANCELLED).title("a").createdBy(user).build();
        Tasks t2 = Tasks.builder().taskId(2L).status(Status.CANCELLED).title("b").createdBy(user).build();
        Mockito.when(taskRepository.findByStatus(Status.CANCELLED)).thenReturn(Arrays.asList(t1, t2));

        List<TaskResponseDto> result = taskService.getTasksByStatus(Status.CANCELLED);
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.stream().allMatch(r -> r.getStatus() == Status.CANCELLED));
    }

    @Test
    public void testGetAllTasksForUser() {
        Users user = Users.builder().userId(1L).userName("u").build();
        Tasks t1 = Tasks.builder().taskId(1L).createdBy(user).build();
        Mockito.when(taskRepository.findByAssignedToUserIdOrCreatedByUserId(1L, 1L)).thenReturn(Arrays.asList(t1));

        List<TaskResponseDto> result = taskService.getAllTasksForUser(user);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    public void testMarkTaskAsComplete() {
        Users user = Users.builder().userId(1L).userName("creator").build();
        Tasks task = Tasks.builder().taskId(1L).status(Status.IN_PROGRESS).createdBy(user).build();
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        Mockito.when(taskRepository.save(Mockito.any(Tasks.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TaskResponseDto dto = taskService.markTaskAsComplete(1L);
        Assertions.assertEquals(Status.COMPLETED, dto.getStatus());
        Assertions.assertNotNull(dto.getUpdatedAt());
    }

    @Test
    public void testUnassignTask() {
        Users assignee = Users.builder().userId(2L).userName("assignee").build();
        Tasks task = Tasks.builder().taskId(1L).assignedTo(assignee).createdBy(assignee).build();
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        Mockito.when(taskRepository.save(Mockito.any(Tasks.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TaskResponseDto dto = taskService.unassignTask(1L);
        Assertions.assertNull(dto.getAssignedUser());
    }

    @Test
    public void testRemoveTaskFromTeam() {
        Users user = Users.builder().userId(1L).userName("creator").build();
        Teams team = Teams.builder().teamId(1L).teamName("TeamA").createdBy(user).build();
        Tasks task = Tasks.builder().taskId(1L).team(team).createdBy(user).build();
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        Mockito.when(taskRepository.save(Mockito.any(Tasks.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TaskResponseDto dto = taskService.removeTaskFromTeam(1L);
        Assertions.assertNull(dto.getTeamName());
    }

    @Test
    public void testGetAllTasksForTeam() {
        Users user = Users.builder().userId(1L).userName("creator").build();
        Tasks t1 = Tasks.builder().taskId(1L).createdBy(user).build();
        Mockito.when(taskRepository.findByTeamTeamId(1L)).thenReturn(Arrays.asList(t1));
        List<TaskResponseDto> result = taskService.getAllTasksForTeam(1L);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    public void testGetAllOverdueTasks() {
        Users user = Users.builder().userId(1L).userName("creator").build();
        Tasks t1 = Tasks.builder().taskId(1L).createdBy(user).dueDate(LocalDateTime.now().minusDays(1)).status(Status.IN_PROGRESS).build();
        Mockito.when(taskRepository.findByDueDateBeforeAndStatusNot(Mockito.any(LocalDateTime.class), Mockito.eq(Status.COMPLETED)))
                .thenReturn(Arrays.asList(t1));
        List<TaskResponseDto> result = taskService.getAllOverdueTasks();
        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.get(0).getDueDate().isBefore(LocalDateTime.now()));
    }
}
