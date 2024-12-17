package tech.reliab.course.reshetova.bank.service;

import tech.reliab.course.reshetova.bank.entity.User;
import tech.reliab.course.reshetova.bank.model.UserModel;

import java.util.List;

public interface UserService {
    void ModelUserInfo();

    User createUser(UserModel userModel);

    void deleteUser(int id);

    User updateUser(int id, String name);

    User getUserDtoById(int id);

    List<User> getAllUsers();
}