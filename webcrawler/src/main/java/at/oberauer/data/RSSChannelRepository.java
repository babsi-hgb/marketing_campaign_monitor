package at.oberauer.data;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by michael on 07.09.17.
 */
@Repository
@RepositoryRestResource(path = "rsschannels")
public interface RSSChannelRepository extends PagingAndSortingRepository<RSSChannel, Long> {}
