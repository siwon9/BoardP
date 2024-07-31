package org.siwon.member.repositories;

import org.siwon.member.entities.Member;
import org.siwon.member.entities.QMember;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>,
        QuerydslPredicateExecutor<Member> { // 얘를 추가하면 predicate가 매개변수인게 추가가 된다.
    @EntityGraph(attributePaths = "authorities")
    Optional<Member> findByEmail(String email);

    default boolean exists(String email) {
           QMember member = QMember.member;

           return exists(member.email.eq(email));
    }
}