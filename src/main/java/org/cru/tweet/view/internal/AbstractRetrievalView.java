package org.cru.tweet.view.internal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AbstractRetrievalView implements RetrievalView {
    int totalPage;
    long pageSize;
    long totalSize;
    int page;
    long size;
}
