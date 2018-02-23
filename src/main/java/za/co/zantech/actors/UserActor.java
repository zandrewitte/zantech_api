package za.co.zantech.actors;

import akka.actor.AbstractActor;
import akka.actor.Status;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import javassist.NotFoundException;
import za.co.zantech.dao.UserDAO;
import za.co.zantech.messagespec.MessageSpec.*;
import za.co.zantech.messagespec.UserSpec.*;
import za.co.zantech.messagespec.UserSpec.User;
import za.co.zantech.utils.PasswordEncrypter;

import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by zandrewitte on 2017/05/31.
 * UserActor
 */
@SuppressWarnings("unchecked")
public class UserActor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(context().system(), this);
    private final UserDAO users = new UserDAO();

    public UserActor() {
        receive(ReceiveBuilder.
                match(GetAll.class, this::getUsers).
                match(GetById.class, this::getUserById).
                match(GetByUserName.class, this::getUserByUserName).
                match(Save.class, this::saveUser).
                matchAny(o -> log.info("Invalid message {}", o)).
                build()
        );
    }

    private void getUsers(GetAll g) {
        log.info("Getting Accounts");

        ListResponse list = new ListResponse(users.list().stream()
                .map(user -> new User(user.getId(), user.getUserName(), user.getRoleId()))
                .collect(Collectors.toList()));

        sender().tell(new Status.Success(list), self());
    }

    private void getUserById(GetById get) {
        Optional<User> response = users.getById((Long) get.getId())
            .map(user -> new User(user.getId(), user.getUserName(), user.getRoleId()));

        if (response.isPresent())
            sender().tell((new Status.Success(new SingleResponse(response.get()))), self());
        else
            sender().tell(new Status.Failure(new NotFoundException("User with id :" + get.getId() + " could not be found")), self());
    }

    private void getUserByUserName(GetByUserName get) {
        Optional<User> response = users.getByUserName(get.getUserName())
                .map(user -> new User(user.getId(), user.getUserName(), user.getPassword(), user.getRoleId()));

        if (response.isPresent())
            sender().tell((new Status.Success(new SingleResponse(response.get()))), self());
        else
            sender().tell(new Status.Failure(new NotFoundException("User with username :" + get.getUserName() + " could not be found")), self());
    }

    private void saveUser(Save<User> s) {
        User user = s.getEntity();

        Optional<User> response = PasswordEncrypter.encrypt(user.getPassword()).flatMap(encryptedPassword ->
            users.save(new za.co.zantech.entities.User(user.getId(), user.getUserName(), encryptedPassword, "pending", user.getRole()))
                .map(savedId -> new User(savedId, user.getUserName(), user.getRole()))
        );

        if (response.isPresent())
            sender().tell((new Status.Success(new SingleResponse(response.get()))), self());
        else
            sender().tell(new Status.Failure(new SQLException("User could not be saved")), self());

    }
}
