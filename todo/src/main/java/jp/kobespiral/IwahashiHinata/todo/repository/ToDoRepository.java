package jp.kobespiral.IwahashiHinata.todo.repository;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import jp.kobespiral.IwahashiHinata.todo.entity.ToDo;
@Repository
public interface ToDoRepository extends CrudRepository<ToDo, Long>{
    List<ToDo> findAll();
    List<ToDo> findByDone(boolean done);
    List<ToDo> findByDoneAndMid(boolean done,String mid);

}