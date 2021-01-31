package be.fooda.backend.auth.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@Entity
public class UserEntity {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @EqualsAndHashCode.Include
    @Column(unique = true)
    private String login;

    private String password;

    private Boolean isActive;

    private String validationCode;

    private LocalDateTime validationExpiry;

    private Boolean isAuthenticated;

    @CreationTimestamp
    private LocalDateTime registry;

    @UpdateTimestamp
    private LocalDateTime lastUpdated;

    @ElementCollection(targetClass = RoleEntity.class, fetch = FetchType.EAGER)
    @CollectionTable
    @Enumerated(EnumType.STRING)
    private Set<RoleEntity> roles;
}
