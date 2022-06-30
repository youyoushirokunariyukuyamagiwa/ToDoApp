package jp.kobespiral.IwahashiHinata.todo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; 

import jp.kobespiral.IwahashiHinata.todo.dto.MemberForm;
import jp.kobespiral.IwahashiHinata.todo.dto.ToDoForm;
import jp.kobespiral.IwahashiHinata.todo.entity.Member;
import jp.kobespiral.IwahashiHinata.todo.entity.ToDo;
import jp.kobespiral.IwahashiHinata.todo.exception.ToDoAppException;
import jp.kobespiral.IwahashiHinata.todo.repository.MemberRepository;
import jp.kobespiral.IwahashiHinata.todo.repository.ToDoRepository;

@Service
public class ToDoService {
   @Autowired
   ToDoRepository tRepo;
   /**
    * ここで登録するのは作成者だけ？
    * タスクを作成する (C)
    * @param form
    * @return
    */
   public ToDo createToDo(String mid ,ToDoForm form) {
       
       ToDo t = form.toEntity();
       t.setMid(mid);
       //t.setDone(false);              //ToDoformのとこですでに書いてる？
       //t.setCreatedAt(new Date());    //ToDoformのとこですでに書いてる？
       //t.setDoneAt(null);             //ToDoformのとこですでに書いてる？
       return tRepo.save(t);
   }
   /**
    * タスクの通し番号からそのタスクを取得する (R)
    * @param mid
    * @return
    */
   public ToDo getToDo(Long seq) {
       ToDo t = tRepo.findById(seq).orElseThrow(
               () -> new ToDoAppException(ToDoAppException.NO_SUCH_MEMBER_EXISTS, seq + ": No such member exists"));
       return t;
   }
   /**
    * ある作成者の未達成タスクを取得する (R)
    * @return
    */
   public List<ToDo> getToDoList(String mid) {
       return tRepo.findByDoneAndMid(false, mid);
   }

   /*
    * ある作成者の達成済みタスクを取得する
    */
   public List<ToDo> getDoneList(String mid){
        return tRepo.findByDoneAndMid(true, mid); 
   }

   /**
    * 全員分の未達成タスクを取得する
    */
   public List<ToDo> getToDoList(){
        return tRepo.findByDone(false);
   }

   /**
    * 全員分の達成済みタスクを取得する
    * @return
    */
   public List<ToDo> getDoneList(){
        return tRepo.findByDone(true);
   }
   /**
    * タスクを削除する (D)
    */
   public void deleteToDo(Long seq) {
       ToDo t = getToDo(seq);
       tRepo.delete(t);
   }

   /**
    * タスクを達成済みにする
    */
    public ToDo DoneTodo(ToDo todo){
        todo.setDone(true);
        todo.setDoneAt(new Date());;

        return tRepo.save(todo);
    }
}