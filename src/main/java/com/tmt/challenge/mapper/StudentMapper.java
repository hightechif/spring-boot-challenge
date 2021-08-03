package com.tmt.challenge.mapper;

import com.tmt.challenge.dto.StudentDTO;
import com.tmt.challenge.model.Student;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentDTO toStudentDTO(Student student);

    List<StudentDTO> toStudentDTO(Collection<Student> students);

}
