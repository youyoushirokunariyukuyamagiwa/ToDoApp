package jp.kobespiral.IwahashiHinata.todo.controller;

import java.lang.ProcessBuilder.Redirect;
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
   @GetMapping("/mylist/{mid}")
   String showMyToDoList(@PathVariable String mid,@ModelAttribute(name="ToDoForm") ToDoForm tform,Model model) {
    List<ToDo> toDoList = tService.getToDoList(mid);
    List<ToDo> doneList = tService.getDoneList(mid);
    model.addAttribute("mid", mid);
    model.addAttribute("ToDoList", toDoList);
    model.addAttribute("DoneList", doneList);
    model.addAttribute("ToDoForm", tform);
    return "list";
   }
   
   /**
    * 最初
    * @return
    */
    @GetMapping("/login") 
   String showLoginForm(Model model,@ModelAttribute LoginForm form) {
        model.addAttribute("LoginForm", new LoginForm());
       return "index";
   }

   /**
    * ユーザーログインチェック HTTP-POST /user/login
    * @param form
    * @param model
    * @return
    */
   @PostMapping("/login") 
   String checkToDoForm(@ModelAttribute(name="LoginForm") LoginForm form,  Model model) {
       model.addAttribute("ToDoForm", form);
       String mid = form.getMid();
       //ユーザー（mid）が存在するなら
       if(mService.checkMember(mid)){
            return "redirect:/user/mylist/"+mid;
       }
       model.addAttribute("LoginForm", new LoginForm());
       return "index";//存在しないときはもとのまま？
   }

   /**
    * 新規タスク登録 HTTP-POST /user/register
    * @param form
    * @param model
    * @return
    */
   @PostMapping("/mylist/{mid}/register")
   String createToDo(@ModelAttribute ToDoForm form,  Model model ,@PathVariable String mid) {
       tService.createToDo(mid, form);
       return "redirect:/user/mylist/"+mid;
   }
   /**
    * タスク削除 HTTP-GET /user/delete/{mid}
    * @param mid
    * @param model
    * @return
    */
   @GetMapping("/{mid}/delete/{seq_str}")
   String deleteToDo(@PathVariable String seq_str,@PathVariable String mid,Model model) {
       Long seq = Long.parseLong(seq_str);
       tService.deleteToDo(seq);
       return "redirect:/user/mylist/"+mid;
   }

   /**
    * タスク完了
    */
    @GetMapping("/{mid}/done/{seq_str}")
    String doneToDo(@PathVariable String seq_str, @PathVariable String mid,Model model){
        Long seq = Long.parseLong(seq_str);
        tService.DoneTodo(seq);
        return "redirect:/user/mylist/"+mid;
    }

    /**
     * 全員分のToDoリスト
     */
    @GetMapping("/allList")
    String showAllList(@ModelAttribute String mid,Model model){
        List<ToDo> allToDoList = tService.getToDoList();
        List<ToDo> allDoneList = tService.getDoneList();
        model.addAttribute("DoneList", allDoneList);
        model.addAttribute("ToDoList", allToDoList);
        model.addAttribute("mid", mid);
        return "alllist";
    }
}