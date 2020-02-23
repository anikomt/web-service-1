package ru.ifmo.web.deploy;

import lombok.Data;
import ru.ifmo.web.database.dao.UserDAO;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;

@Data
@ApplicationScoped
public class UsersBean {
    @Resource(lookup = "jdbc/users")
    private DataSource dataSource;

    @Produces
    public UserDAO userDAO() {
        return new UserDAO(dataSource);
    }
}
