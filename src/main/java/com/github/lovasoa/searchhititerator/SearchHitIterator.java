package com.github.lovasoa.searchhititerator;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.SearchHit;
import java.util.Iterator;

public class SearchHitIterator implements Iterator<SearchHit> {
    private final Client client;
    private SearchResponse currentResponse;
    private int currentIndex = 0;
    private TimeValue scrollTimeout;

    public SearchHitIterator(Client client, SearchRequestBuilder builder, TimeValue scrollTimeout) {
        this.client = client;
        this.currentResponse = builder.setScroll(scrollTimeout).get();
        this.scrollTimeout = scrollTimeout;
    }

    public SearchHitIterator(Client client, SearchRequestBuilder builder) {
        this(client, builder, new TimeValue(60000));
    }

    public boolean hasNext() {
        if (currentIndex >= currentResponse.getHits().getHits().length) {
            currentResponse = client.prepareSearchScroll(currentResponse.getScrollId())
                    .setScroll(scrollTimeout)
                    .get();
        }
        return currentResponse.getHits().getHits().length > 0;
    }

    public SearchHit next() {
        if (!hasNext()) return null;
        return currentResponse.getHits().getHits()[currentIndex++];
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}