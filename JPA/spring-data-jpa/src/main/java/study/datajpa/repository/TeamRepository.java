package study.datajpa.repository;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

@Repository
@RequiredArgsConstructor
public class TeamRepository {
    private final EntityManager em;

    public Team save(Team team) {
        em.persist(team);
        return team;
    }

    public void delete(Team team) {
        em.remove(team);
    }

    public List<Team> findAll() {
        return em.createQuery(
            "select t from Team t"
            , Team.class
        ).getResultList();
    }

    public Optional<Team> findById(Long id) {
        return Optional.ofNullable(em.find(Team.class, id));
    }

    public long count() {
        return em.createQuery(
            "select count(t) from Team t"
            , Long.class
        ).getSingleResult();
    }

    public Team find(Long id) {
        return em.find(Team.class, id);
    }
}
