package org.cru.tweet.view;

import lombok.Getter;
import lombok.Setter;
import org.cru.tweet.view.internal.AbstractRetrievalView;

import java.util.List;

@Getter
@Setter
public class TweetsRetrievalView extends AbstractRetrievalView {
    List<TweetView> tweets;
}
