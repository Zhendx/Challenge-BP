package com.pichincha.business.banking.services.service.impl;

import com.pichincha.business.banking.services.exception.AppException;
import com.pichincha.business.banking.services.exception.custom.CustomError;
import com.pichincha.business.banking.services.repository.impl.AccountRepository;
import com.pichincha.business.banking.services.repository.impl.MovementRepository;
import com.pichincha.business.banking.services.service.AccountService;
import com.pichincha.business.banking.services.service.dto.AccountRequestDTO;
import com.pichincha.business.banking.services.service.dto.AccountResponseDTO;
import com.pichincha.business.banking.services.service.entity.Account;
import com.pichincha.business.banking.services.service.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final MovementRepository movementRepository;

    @Override
    public Mono<Flux<AccountResponseDTO>> getAccountsByFilter() {
        log.info("|--> Starting to get Account By Filter");
        return Mono.just(Flux.fromIterable(accountRepository.findAll())
                        .map(AccountMapper.INSTANCE::accountToAccountResponseDTO)
                        .switchIfEmpty(Mono.error(new AppException(CustomError.EmptyDataException))))
                .doOnError(error -> log.error("<--| Error to get Account By Filter! -> {}", error.getMessage()))
                .onErrorMap(throwable -> new AppException(CustomError.ApiClientException))
                .doOnSuccess(success -> log.info("<--| Success to get Account By Filter!"));
    }

    @Override
    public Mono<AccountResponseDTO> getAccountById(String number) {
        log.info("|--> Starting to get Account By Id");
        return Mono.just(accountRepository.findFirstByNumber(number))
                .map(AccountMapper.INSTANCE::accountToAccountResponseDTO)
                .switchIfEmpty(Mono.error(new AppException(CustomError.NotFoundDataException)))
                .doOnError(error -> log.error("<--| Error to get Account By Id! -> {}", error.getMessage()))
                .onErrorMap(throwable -> new AppException(CustomError.ApiClientException))
                .doOnSuccess(success -> log.info("<--| Success to get Account By Id!"));
    }

    @Override
    public Mono<AccountResponseDTO> postAccount(AccountRequestDTO accountRequestDTO) {
        log.info("|--> Starting to post Account");
        return Mono.just(accountRequestDTO)
                .flatMap(accountRequest -> Mono.fromCallable(() -> accountRepository.save(AccountMapper.INSTANCE.accountRequestDTOToAccount(accountRequest)))
                        .map(AccountMapper.INSTANCE::accountToAccountResponseDTO))
                .doOnError(error -> log.error("<--| Error to post Account! -> {}", error.getMessage()))
                .onErrorMap(throwable -> new AppException(CustomError.ApiClientException))
                .doOnSuccess(success -> log.info("<--| Success to post Account!"));
    }

    @Override
    public Mono<AccountResponseDTO> putAccount(String number, AccountRequestDTO accountRequestDTO) {
        log.info("|--> Starting to put Account");
        return Mono.just(accountRepository.findFirstByNumber(number))
                .switchIfEmpty(Mono.error(new AppException(CustomError.NotFoundDataException)))
                .map(account -> updateAccount(account, accountRequestDTO))
                .map(accountRepository::save)
                .map(AccountMapper.INSTANCE::accountToAccountResponseDTO)
                .doOnError(error -> log.error("<--| Error to put Account! -> {}", error.getMessage()))
                .onErrorMap(throwable -> new AppException(CustomError.ApiClientException))
                .doOnSuccess(success -> log.info("<--| Success to put Account!"));
    }

    private Account updateAccount(Account account, AccountRequestDTO accountRequestDTO) {
        account.setState(accountRequestDTO.getState());
        account.setBalance(accountRequestDTO.getBalance());
        account.setType(accountRequestDTO.getType());
        account.setNumber(accountRequestDTO.getNumber());
        account.setIdClient(accountRequestDTO.getIdClient());
        return account;
    }

    @Override
    public Mono<Void> deleteAccount(String number) {
        log.info("|--> Starting to delete Account");
        return Mono.just(accountRepository.findFirstByNumber(number))
                .switchIfEmpty(Mono.error(new AppException(CustomError.NotFoundDataException)))
                .map(account -> movementRepository.findFirstByIdAccount((long) account.getId()))
                .map(movement -> {
                    if(Objects.isNull(movement)){
                        accountRepository.deleteByNumber(number);
                        return Mono.just("Account deleted successfully.");
                    }
                    throw new AppException(CustomError.DeleteException);
                })
                .doOnError(error -> log.error("<--| Error to delete Account! -> {}", error.getMessage()))
                .doOnSuccess(success -> log.info("<--| Success to delete Account!"))
                .then();
    }
}
