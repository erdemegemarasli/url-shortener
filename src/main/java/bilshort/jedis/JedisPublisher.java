package bilshort.jedis;

import bilshort.link.models.Link;
import bilshort.link.repositories.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Optional;

@Component
public class JedisPublisher {

    @Autowired
    LinkRepository linkRepository;

    private final JedisPool pool = new JedisPool(new JedisPoolConfig());
    private final Jedis jedis = pool.getResource();

    public void cacheLink(String code, String url, int visitCount){
        jedis.hset(code, "url", url);
        jedis.hset(code, "visit_count", String.valueOf(visitCount));
        updateShadowExpireTime(code);
    }

    public Optional<String> getCachedLink(String code){
        Optional<String> url = Optional.empty();

        if (jedis.exists(code)){
            url = Optional.of(jedis.hget(code, "url"));
            jedis.hincrBy(code, "visit_count", 1);
            updateShadowExpireTime(code);
        }

        return url;
    }

    private void updateShadowExpireTime(String code){
        String shadowCode = "shadow:" + code;
        jedis.set(shadowCode, "");
        jedis.expire(shadowCode, 5);
    }

    public void syncDatabaseWithRedis(String code){
        Link link = linkRepository.findByCode(code);

        int visitCount = getVisitCountFromRedis(code);
        link.setVisitCount(visitCount);
        linkRepository.save(link);

        deleteKeyWhenShadowExpired(code);
    }

    private void deleteKeyWhenShadowExpired(String code){
        jedis.del(code);
    }

    private int getVisitCountFromRedis(String code){
        String visitCount = jedis.hget(code, "visit_count");
        return Integer.parseInt(visitCount);
    }
}
