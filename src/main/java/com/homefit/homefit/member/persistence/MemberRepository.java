package com.homefit.homefit.member.persistence;

import com.homefit.homefit.member.domain.Member;
import com.homefit.homefit.member.persistence.po.MemberPo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberRepository {
    int insert(Member member);

    MemberPo selectById(Long id);

    MemberPo selectByUsername(String username);
    
    int updateMember(Member member);
    
    int updatePassword(Member member);
    
    int updateRole(Member member);
    
    int updateDeletion(Member member);
}