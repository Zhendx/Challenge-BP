package com.pichincha.business.banking.services.service.mapper;

import com.pichincha.business.banking.services.service.dto.AccountRequestDTO;
import com.pichincha.business.banking.services.service.dto.AccountResponseDTO;
import com.pichincha.business.banking.services.service.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountResponseDTO accountToAccountResponseDTO(Account account);

    @Mapping(target = "id", ignore = true)
    Account accountRequestDTOToAccount(AccountRequestDTO accountRequestDTO);

}
