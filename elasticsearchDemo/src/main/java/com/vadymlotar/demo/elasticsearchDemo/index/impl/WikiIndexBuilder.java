package com.vadymlotar.demo.elasticsearchDemo.index.impl;

import com.vadymlotar.demo.elasticsearchDemo.client.ElasticSearchClientFactory;
import com.vadymlotar.demo.elasticsearchDemo.index.AbstractFileResourceIndexBuilder;
import com.vadymlotar.demo.elasticsearchDemo.model.WikiArticle;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.elasticsearch.action.index.IndexRequestBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Allows to build WIKI index.
 * It will load each 10000 articles from the file and then flush it to ElasticSearch
 */
public class WikiIndexBuilder extends AbstractFileResourceIndexBuilder<WikiArticle> {

    private static final int STEP = 10000;
    private InputStream in;
    private BZip2CompressorInputStream bzIn;
    private BufferedReader reader;

    @Override
    protected BufferedReader getBufferedReader() throws IOException {
        in = WikiIndexBuilder.class.getResourceAsStream("nlwiki.txt.bz2");
        bzIn = new BZip2CompressorInputStream(in);
        reader = new BufferedReader(new InputStreamReader(bzIn));
        return reader;
    }

    @Override
    protected void closeResources() throws IOException {
        reader.close();
        bzIn.close();
        in.close();
    }

    protected long doBulkLoad(BufferedReader reader) throws IOException {

        Queue<WikiArticle> articles = new LinkedList<>();

        int count = 0;
        String strLine;
        // Read File Line By Line
        while ((strLine = reader.readLine()) != null) {
            // Print the content
            System.out.println(strLine);
            count++;

            String[] article = strLine.split(":");
            //add an article to the queue
            articles.add(new WikiArticle(article[0] + article[1], article[2]));
            //flush the data to ElasticSearch index if need
            if (articles.size() == STEP) {
                flush(articles);
            }
        }
        //flush the data if need
        flush(articles);
        return count;
    }

    @Override
    protected IndexRequestBuilder getIndexRequestBuilder(WikiArticle article) throws IOException {
        return ElasticSearchClientFactory
                .getClient()
                .prepareIndex("wiki", "article", article.getId()).setSource(jsonBuilder().
                        startObject().field("name", article.getName()).endObject());
    }

}
