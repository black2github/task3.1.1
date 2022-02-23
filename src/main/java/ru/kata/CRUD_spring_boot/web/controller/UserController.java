package ru.kata.CRUD_spring_boot.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.CRUD_spring_boot.model.User;
import ru.kata.CRUD_spring_boot.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Value("${spring.application.name}")
    String appName;

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /*
    GET /users/ - получение списка всех пользователей
     */
    @GetMapping()
    public String list(ModelMap model) {
        log.info("list: <- ");

        List<User> users = userService.listAll();

        // добавление пользователей при первом запуске, если еще не созданы
        if (!users.iterator().hasNext()) {
            for (int i = 0; i < 5; i++) {
                User user = userService.create(new User("firstName" + i, "secondName" + i, i));
                log.info("list:" + user);
            }
            users = userService.listAll();
        }
        model.addAttribute("users", users);
        log.info("list: -> " + users);
        return "users/index";
    }

    /*
    GET /users/:id - заполнение данных данных о конкретном пользователе для просмотра
     */
    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, ModelMap model) {
        log.info("show: <- id=" + id);

        // получение одного пользователя по id и передача на отображение
        User user = userService.find(id);
        if (user == null) {
            log.warn("show: User with id=" + id + " not found");
        }
        model.addAttribute("user", user);
        log.info("show: -> " + user);
        return "users/show";
    }

    /*
    GET /users/new - создание пустого объекта для заполнения данными формы
    и ссылка на форму создания нового пользователя
     */
    @GetMapping(value = "/new")
    public String newUser(@ModelAttribute("user") User user) {
        log.info("newUser: <- ");
        return "users/new";
    }

    /*
     POST /users/ - обработка данных с формы:
      - создание пользователя по объекту, заполненному на форме
      - перенаправление на начальну страницу вывода списка
     */
    @PostMapping()
    public String create(@ModelAttribute("user") User user) {
        log.info("create: <- " + user);
        User u = userService.create(user);
        log.info("create: -> " + u);
        return "redirect:/users";
    }

    /*
     GET /users/:id/edit - заполнение объекта данными
     и отправка на отправка на форму редактирование данных пользователя
     */
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {
        log.info("edit: <- id=" + id);
        User user = userService.find(id);
        if (user != null) {
            model.addAttribute("user", user);
        } else {
            log.warn("edit: User with id=" + id + " not found");
        }
        log.info("edit: -> "+ user);
        return "users/edit";
    }

    /*
     PATCH /users/:id - обновление данных пользователя c конкретным id
     */
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") long id) {
        log.info("update: <- user=" + user + ", id=" + id);
        User usr = userService.find(id);
        if (usr != null) {
            usr = userService.update(id, user);
        } else {
            log.warn("update: User with id=" + id + " not found");
        }
        log.info("update: ->");
        return "redirect:/users";
    }

    /*
     DELETE /users/:id - удаление пользователя по id
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        log.info("delete: <- id=" + id);
        User usr = userService.find(id);
        if (usr != null) {
            userService.delete(usr);
        } else {
            log.warn("delete: User with id=" + id + " not found");
        }
        log.info("delete: ->");
        return "redirect:/users";
    }
}
