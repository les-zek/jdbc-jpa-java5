package pl.sda.springquiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.entity.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
