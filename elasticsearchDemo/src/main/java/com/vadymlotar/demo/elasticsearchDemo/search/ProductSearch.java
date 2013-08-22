package com.vadymlotar.demo.elasticsearchDemo.search;

import com.vadymlotar.demo.elasticsearchDemo.client.ElasticSearchClientFactory;
import com.vadymlotar.demo.elasticsearchDemo.model.Product;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

/**
 * User: vlotar
 */
public class ProductSearch {

    public static final String INDEX_NAME = "products";

    /**
     * Example how search request can be done using Java client.
     * Notice that search requests can be executed via REST API
     *
     * @param phrase search phrase
     * @return collection of found products
     */
    public Collection<Product> search(String phrase) {
        //create search request builder for products index
        SearchRequestBuilder searchRequestBuilder =
                ElasticSearchClientFactory.getClient().prepareSearch(INDEX_NAME);
        //prepare query
        QueryStringQueryBuilder qb = QueryBuilders.queryString(phrase)
                .defaultOperator(QueryStringQueryBuilder.Operator.OR)
                .useDisMax(true);
        //add sorting by price
        searchRequestBuilder.addSort("price", SortOrder.DESC);
        searchRequestBuilder.setFrom(0).setSize(50);
        searchRequestBuilder.setQuery(qb);
        //execute
        SearchResponse rsp = searchRequestBuilder.execute().actionGet();
        SearchHit[] docs = rsp.getHits().getHits();
        //collect products
        Collection<Product> products = new ArrayList<>();
        for (SearchHit sd : docs) {
            Product product = new Product(sd.getId(), (String) sd.getSource().get("brand"),
                    (String) sd.getSource().get("name"),
                    new BigDecimal((Double) sd.getSource().get("price")));
            products.add(product);
        }
        return products;
    }


}
