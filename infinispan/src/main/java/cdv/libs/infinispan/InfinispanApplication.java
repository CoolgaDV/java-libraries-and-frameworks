package cdv.libs.infinispan;

import org.apache.lucene.search.Query;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.infinispan.Cache;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.cache.Index;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.query.CacheQuery;
import org.infinispan.query.Search;
import org.infinispan.query.SearchManager;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Sample application that demonstrate text search using Infinispan
 *
 * @author Dmitry Kulga
 *         18.03.2018 15:04
 */
public class InfinispanApplication {

    private static final String CACHE_NAME = "sample-cache";

    public static void main(String[] args) {

        DefaultCacheManager cacheManager = new DefaultCacheManager();
        cacheManager.defineConfiguration(CACHE_NAME, new ConfigurationBuilder()
                .indexing()
                    .index(Index.PRIMARY_OWNER)
                    .addProperty("default.directory_provider", "local-heap")
                    .addProperty("lucene_version", "LUCENE_CURRENT")
                    .addIndexedEntity(InfinispanEntity.class)
                .build());

        Cache<String, InfinispanEntity> cache = cacheManager.getCache(CACHE_NAME);
        cache.put("01", new InfinispanEntity("01", "Some value"));
        cache.put("02", new InfinispanEntity("02", "Another some value"));
        cache.put("03", new InfinispanEntity("03", "Another value"));

        SearchManager search = Search.getSearchManager(cache);
        QueryBuilder queryBuilder = search.buildQueryBuilderForClass(InfinispanEntity.class).get();
        Query query = queryBuilder.keyword().fuzzy().withEditDistanceUpTo(2)
                .onField("name")
                .matching("some")
                .createQuery();

        CacheQuery<InfinispanEntity> cacheQuery = search.getQuery(query, InfinispanEntity.class);
        List<InfinispanEntity> results = StreamSupport.stream(cacheQuery.spliterator(), false)
                .map(InfinispanEntity.class::cast)
                .sorted(Comparator.comparing(InfinispanEntity::getId))
                .collect(Collectors.toList());

        System.out.println("Results: " + results);
    }

}
