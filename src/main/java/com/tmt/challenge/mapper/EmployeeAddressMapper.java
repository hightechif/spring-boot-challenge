package com.tmt.challenge.mapper;

import com.tmt.challenge.dto.EmployeeAddressDTO;
import com.tmt.challenge.model.EmployeeAddress;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeAddressMapper {

    EmployeeAddressDTO toEmployeeAddressDTO(EmployeeAddress employeeAddress);
    List<EmployeeAddressDTO> toEmployeeAddressDTO(List<EmployeeAddress> employeeAddresses);
    EmployeeAddress toEmployeeAddressEntity(EmployeeAddressDTO employeeAddressDTO);
    List<EmployeeAddress> toEmployeeAddressEntity(List<EmployeeAddressDTO> employeeAddressDTOS);

}
