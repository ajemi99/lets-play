package com.letsplay.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users") // Katgoul l Spring bli had la classe hiya collection f MongoDB
@Data // Kat-générer les getters, setters, toString, equals, hashCode b Lombok
@NoArgsConstructor // Constructeur sans arguments
@AllArgsConstructor // Constructeur avec tous les arguments
@Builder // Bach t-créer les objets b tariqa sahla (User.builder().name("..").build())
public class User {
    
    @Id // L'Id principal dyal MongoDB (ghadi ykon String/ObjectId)
    private String id;
    
    private String name;
    private String email;
    private String password; // Had l'password ghadi n-hachiwh b BCrypt mn be3d
    private String role;     // "ADMIN" awla "USER"
}
