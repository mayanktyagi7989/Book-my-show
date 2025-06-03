package dev.aman.bookmyshow.Controllers;

import dev.aman.bookmyshow.DTOs.ResponseStatus;
import dev.aman.bookmyshow.DTOs.SignUpRequestDTO;
import dev.aman.bookmyshow.DTOs.SignUpResponseDTO;
import dev.aman.bookmyshow.Models.User;
import dev.aman.bookmyshow.Services.UserService;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public SignUpResponseDTO signUp(SignUpRequestDTO requestDTO) {
        SignUpResponseDTO responseDTO = new SignUpResponseDTO();
        try {
            User user =  userService.signUp(
                    requestDTO.getName(),
                    requestDTO.getEmail(),
                    requestDTO.getPassword());
            responseDTO.setUser(user);
            responseDTO.setResponseStatus(ResponseStatus.SUCCESS);
        }
        catch (Exception e) {
            responseDTO.setResponseStatus(ResponseStatus.FAILURE);
        }

        return responseDTO;
    }
}
