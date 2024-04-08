package kr.megaptera.backendsurvivalweek10.controllers;

import jakarta.validation.Valid;
import kr.megaptera.backendsurvivalweek10.application.users.CreateUserService;
import kr.megaptera.backendsurvivalweek10.dtos.CreateUserRequestDto;
import kr.megaptera.backendsurvivalweek10.dtos.CreateUserResultDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final CreateUserService createUserService;

    public UserController(CreateUserService createUserService) {
        this.createUserService = createUserService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserResultDto createUser(
            @Valid @RequestBody CreateUserRequestDto dto
    ) {
        String accessToken = createUserService.createUser(
                dto.username().trim()
                , dto.password().trim()
        );

        return new CreateUserResultDto(accessToken);
    }

}
