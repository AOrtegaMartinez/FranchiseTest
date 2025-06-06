package franchise.test.mongo.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableReactiveMongoRepositories(basePackages = "franchise.test.mongo.repository")
public class MongoConfig extends AbstractReactiveMongoConfiguration {

    private final MongoDBSecret mongoDBSecret;

    public MongoConfig(MongoDBSecret mongoDBSecret) {
        this.mongoDBSecret = mongoDBSecret;
    }

    @Override
    protected String getDatabaseName() {
        return mongoDBSecret.getDatabaseName();
    }

    @Override
    @Bean
    public MongoClient reactiveMongoClient() {
        return MongoClients.create(mongoDBSecret.getUri());
    }

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(reactiveMongoClient(), getDatabaseName());
    }
}
