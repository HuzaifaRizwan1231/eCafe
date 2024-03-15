package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Student;


public interface studentRepository extends JpaRepository<Student, Integer> {
    
}
