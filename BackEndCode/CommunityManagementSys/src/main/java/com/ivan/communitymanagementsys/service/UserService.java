package com.ivan.communitymanagementsys.service;

import com.ivan.communitymanagementsys.dto.UserDTO;
import com.ivan.communitymanagementsys.dto.UserLoginDTO;
import com.ivan.communitymanagementsys.dto.UserPageQueryDTO;
import com.ivan.communitymanagementsys.entity.User;
import com.ivan.communitymanagementsys.result.PageResult;

import java.util.List;

public interface UserService {
    PageResult getAllUser(UserPageQueryDTO userPageQueryDTO) ;

    void changeUserByself(User user);
    User login(UserLoginDTO userLoginDTO);

    void register(UserDTO userDTO);

    void logoff();

    User getUserByUserName(String username);

    User getUserById(Long id);

    void deleteUserById(Long id);

    void addUser(User user);

    void changeUserByOther(User user);

}
