package ru.kata.CRUD_spring_boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.kata.CRUD_spring_boot.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kata.CRUD_spring_boot.model.UserRepository;

@Service("userService")
@Repository
@Transactional
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
        log.info("create: <- " + user);
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> list(int count) {
        log.info("list: <- count = " + count);
        List<User> list = new ArrayList<>();
        Iterable<User> itl = userRepository.findAll();
        Iterator<User> it = itl.iterator();
        for (int i=0; it.hasNext() && i < count; i++) {
            User user = it.next();
            list.add(user);
        }
        return list;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> listAll() {
        log.info("listAll: <-");
        List<User> list = new ArrayList<>();
        Iterable<User> i = userRepository.findAll();
        for (User user : i) {
            list.add(user);
        }
        log.info("listAll: -> " + Arrays.toString(list.toArray()));
        return list;
    }

    @Transactional(readOnly = true)
    @Override
    public User find(Long id) {
        log.info("find: <- id=" + id);
        return userRepository.findById(id).get();
    }

    @Override
    public void delete(User user) {
        log.info("delete: <- " + user);
        userRepository.delete(user);
    }

    @Override
    public User update(long id, User user) {
        log.info(String.format("update: <- id=%s, user=%s", id, user));

        User u = userRepository.findById(id).get();
        if (u != null && user != null) {
            u.setFirstName(user.getFirstName());
            u.setSecondName(user.getSecondName());
            u.setAge(user.getAge());
            u = userRepository.save(u);
        }

        log.info("update: -> " + u);
        return u;
    }
}
