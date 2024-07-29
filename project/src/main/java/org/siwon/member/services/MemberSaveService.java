package org.siwon.member.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.siwon.member.constants.Authority;
import org.siwon.member.controllers.RequestJoin;
import org.siwon.member.entities.Authorities;
import org.siwon.member.entities.Member;
import org.siwon.member.repositories.AuthoritiesRepository;
import org.siwon.member.repositories.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberSaveService {
    private final MemberRepository memberRepository;
    private final AuthoritiesRepository authoritiesRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입 처리
     * @param form
     */
    public void save(RequestJoin form) {
        Member member = new ModelMapper().map(form, Member.class);
        String hash = passwordEncoder.encode(form.getPassword()); // BCrypt 해시화
        member.setPassword(hash);

        save(member, List.of(Authority.USER));
    }


    public void save(Member member, List<Authority> authorities) {
        // 휴대전화번호 숫자만 기록
        String mobile = member.getMobile();
        if(StringUtils.hasText(mobile)) {
            mobile = mobile.replaceAll("\\D", "");
            member.setMobile(mobile);
        }

        memberRepository.saveAndFlush(member);

        // 권한 추가, 수정 S
        if(authorities != null) {
            List<Authorities> items = authoritiesRepository.findByMember(member);
            authoritiesRepository.deleteAll(items);
            authoritiesRepository.flush();

            items = authorities.stream().map(a -> Authorities.builder()
                    .member(member)
                    .authority(a)
                    .build()).toList();

            authoritiesRepository.saveAllAndFlush(items);
        }
        // 권한 추가, 수정 E
    }
}
