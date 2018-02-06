package at.oberauer.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by michael on 07.09.17.
 */
@Repository
@RepositoryRestResource(path = "rssitems")
public interface RSSItemRepository extends PagingAndSortingRepository<RSSItem, String> {

    Boolean existsByGuid(@Param("guid") String guid);

    @Query("SELECT i FROM RSSItem i " +
            "WHERE LOWER(description) LIKE LOWER(CONCAT('%', ?1, '%'))) " +
            "OR LOWER(title) LIKE LOWER(CONCAT('%', ?1, '%'))) " +
            "OR LOWER(link) LIKE LOWER(CONCAT('%', ?1, '%')))"
    )
    List<RSSItem> findByKeyword(@Param("keyword") String keyword);

}
