package be.fooda.backend.auth.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@Entity
public class FoodaUser {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Include
    @Column(unique = true)
    private String login;

    private String password;

    private Boolean isActive = Boolean.TRUE;

    private Boolean isAuthenticated = Boolean.FALSE;

    @CreationTimestamp
    private LocalDateTime registry;

    @UpdateTimestamp
    private LocalDateTime lastUpdated;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable
    @Enumerated(EnumType.STRING)
    private Set<FoodaRole> roles;
}
