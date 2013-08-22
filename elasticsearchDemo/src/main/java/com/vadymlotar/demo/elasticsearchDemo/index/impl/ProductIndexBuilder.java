package com.vadymlotar.demo.elasticsearchDemo.index.impl;

import com.vadymlotar.demo.elasticsearchDemo.client.ElasticSearchClientFactory;
import com.vadymlotar.demo.elasticsearchDemo.index.AbstractFileResourceIndexBuilder;
import com.vadymlotar.demo.elasticsearchDemo.model.Product;
import org.elasticsearch.action.index.IndexRequestBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Queue;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Allows to build Products index.
 * Loads data from products.txt.
 * User: vlotar
 */
public class ProductIndexBuilder extends AbstractFileResourceIndexBuilder<Product> {

    private InputStream in;
    private BufferedReader reader;

    @Override
    protected void closeResources() throws IOException {
        reader.close();
        in.close();
    }

    @Override
    protected BufferedReader getBufferedReader() throws IOException {
        in = ProductIndexBuilder.class.getResourceAsStream("products.txt");
        reader = new BufferedReader(new InputStreamReader(in));
        return reader;
    }

    @Override
    protected long doBulkLoad(BufferedReader reader) throws IOException {
        Queue<Product> products = new LinkedList<>();

        int count = 0;
        String strLine;
        // Read File Line By Line
        while ((strLine = reader.readLine()) != null) {
            // Print the content
            System.out.println(strLine);
            count++;
            String[] product = strLine.split(";");
            products.add(new Product(product[0],product[1],product[2],new BigDecimal(product[3])));
        }

        flush(products);

        return count;
    }

    @Override
    protected IndexRequestBuilder getIndexRequestBuilder(Product product) throws IOException {
        return ElasticSearchClientFactory
                .getClient()
                .prepareIndex("products", "product", product.getId()).setSource(jsonBuilder().startObject()
                        .field("brand", product.getBrand())
                        .field("name", product.getName())
                        .field("price", product.getPrice())
                        .endObject());
    }
}
