package jp.kobespiral.IwahashiHinata.todo.controller;

import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import aj.org.objectweb.asm.Attribute;
import jp.kobespiral.IwahashiHinata.todo.dto.LoginForm;
import jp.kobespiral.IwahashiHinata.todo.dto.ToDoForm;
import jp.kobespiral.IwahashiHinata.todo.entity.ToDo;
import jp.kobespiral.IwahashiHinata.todo.service.MemberService;
import jp.kobespiral.IwahashiHinata.todo.service.ToDoService;



@Controller
@RequestMapping("/user")
public class ToDoController {
   @Autowired
   ToDoService tService;
   @Autowired
   MemberService mService;
   /**
    * ユーザーのToDoリスト HTTP-GET /user/mylist
    * @param model
    * @return
    */
   //@GetMapping("/mylist")
   String showMyToDoList(Model model,String mid) {
       
    model.addAttribute("mid", mid);
    List<ToDo> toDoList = tService.getToDoList(mid);
    model.addAttribute("ToDoList", toDoList);
    List<ToDo> doneList = tService.getDoneList(mid);
    model.addAttribute("DoneList", doneList);
    return "list";
   }
   /**
    * ユーザーログインチェック HTTP-POST /user/login
    * @param form
    * @param model
    * @return
    */
   @PostMapping("/login") 
   String checkToDoForm(@ModelAttribute LoginForm form,  Model model) {
       model.addAttribute("ToDoForm", form);
       String mid = form.getMid();
       //ユーザー（mid）が存在するなら
       if(mService.checkMember(mid)){
            return showMyToDoList(model, mid);
       }
       return "index";//存在しないときはもとのまま？
   }
   /**
    * 新規タスク登録 HTTP-POST /user/register
    * @param form
    * @param model
    * @return
    */
   @PostMapping("/register")
   String createToDo(@ModelAttribute ToDoForm form,  Model model ,String mid) {
       tService.createToDo(mid, form);
       return showMyToDoList(model, mid);
   }
   /**
    * タスク削除 HTTP-GET /user/delete/{mid}
    * @param mid
    * @param model
    * @return
    */
   @GetMapping("/delete/{mid}")
   String deleteToDo(@PathVariable Long seq, Model model,String mid) {
       tService.deleteToDo(seq);
       return showMyToDoList(model, mid);
   }

   /**
    * タスク完了
    */
    @GetMapping("/done/{mid}")
    String doneToDo(@PathVariable Long seq, Model model,String mid){
        tService.DoneTodo(seq);
        return showMyToDoList(model, mid);
    }

}