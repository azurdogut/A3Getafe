package com.web.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"applicantName", "email", "message"}) // Evita mostrar datos sensibles en los logs
public class AdoptionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío.")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres.")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "Debe introducir un nombre válido.")
    @Column(nullable = false, length = 100)
    private String applicantName;

    @NotBlank(message = "El correo electrónico es obligatorio.")
    @Email(message = "Debes proporcionar un correo electrónico válido.")
    @Column(nullable = false, length = 150)
    private String email;

    @NotBlank(message = "El mensaje no puede estar vacío.")
    @Size(min = 10, max = 2000, message = "El mensaje debe tener entre 10 y 2000 caracteres.")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @NotNull
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @NotBlank
    @Column(nullable = false, length = 20)
    private String status = "PENDING";

    @NotNull(message = "Debe especificar un gato para la adopción.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cat_id", nullable = false)
    private Cat cat;

    public AdoptionRequest(String applicantName, String email, String message, LocalDateTime createdAt, String status, Cat cat) {
        this.applicantName = applicantName;
        this.email = email;
        this.message = message;
        this.createdAt = createdAt;
        this.status = status;
        this.cat = cat;
    }
}
