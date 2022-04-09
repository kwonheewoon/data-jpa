package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String name, int aaa);

    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username")String username, @Param("age")int age);

    @Query("select m.username from Member m")
    List<Member> findUserName();

    @Query(value = "select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t",
            countQuery = "select count(m) from Member m")
    Slice<MemberDto> findMemberTeam(Pageable pageable);

    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t where m.username in :names")
    List<MemberDto> findMemberTeamIn(@Param("names")List<String> names);

    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();
}
