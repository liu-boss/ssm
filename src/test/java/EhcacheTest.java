import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * @Author zhangyukang
 * @Date 2020/9/12 12:02
 * @Version 1.0
 **/
public class EhcacheTest {
    public static void main(String[] args) {
        CacheManager cacheManager=new CacheManager(EhcacheTest.class.getResourceAsStream("ehcache.xml"));
        Cache cache = cacheManager.getCache("passwordRetryCache");
        cache.put(new Element("username","zhangyukang"));
        System.out.println(cache.get("username").getObjectValue());

        cache.flush();
        cacheManager.shutdown();
    }
}
