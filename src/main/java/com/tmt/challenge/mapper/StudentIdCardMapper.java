package com.tmt.challenge.mapper;

import com.tmt.challenge.dto.StudentIdCardDTO;
import com.tmt.challenge.model.StudentIdCard;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentIdCardMapper {

    StudentIdCardDTO toIdCardDTO(StudentIdCard studentIdCard);

    List<StudentIdCardDTO> toIdCardDTO(Collection<StudentIdCard> studentIdCards);

    StudentIdCard toIdCardEntity(StudentIdCardDTO studentIdCardDTO);

    List<StudentIdCard> toIdCardEntity(Collection<StudentIdCardDTO> studentIdCardDTOS);

}
