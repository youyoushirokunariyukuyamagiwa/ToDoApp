package jp.kobespiral.IwahashiHinata.todo.dto;

import java.util.Date;

import jp.kobespiral.IwahashiHinata.todo.entity.ToDo;
import lombok.Data;
@Data
public class ToDoForm {
    String title; //タイトル

    public ToDo toEntity() {
        ToDo t = new ToDo(null,title,null,false,new Date(),null);
        return t;
    }
}