package com.example.service;

import com.example.dto.StudentDTO;
import com.example.entity.StudentEntity;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.repository.StudentRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public StudentDTO add(StudentDTO studentDTO) {
        check(studentDTO);

        StudentEntity entity = new StudentEntity();
        entity.setName(studentDTO.getName());
        entity.setSurname(studentDTO.getSurname());
        entity.setAge(studentDTO.getAge());
        entity.setLevel(studentDTO.getLevel());
        entity.setGender(studentDTO.getGender());
        entity.setCreatedDate(studentDTO.getCreatedDate());

        studentRepository.save(entity);
        studentDTO.setId(entity.getId());
        return studentDTO;
    }

    public List<StudentDTO> getAll() {
        Iterable<StudentEntity> iterable = studentRepository.findAll();
        List<StudentDTO> studentDTOList = new LinkedList<>();
        for (StudentEntity entity : iterable) {
            studentDTOList.add(toDTO(entity));
        }
        return studentDTOList;
    }

    public StudentDTO getById(Integer id) {
        Optional<StudentEntity> optionalStudentEntity = studentRepository.findById(id);
        if (optionalStudentEntity.isEmpty()) {
            throw new ItemNotFoundException("This id not found");
        }
        StudentEntity student = optionalStudentEntity.get();
        return toDTO(student);
    }

    public Boolean update(Integer id, StudentDTO student) {
        Optional<StudentEntity> optional = studentRepository.findById(id);
        if (optional.isPresent()) {
            StudentEntity entity = optional.get();
            entity.setName(student.getName());
            entity.setSurname(student.getSurname());
            entity.setAge(student.getAge());
            entity.setLevel(student.getLevel());
            entity.setGender(student.getGender());
            entity.setCreatedDate(student.getCreatedDate());
            studentRepository.save(entity);
        }

        return null;
    }

    public Boolean delete(Integer id) {
        studentRepository.deleteById(id);
        return true;
    }

    public List<StudentEntity> getByName(String name) {
        return studentRepository.getByName(name);
    }

    public List<StudentEntity> getBySurname(String surname) {
        return studentRepository.getBySurname(surname);
    }

    public List<StudentEntity> getByDate(LocalDate createdDatete) {
        return studentRepository.getByCreatedDate(createdDatete);
    }

    public List<StudentEntity> getByDateBetween(LocalDate from, LocalDate to) {
        return studentRepository.findAllByCreatedDateBetween(from, to);
    }

    public PageImpl<StudentDTO> studentPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<StudentEntity> pageObj = studentRepository.findAll(pageable);
        List<StudentEntity> entities = pageObj.getContent();
        long lon = pageObj.getTotalElements();

        List<StudentDTO> studentDTOList = new LinkedList<>();
        entities.forEach(entity -> {
            studentDTOList.add(toDTO(entity));
        });

        return new PageImpl<StudentDTO>(studentDTOList, pageable, lon);
    }

    public PageImpl<StudentDTO> studentLevelpage(int page, int size, String level) {
        Pageable pageable = PageRequest.of(page, size,Sort.by("id").ascending());
        Page<StudentEntity> pageOBJ = studentRepository.findByLevel(level,pageable);

        List<StudentDTO> studentDTOList = pageOBJ
                .stream()
                .map(this::toDTO)
                .toList();
        return new PageImpl<StudentDTO>(studentDTOList, pageable, pageOBJ.getTotalElements());


//        Page<StudentEntity> pageObj = studentRepository
//                .findAll(pageable);
//        List<StudentDTO> dtoList = new LinkedList<>();
//        List<StudentDTO> studentDTOList = pageObj
//                .stream()
//                .map(this::toDTO)
//                .toList();
//        return new PageImpl<StudentDTO>(studentDTOList, pageable, pageObj.getTotalElements());
    }

    public StudentDTO toDTO(StudentEntity entity) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(entity.getId());
        studentDTO.setName(entity.getName());
        studentDTO.setSurname(entity.getSurname());
        studentDTO.setAge(entity.getAge());
        studentDTO.setLevel(entity.getLevel());
        studentDTO.setGender(entity.getGender());
        studentDTO.setCreatedDate(entity.getCreatedDate());
        return studentDTO;
    }

    private void check(StudentDTO student) {
        if (student.getName() == null || student.getName().isBlank()) {
            throw new AppBadRequestException("Name qani?");
        }
        if (student.getSurname() == null || student.getSurname().isBlank()) {
            throw new AppBadRequestException("Surname qani?");
        }
    }
}
