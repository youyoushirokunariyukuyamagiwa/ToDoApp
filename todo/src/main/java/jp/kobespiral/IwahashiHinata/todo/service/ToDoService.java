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
    * メンバーを作成する (C)
    * @param form
    * @return
    */
   public ToDo createToDo(String mid ,ToDoForm form) {
       
       ToDo t = form.toEntity();
       t.setMid(mid);
       t.setDone(false);
       t.setCreatedAt(new Date());
       t.setDoneAt(null);
       return tRepo.save(t);
   }
   /**
    * メンバーを取得する (R)
    * @param mid
    * @return
    */
   public ToDo getToDo(Long seq) {
       ToDo t = tRepo.findById(seq).orElseThrow(
               () -> new ToDoAppException(ToDoAppException.NO_SUCH_MEMBER_EXISTS, seq + ": No such member exists"));
       return t;
   }
   /**
    * 全メンバーを取得する (R)
    * @return
    */
   public List<ToDo> getToDoList(String mid) {
       return tRepo.findByDoneAndMid(false, mid);
   }

   public List<ToDo> getDoneList(String mid){
        return tRepo.findByDoneAndMid(true, mid); 
   }

   public List<ToDo> getToDoList(){
        return tRepo.findByDone(false);
   }

   public List<ToDo> getDoneList(){
        return tRepo.findByDone(true);
   }
   /**
    * メンバーを削除する (D)
    */
   public void deleteToDo(Long seq) {
       ToDo t = getToDo(seq);
       tRepo.delete(t);
   }
}