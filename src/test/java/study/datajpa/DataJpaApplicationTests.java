package study.datajpa;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;
import study.datajpa.entity.QMember;
import study.datajpa.entity.Team;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Transactional
class DataJpaApplicationTests {

    @Autowired
    EntityManager em;




    @Test
    void contextLoads() {
        Team team = new Team("팀11111");
        em.persist(team);


        Member member = new Member("권희운", 26, team);
        em.persist(member);

        Member member2 = new Member("권희운2", 26, team);
        em.persist(member2);

        JPAQueryFactory query = new JPAQueryFactory(em);
        QMember qMember = QMember.member;
        Member memberList =  query
                .selectFrom(qMember)
                .fetchFirst();

        System.out.println(memberList.getTeam().toString());
    }

}
