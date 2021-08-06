package com.tmt.challenge.mapper;

import com.tmt.challenge.dto.AssignmentDTO;
import com.tmt.challenge.model.Assignment;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AssignmentMapper {

    AssignmentDTO toAssignmentDTO(Assignment assignment);

    List<AssignmentDTO> toAssignmentDTO(Collection<Assignment> assignments);

    Assignment toAssignmentEntity(AssignmentDTO assignmentDTO);

    List<Assignment> toAssignmentEntity(Collection<AssignmentDTO> assignmentDTOS);

}
