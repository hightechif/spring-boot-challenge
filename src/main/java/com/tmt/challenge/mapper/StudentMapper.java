package com.tmt.challenge.mapper;

import com.tmt.challenge.dto.StudentDTO;
import com.tmt.challenge.dto.StudentOnlyDTO;
import com.tmt.challenge.model.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentDTO toStudentDTO(Student student);
    StudentOnlyDTO toStudentOnlyDTO(Student student);

}
