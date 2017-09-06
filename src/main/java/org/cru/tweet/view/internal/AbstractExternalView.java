package org.cru.tweet.view.internal;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;

import java.io.Serializable;

@Getter
@Setter
public class AbstractExternalView extends ResourceSupport implements Serializable {
    private String guid;
}
