package group1.auth.controller;

import group1.auth.dto.AuthRequest;
import group1.auth.entity.Turister;
import group1.auth.exception.ExistingEntityException;
import group1.auth.repository.TuristerRepository;
import group1.auth.service.JwtService;
import group1.auth.service.MyUserDetailService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.PrintWriter;
import java.util.List;

@RestController
@RequestMapping("/turister")
public class TuristerController {

    @Autowired
    TuristerRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtService jwtService;
    @Autowired
    MyUserDetailService myUserDetailService;

    @PostMapping("/registro")
    public Turister createTurister(@RequestBody Turister turister) {
        if(repository.findByUsername(turister.getUsername()).isPresent()) {
            throw new ExistingEntityException(turister.getName());
        } else {
            turister.setPassword(passwordEncoder.encode(turister.getPassword()));
            return repository.save(turister);
        }
    }

    @PostMapping("/login")
    public String authenticateTurister(@RequestBody AuthRequest request)  {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(myUserDetailService.loadUserByUsername(request.getUsername()));
        } else {
            throw new UsernameNotFoundException("Las credenciales son invalidas o no existen");
        }
    }

    @GetMapping("/usuarios")
    public List<Turister> getTuristers() {
        return repository.findAll();
    }

    @GetMapping("/usuarios/exportar")
    public ResponseEntity<Void> getTuristers(HttpServletResponse response) throws Exception {
        List<Turister> users = repository.findAll();  // Recupera la lista de usuarios

        // Configurar las cabeceras para descargar el archivo CSV
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=turisters.csv");

        // Escribir los datos CSV en la respuesta
        PrintWriter writer = response.getWriter();
        writer.println("username,name,password");  // Encabezados del CSV

        for (Turister user : users) {
            writer.println(user.getUsername() + "," + user.getName() + "," + user.getPassword());  // Datos del usuario
        }
        writer.flush();
        writer.close();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/test")
    public String test() {
        return "TEST2";
    }
}
