package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import model.Student;
import repository.studentRepository;
import java.util.List;

@Controller
public class StudentController {
    @Autowired
    private studentRepository srepo;


    @GetMapping("/")
    public List<Student> getAllStudents(){
        List<Student> list = (List<Student>)this.srepo.findAll();
        return list; 
    }

}
