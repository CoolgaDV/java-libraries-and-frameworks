package cdv.libs.infinispan;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

/**
 * Sample entity that is used in {@link InfinispanApplication}
 *
 * @author Dmitry Kulga
 *         18.03.2018 15:13
 */
@Indexed
public class InfinispanEntity {

    private final String id;

    @Field
    private final String name;

    InfinispanEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "InfinispanEntity{id='" + id + "'}";
    }

}
