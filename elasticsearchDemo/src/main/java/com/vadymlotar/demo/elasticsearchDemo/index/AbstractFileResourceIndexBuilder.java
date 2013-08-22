package com.vadymlotar.demo.elasticsearchDemo.index;

import com.vadymlotar.demo.elasticsearchDemo.client.ElasticSearchClientFactory;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Queue;

/**
 * User: vlotar
 */
public abstract class AbstractFileResourceIndexBuilder<T> {

    /**
     * Allows to build the index
     *
     * @throws IOException can be thrown
     */
    public void buildIndex() throws IOException {
        long startTimeInMillis = System.currentTimeMillis();
        //build the index
        long count = doBulkLoad(getBufferedReader());
        //close all opened resources
        closeResources();
        long endTimeInMillis = System.currentTimeMillis();
        System.out.println("Indexing finished in " + (endTimeInMillis - startTimeInMillis)
                + ". Total number of items: " + count);
    }

    /**
     * Gets BufferedReader object from the implementation
     *
     * @return BufferedReader for current implementation
     * @throws IOException
     */
    protected abstract BufferedReader getBufferedReader() throws IOException;

    /**
     * Closes all resources which were opened previously
     *
     * @throws IOException
     */
    protected abstract void closeResources() throws IOException;

    /**
     * Does bulk load. In the end all data should be in the Lucene index
     *
     * @param reader buffered reader for current implementation
     * @return number of loaded items
     * @throws IOException
     */
    protected abstract long doBulkLoad(BufferedReader reader) throws IOException;

    /**
     * Prepares request builder for one item
     *
     * @param item T
     * @return current request builder
     * @throws IOException
     */
    protected abstract IndexRequestBuilder getIndexRequestBuilder(T item) throws IOException;

    /**
     * Allows to flush items from collection to ElasticSearch/Lucene index
     *
     * @param items items to be loaded to the index
     * @throws IOException
     */
    protected final void flush(Queue<T> items) throws IOException {
        BulkRequestBuilder bulkRequestBuilder = ElasticSearchClientFactory
                .getClient().prepareBulk();

        while (!items.isEmpty()) {
            T item = items.poll();
            IndexRequestBuilder req = getIndexRequestBuilder(item);
            bulkRequestBuilder.add(req);
        }

        BulkResponse bulkResponse = bulkRequestBuilder.execute()
                .actionGet();
        if (bulkResponse.hasFailures()) {
            System.out.println("Error occurred");
        }

    }
}
