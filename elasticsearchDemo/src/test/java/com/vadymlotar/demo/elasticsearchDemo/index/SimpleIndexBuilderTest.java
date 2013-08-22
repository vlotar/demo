package com.vadymlotar.demo.elasticsearchDemo.index;

import java.io.IOException;

import com.vadymlotar.demo.elasticsearchDemo.index.impl.ProductIndexBuilder;
import com.vadymlotar.demo.elasticsearchDemo.index.impl.WikiIndexBuilder;
import junit.framework.Assert;

import org.junit.Test;

public class SimpleIndexBuilderTest {

	@Test
	public void testBuildWikiIndex() {
		try {
			new WikiIndexBuilder().buildIndex();
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail("Indexing process must not fail");
		}
	}

    @Test
    public void testBuildProductsIndex() {
        try {
            new ProductIndexBuilder().buildIndex();
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("Indexing process must not fail");
        }
    }
}
