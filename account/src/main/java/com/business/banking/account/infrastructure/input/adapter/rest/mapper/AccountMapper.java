package com.business.banking.account.infrastructure.input.adapter.rest.mapper;

import com.business.banking.account.domain.PatchAccountRequest;
import com.business.banking.account.domain.PutAccountRequest;
import com.business.banking.account.infrastructure.input.adapter.rest.models.Account;
import com.business.banking.account.infrastructure.input.adapter.rest.models.GetAccountByIdResponse;
import com.business.banking.account.infrastructure.input.adapter.rest.models.PostAccountRequest;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring"
)
public interface AccountMapper {

    Account toAccount(com.business.banking.account.domain.Account account);

    com.business.banking.account.domain.Account toAccount(PostAccountRequest account);

    PutAccountRequest toPutAccountRequest(com.business.banking.account.infrastructure.input.adapter.rest.models.PutAccountRequest putAccountRequest);

    GetAccountByIdResponse toGetAccountByIdResponse(com.business.banking.account.domain.Account account);

    PatchAccountRequest toPatchAccountRequest(com.business.banking.account.infrastructure.input.adapter.rest.models.PatchAccountRequest patchAccountRequest);
}
