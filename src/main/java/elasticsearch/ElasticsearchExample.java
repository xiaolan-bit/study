package elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

import java.io.IOException;

public class ElasticsearchExample {

    public static void main(String[] args) throws IOException {
        // 连接到Elasticsearch集群
        RestClientBuilder restClientBuilder = RestClient.builder(
                new HttpHost("127.0.0.1", 9200, "http") // 如果您有多个节点
                //new HttpHost("localhost", 9201, "http") // 如果您有多个节点
        );

        RestHighLevelClient client = new RestHighLevelClient(restClientBuilder);

        // 创建索引并添加文档
        String indexName = "my_index";
        String documentId = "1";
        String jsonString = "{\"title\":\"Sample Document\",\"content\":\"This is a sample document\"}";

        IndexRequest indexRequest = new IndexRequest(indexName)
                .id(documentId)
                .source(jsonString, XContentType.JSON);

        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println("Index created: " + indexResponse);

        // 执行搜索操作
        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.sort("title.keyword", SortOrder.ASC);
        searchSourceBuilder.timeout(new TimeValue(3000));
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("Search results: " + searchResponse);

        // 关闭Elasticsearch客户端连接
        client.close();
    }
}
