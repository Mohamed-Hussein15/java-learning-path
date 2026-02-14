package user.service;

import user.model.User;

public interface UserService {

    User findByEmail(String email);

    User findById(Long id);

    boolean createUser(User user);

    boolean validateUser(String email, String password);
}
