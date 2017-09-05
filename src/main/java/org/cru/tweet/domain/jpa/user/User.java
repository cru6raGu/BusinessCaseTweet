package org.cru.tweet.domain.jpa.user;

import lombok.Getter;
import lombok.Setter;
import org.cru.tweet.constant.UserStatus;
import org.cru.tweet.domain.jpa.AbstractExternalMutableDomainObject;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User extends AbstractExternalMutableDomainObject<Long> {
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "first_name", nullable = false)
    private String first_name;

    @Column(name = "last_name", nullable = false)
    private String last_name;

    @Column(name = "middle_name")
    private String middle_name;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;
}

