package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager(); //transaction마다 매니저가 있어야함
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUserName("kim");
            member.changeTeam(team);
            em.persist(member);


            em.flush(); //db로 전송
            em.clear();

//            Member findMember = em.find(Member.class, member.getId());
//            Member findMember = em.getReference(Member.class, member.getId()); //프록시
            System.out.println("=================================================");

            Member m = em.find(Member.class, member.getId()); //db에서 받아와 1차캐시 채우고 값 반환

            System.out.println("m = " + m.getTeam().getClass());

            m.getTeam().getName();

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close(); //em은 스레드 단위에서 사용되어야 함
        }

        emf.close();
    }
}
