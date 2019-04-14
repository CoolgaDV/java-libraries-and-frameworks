package cdv.lib.spring.data.jpa;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item, Long> {

    List<Item> findByName(String name);

    List<Item> findByKind(String kind);

}
