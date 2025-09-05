package com.example.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "permissions",
        uniqueConstraints = @UniqueConstraint(columnNames = {"role_id", "resource_id", "scope_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne @JoinColumn(name = "resource_id", nullable = false)
    private Resource resource;

    @ManyToOne @JoinColumn(name = "scope_id", nullable = false)
    private Scope scope;
}
