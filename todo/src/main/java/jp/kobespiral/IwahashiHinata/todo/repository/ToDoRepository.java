package jp.kobespiral.IwahashiHinata.todo.repository;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import jp.kobespiral.IwahashiHinata.todo.entity.ToDo;
@Repository
public interface ToDoRepository extends CrudRepository<ToDo, Long>{
    List<ToDo> findAll();
    /*midとdoneの値でToDoを検索するメソッド */
    List<ToDo> findByDone(boolean done);
    /*doneの値でToDoを検索するメソッド */
    List<ToDo> findByDoneAndMid(boolean done,String mid);

}