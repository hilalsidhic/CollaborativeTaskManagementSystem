package com.hilal.TaskMaster.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamMemberships {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long membershipId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teamId", referencedColumnName = "teamId")
    private Teams team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private Users user;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDateTime joinedAt;
}
