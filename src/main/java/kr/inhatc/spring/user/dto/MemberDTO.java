package kr.inhatc.spring.user.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import kr.inhatc.spring.user.entity.MemberVo;

@Data
@NoArgsConstructor
public class MemberDTO {

    private String id;
    private String pw;
    private String dept;
    private String ban;

    @Builder
    public MemberDTO(String id, String pw, String dept, String ban) {
        this.id = id;
        this.pw = pw;
        this.dept = dept;
        this.ban = ban;
    }

    public MemberVo toEntity(){
        return MemberVo.builder()
                .id(id)
                .pw(pw)
                .dept(dept)
                .ban(ban)
                .build();
    }
}