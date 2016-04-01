package fr.ippon.tatami.repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import fr.ippon.tatami.config.ColumnFamilyKeys;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;

/**
 * Cassandra implementation of the Follower repository.
 * <p/>
 * Structure :
 * - Key = tag + domain
 * - Name = follower login
 * - Value = time
 *
 * @author Julien Dubois
 */
@Repository
public class TagFollowerRepository
        extends AbstractFollowerRepository {

    @Inject
    private Session session;

    public void addFollower(String domain, String tag, String login) {
        super.addFollower(getKey(domain, tag), login);
    }

    public void removeFollower(String domain, String tag, String login) {
        super.removeFollower(getKey(domain, tag), login);
    }
    
    public Collection<String> findFollowers(String domain, String tag) {
        Statement statement = QueryBuilder.select()
                .column("login")
                .from("tagFollowers")
                .where(eq("key", getKey(domain, tag)));
        ResultSet results = session.execute(statement);
        return results
                .all()
                .stream()
                .map(e -> e.getString("login"))
                .collect(Collectors.toList());
    }

    @Override
    public String getFollowersCF() {
        return ColumnFamilyKeys.TAG_FOLLOWERS_CF;
    }

    /**
     * Generates the key for this column family.
     */
    private String getKey(String domain, String tag) {
        return tag.toLowerCase() + "-" + domain;
    }
}