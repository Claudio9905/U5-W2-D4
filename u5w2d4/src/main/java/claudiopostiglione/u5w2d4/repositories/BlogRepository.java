package claudiopostiglione.u5w2d4.repositories;

import claudiopostiglione.u5w2d4.entities.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BlogRepository extends JpaRepository<Blog, UUID> {

    Optional<Blog> findByTitolo(String Titolo);
}
