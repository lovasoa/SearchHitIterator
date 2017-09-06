# SearchHitIterator

[![](https://jitpack.io/v/lovasoa/SearchHitIterator.svg)](https://jitpack.io/#lovasoa/SearchHitIterator)

Java iterator for elasticsearch scrolls. Allows to iterate over all the results of an elasticsearch query. This library uses elasticsearch scrolls internally, in order to provide an efficiant way to iterate over large search results.

## Example

```java
SearchRequestBuilder builder = client.prepareSearch();

for (SearchHit hit : new SearchHitIterable(client, builder)) {
    // use hit...
}
```
