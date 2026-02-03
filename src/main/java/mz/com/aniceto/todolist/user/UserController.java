package mz.com.aniceto.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 
 * Modificadores de acesso
 * public - Acesso livre
 * private - Acesso restrito a classe
 * protected - Acesso restrito a classe e suas subclasses (mesmo pacote ou fora do pacote)
 */

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository userRepository;
    
    /** 
     * Tipo de metodos
     * void - não retorna nada
     * String - retorna uma cadeia de caracteres
     * int - retorna um numero inteiro
     * boolean - retorna verdadeiro ou falso
     * Object - retorna um objecto
     * date - retorna uma data
     * double - retorna um numero com casas decimais
     * char - retorna um caractere
     * etc
     */
    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel){
        var user = this.userRepository.findByUsername(userModel.getUsername());
        if(user != null){
            System.out.println("Utilizador já foi criado");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Utilizador já existe");
        }
        var userCreated = this.userRepository.save(userModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}
