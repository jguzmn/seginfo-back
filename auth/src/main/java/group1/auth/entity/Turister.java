package group1.auth.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "usuario")
@Entity
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Turister {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;
    @Column(name = "nickname", nullable = false, length = 100)
    private String username;
    @Column(name = "nombre_completo", nullable = false, length = 100)
    private String name;
    @Column(name = "clave", nullable = false, length = 300)
    private String password;
    @Column(name = "rol", nullable = false, length = 300)
    private String role = "USER";
}
