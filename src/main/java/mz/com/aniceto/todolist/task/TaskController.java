package mz.com.aniceto.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import mz.com.aniceto.todolist.utils.Utils;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired // para o spring gerenciar a instancia do repositorio
    private ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create (@RequestBody TaskModel taskModel, HttpServletRequest request){
        System.out.println("Create task");
        var idUser = request.getAttribute("idUser");
        taskModel.setIdUser((UUID) idUser);

        var currentDate = LocalDateTime.now();

        if(currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de inicio / data de termino deve ser maior que a data actual");
        }


        if (taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de inicio nao deve ser maior que a data de termino");
        }

        var task =this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        var taskList = this.taskRepository.findByIdUser((UUID) idUser);
        return taskList; 
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable UUID id, @RequestBody TaskModel taskModel, HttpServletRequest request) {
        var task = this.taskRepository.findById(id).orElse(null);
        //tarefa nao encontrada
        if (task == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa nao encontrada");
        }

        //user sem permissao
        var idUser = request.getAttribute("idUser");
        if(!task.getIdUser().equals(idUser)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario nao autorizado");
        }

        Utils.copyNonNullPropertyNames(taskModel, task);

        return ResponseEntity.ok().body(this.taskRepository.save(task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable UUID id, @RequestBody TaskModel taskModel, HttpServletRequest request) {
        var task = this.taskRepository.findById(id).orElse(null);
        //tarefa nao encontrada
        if (task == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa nao encontrada");
        }

         //user sem permissao
        var idUser = request.getAttribute("idUser");
        if(!task.getIdUser().equals(idUser)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario nao autorizado");
        }

        this.taskRepository.delete(task);

        return ResponseEntity.ok().body("Task eliminada com sucesso");
    }
    
}
