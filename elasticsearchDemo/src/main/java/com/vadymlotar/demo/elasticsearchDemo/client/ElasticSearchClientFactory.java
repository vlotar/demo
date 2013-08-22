package com.vadymlotar.demo.elasticsearchDemo.client;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

public class ElasticSearchClientFactory {

	private static Client client;

	public static synchronized Client getClient() {
		if (client == null) {
			client = new TransportClient()
					.addTransportAddress(new InetSocketTransportAddress(
							"10.12.68.169", 9300));
	        
		}
		return client;
	}

	public static void shutdown() {
		client.close();
		client = null;
	}
}
