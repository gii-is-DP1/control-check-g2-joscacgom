package org.springframework.samples.petclinic.feeding;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface FeedingRepository extends CrudRepository<Feeding,Integer>{
    List<Feeding> findAll();
    @Query("SELECT fp FROM FeedingType fp")
    List<FeedingType> findAllFeedingTypes();
    @Query("SELECT fp FROM FeedingType fp WHERE fp.name=?1 ")
    FeedingType findFeedingTypeByName(String name);
    Optional<Feeding> findById(int id);
    Feeding save(Feeding p);
}
