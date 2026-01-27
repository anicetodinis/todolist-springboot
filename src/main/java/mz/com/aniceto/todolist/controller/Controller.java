package mz.com.aniceto.todolist.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api")
public class Controller {
    
    /**
     * Metodos de acesso a uma requisição HTTP
     * GET - buscar informação
     * POST - Adicionar informação
     * PUT - Actualizar toda a informação
     * DELETE - Remover informação
     * PATCH - Actualizar parte da informação
     * 
     */
    @GetMapping("/hello")
    public String home(){
        return "Hello Word";
    }
}
