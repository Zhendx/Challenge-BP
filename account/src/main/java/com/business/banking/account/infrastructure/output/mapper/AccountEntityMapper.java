package com.business.banking.account.infrastructure.output.mapper;

import com.business.banking.account.domain.Account;
import com.business.banking.account.infrastructure.output.repository.entity.AccountEntity;
import org.mapstruct.*;

import java.util.Objects;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        builder = @Builder(disableBuilder = true)
)
public interface AccountEntityMapper {

    @Mapping(target = "type.code", source="accountEntity.type", qualifiedByName = "typeCode")
    @Mapping(target = "type.description", source="accountEntity.type")
    Account toAccount(AccountEntity accountEntity);

    @Mapping(target = "type", source="account.type.description")
    AccountEntity toAccountEntity(Account account);

    @Mapping(target = "accountId", source="account.accountId")
    @Mapping(target = "type", source="account.type.description")
    AccountEntity toAccountEntityPut(Account account);

    @Named("typeCode")
    default String typeCode(String code){
        return Objects.equals(code, "AHORRO") ? "AHO" : "CRRE";
    }

}
