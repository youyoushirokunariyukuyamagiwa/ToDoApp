package jp.kobespiral.IwahashiHinata.todo.dto;

import jp.kobespiral.IwahashiHinata.todo.entity.Member;
import lombok.Data;
@Data
public class MemberForm {
    String mid; //メンバーID．
    String name; //名前

    public Member toEntity() {
        Member m = new Member(mid, name);
        return m;
    }
}