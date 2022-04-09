package study.datajpa;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.QMember;
import study.datajpa.entity.Team;
import study.datajpa.repository.MemberRepository;
import study.datajpa.repository.TeamRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@Transactional
class DataJpaApplicationTests {

    @Autowired
    EntityManager em;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TeamRepository teamRepository;



    @Test
    void contextLoads() {
        Team team = new Team("팀11111");
        teamRepository.save(team);


        Member member = new Member("권희운", 26, team);
        memberRepository.save(member);

        Member member2 = new Member("권희운2", 26, team);
        memberRepository.save(member2);

        JPAQueryFactory query = new JPAQueryFactory(em);
        QMember qMember = QMember.member;
        Member memberList =  query
                .selectFrom(qMember)
                .fetchFirst();

        System.out.println(memberList.getTeam().toString());
    }

    @Test
    public void 테스트당(){
        Team team = new Team("팀11111");
        teamRepository.save(team);

        memberRepository.save(new Member("권희운1", 26, team));
        memberRepository.save(new Member("권희운2", 26, team));
        memberRepository.save(new Member("권희운3", 26, team));
        memberRepository.save(new Member("권희운4", 26, team));
        memberRepository.save(new Member("권희운5", 26, team));
        memberRepository.save(new Member("권희운6", 26, team));

        em.flush();
        em.clear();

        memberRepository.bulkAgePlus(20);

        PageRequest pageRequest = PageRequest.of(0,2, Sort.by(Sort.Direction.DESC,"username"));
        List<Member> findMembers = memberRepository.findAll();


        /*List<MemberDto> findMembers2 = memberRepository.findMemberTeamIn(
                findMembers.stream().map(MemberDto::getUsername).collect(Collectors.toList())
        );*/
        System.out.println(findMembers);
        findMembers.stream().forEach(data -> System.out.println(data.toString()));


    }

}
