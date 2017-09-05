package org.cru.tweet.view;

import lombok.Getter;
import lombok.Setter;
import org.cru.tweet.view.internal.AbstractExternalView;

@Getter
@Setter
public class UserView extends AbstractExternalView {
    private String guid;

    private String username;

    private String email;

    private String first_name;

    private String last_name;

    private String middle_name;

    private String status;

}
