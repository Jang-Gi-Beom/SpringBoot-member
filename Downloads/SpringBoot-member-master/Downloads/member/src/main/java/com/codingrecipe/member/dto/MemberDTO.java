package com.codingrecipe.member.dto;

import com.codingrecipe.member.entity.MemberEntity;
import lombok.*;

@Getter
@Setter
// Getter,Setter 생성자를 기본으로 만들어 주는 어노테이션
@NoArgsConstructor
// 기본생성자를 자동으로 만들어주는 어노테이션
@AllArgsConstructor
@ToString
public class MemberDTO {
    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String memberName;


    public static MemberDTO tomemberDTO(MemberEntity memberEntity) {
        // entity 객체를 dto 객체로 변환
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberName(memberEntity.getMemberName());
        return memberDTO;
    }
}
