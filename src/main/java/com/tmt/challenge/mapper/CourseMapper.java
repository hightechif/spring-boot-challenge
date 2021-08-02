package com.tmt.challenge.mapper;

import com.tmt.challenge.dto.CourseDTO;
import com.tmt.challenge.model.Course;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    CourseDTO toCourseDTO(Course course);

}
