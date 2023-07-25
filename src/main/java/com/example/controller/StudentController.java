package com.example.controller;

import com.example.dto.StudentDTO;
import com.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody StudentDTO studentDTO){
        StudentDTO response = studentService.add(studentDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping(value = "/all")
    public List<StudentDTO> getAll(){
       return studentService.getAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(studentService.getById(id));
    }
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    @RequestBody StudentDTO studentDTO){
        Boolean response = studentService.update(id,studentDTO);
        return ResponseEntity.ok(true);
    }
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        return ResponseEntity.ok(studentService.delete(id));
    }
    @GetMapping(value = "/getByName")
    public ResponseEntity<?> getByName(@RequestParam("name") String name){
        return ResponseEntity.ok(studentService.getByName(name));
    }
    @GetMapping(value = "/getBySurname")
    public ResponseEntity<?> getBySurname(@RequestParam("surname") String surname){
        return ResponseEntity.ok(studentService.getBySurname(surname));
    }
    @GetMapping(value = "/getByDate")
    public ResponseEntity<?> getByDate(@RequestParam("createdDate") LocalDate createdDate){
        return ResponseEntity.ok(studentService.getByDate(createdDate));
    }
    @GetMapping(value = "/getBetween")
    public ResponseEntity<?> getBetween(@RequestParam("createdDate") LocalDate from,
                                        @RequestParam("createdDate") LocalDate to){
        return ResponseEntity.ok(studentService.getByDateBetween(from,to));
    }
    @GetMapping(value = "/pagination")
    public ResponseEntity<PageImpl<StudentDTO>> paging(@RequestParam(value = "page",defaultValue = "1")int page,
                                             @RequestParam(value = "size",defaultValue = "10")int size){
        PageImpl<StudentDTO> response = studentService.studentPage(page -1,size);
        return ResponseEntity.ok(response);
    }
    @GetMapping(value = "/pagaByLevel")
    public ResponseEntity<PageImpl<StudentDTO>> paging(@RequestParam(value = "page",defaultValue = "1")int page,
                                    @RequestParam(value = "size",defaultValue = "10")int size,
                                    @RequestParam(value = "level")String level){
       PageImpl<StudentDTO> response = studentService.studentLevelpage(page-1,size,level);
        return ResponseEntity.ok(response);
    }
}
