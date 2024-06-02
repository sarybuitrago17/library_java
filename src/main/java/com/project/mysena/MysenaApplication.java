package com.project.mysena;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class MysenaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MysenaApplication.class, args);
	}
            @GetMapping("/")
    public String index() {
        return "index"; // Devuelve el nombre del archivo HTML sin la extensión
    }
        @GetMapping("/registro")
    public String registrar() {
        return "register"; // Nombre del archivo HTML del formulario de registro
    }
                @GetMapping("/welcome")
    public String welcome() {
        return "welcome"; // Nombre del archivo HTML del formulario de registro
    }
            @GetMapping("/addBook")
    public String addbook() {
        return "addBook"; // Nombre del archivo HTML del formulario de registro
    }


}
