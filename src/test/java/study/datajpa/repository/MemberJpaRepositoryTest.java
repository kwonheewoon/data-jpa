package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback(false)
@Transactional
class MemberJpaRepositoryTest {

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TeamRepository teamRepository;



    @Test
    public void save(){
        Team team = new Team("앙경영팀쓰");
        Team saveTeam = teamRepository.save(team);

        Member member = new Member("권희운4",26,saveTeam);

        Member savedMember = memberRepository.save(member);

        Optional<Member> findMember = memberRepository.findById(savedMember.getId());
        Assertions.assertThat(findMember.get().getId()).isEqualTo(savedMember.getId());

    }

    @Test
    public void findByUserNameAndAgeGranterThan(){
        List<Member> find = memberRepository.findByUsernameAndAgeGreaterThan("권희운4",10);
        find.stream().forEach(System.out::println);
    }

}