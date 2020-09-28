package pl.sda.springquiz.repository;

import pl.sda.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringQuestionRepository extends JpaRepository<Question, Long> {
}
