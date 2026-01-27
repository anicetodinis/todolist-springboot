package mz.com.aniceto.todolist.user;

import lombok.Data;

@Data  //Gerando automaticamente getters e setters atraves da dependencia lombok
public class UserModel {
    private String username;
    private String name;
    private String password;
    private Integer age;

    
}
