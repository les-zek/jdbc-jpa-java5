package pl.sda.springquiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.entity.CompletedQuiz;

@Repository
public interface CompletedQuizRepository extends JpaRepository<CompletedQuiz, Long> {
}
