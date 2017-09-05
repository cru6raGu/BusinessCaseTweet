package org.cru.tweet.domain.jpa.internal;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractImmutableDomainObject<PK extends Serializable> extends AbstractDomainObject<PK> {
    @Column(name = "create_time", updatable = false)
    private Date createTime;
}

