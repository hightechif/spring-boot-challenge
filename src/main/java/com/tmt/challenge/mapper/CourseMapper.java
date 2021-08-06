package com.tmt.challenge.mapper;

import com.tmt.challenge.dto.CourseDTO;
import com.tmt.challenge.model.Course;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    CourseDTO toCourseDTO(Course course);

    List<CourseDTO> toCourseDTO(Collection<Course> courses);

    Course toCourseEntity(CourseDTO courseDTO);

    List<Course> toCourseEntity(Collection<CourseDTO> courseDTOS);
}
