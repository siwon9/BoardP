package org.siwon.member.repositories;

import org.siwon.member.entities.Authorities;
import org.siwon.member.entities.AuthoritiesId;
import org.siwon.member.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface AuthoritiesRepository extends JpaRepository<Authorities, AuthoritiesId>,
        QuerydslPredicateExecutor<Authorities> {

    List<Authorities> findByMember(Member member);
}
