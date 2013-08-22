package com.vadymlotar.demo.elasticsearchDemo.search;

import com.vadymlotar.demo.elasticsearchDemo.model.Product;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Collection;

/**
 * User: vlotar
 */
public class ProductSearchTest {

    @Test
    public void testSearch() {
        Collection<Product> products = new ProductSearch().search("apple");
        Assert.assertEquals(2, products.size());
    }
}
