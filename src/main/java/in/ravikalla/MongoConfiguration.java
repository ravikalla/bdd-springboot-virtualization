package in.ravikalla;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories
public class MongoConfiguration extends AbstractMongoConfiguration
{
    @Override
    protected String getDatabaseName()
    {
        return "ravi_db";
    }

    @Override
    public Mongo mongo() throws Exception
    {
         return new MongoClient();
    }

    @Override
    protected String getMappingBasePackage()
    {
        return "in.ravikalla";
    }
}
