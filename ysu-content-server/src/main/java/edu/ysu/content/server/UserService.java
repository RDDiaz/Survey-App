package edu.ysu.content.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.jooq.DSLContext;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService
{
    @Autowired
    private DSLContext jooq;

    public void create(User user)
    {
        jooq.execute
        (
            "INSERT INTO users " +
                    "(first_name, last_name) " +
                    "VALUES( " +
                    "?,?) " +
                    "RETURNING id",
            user.getFirstName(), user.getLastName()
        );
    }

    public List<User> getList() {
        return jooq.fetch(
            "SELECT * FROM users"
        ).into(User.class);
    }
}
