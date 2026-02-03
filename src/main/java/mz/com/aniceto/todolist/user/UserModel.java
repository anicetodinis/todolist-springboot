package mz.com.aniceto.todolist.user;


import java.time.LocalDate;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data  //Gerando automaticamente getters e setters atraves da dependencia lombok
@Entity(name = "tb_users")
public class UserModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String username;
    private String name;
    private String password;
    private Integer age;

    @CreationTimestamp
    private LocalDate createdAt;
}
