import controller.*;
import database.JDBConnectionWrapper;
import model.validator.UserValidator;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.account.AccountService;
import service.account.AccountServiceMySQL;
import service.client.ClientService;
import service.client.ClientServiceMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;
import service.user.UserService;
import service.user.UserServiceMySQL;
import view.*;

import java.sql.Connection;

import static database.Constants.Schemas.PRODUCTION;

public class Main {
    public static void main(String[] args) {
        final Connection connection = new JDBConnectionWrapper(PRODUCTION).getConnection();

        final RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        final UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);

        final AuthenticationService authenticationService = new AuthenticationServiceMySQL(userRepository,
                rightsRolesRepository);

        final UserService userService = new UserServiceMySQL(userRepository,
                rightsRolesRepository);

        final LoginView loginView = new LoginView();

        final UserValidator userValidator = new UserValidator(userRepository);

        final ActionsMenuView actionsMenuView = new ActionsMenuView();

        new LoginController(loginView, authenticationService, userValidator, actionsMenuView);


        final AddClientView addClientView = new AddClientView();
        final AddAccountView addAccountView = new AddAccountView();
        final DeleteAccountView deleteAccountView = new DeleteAccountView();
        final DeleteUserView deleteUserView = new DeleteUserView();
        final UpdateClientView updateClientView = new UpdateClientView();
        final UpdateAccountView updateAccountView = new UpdateAccountView();
        final TransferMoneyView transferMoneyView = new TransferMoneyView();

        final AddClientController addClientController = new AddClientController(addClientView, connection);
        final AddAccountController addAccountController = new AddAccountController(addAccountView, connection);
        final DeleteAccountController deleteAccountController = new DeleteAccountController(deleteAccountView, connection);
        final DeleteUserController deleteUserController = new DeleteUserController(deleteUserView, connection);
        final UpdateClientController updateClientController = new UpdateClientController(updateClientView, connection);
        final UpdateAccountController updateAccountController = new UpdateAccountController(updateAccountView, connection);
        final TransferMoneyController transferMoneyController = new TransferMoneyController(transferMoneyView, connection);

        final ClientService clientService = new ClientServiceMySQL(new ClientRepositoryMySQL(connection));
        AccountService accountService = new AccountServiceMySQL(new AccountRepositoryMySQL(connection));

        new ActionsMenuController(actionsMenuView, clientService, accountService,
                addAccountView, addClientView, deleteUserView, deleteAccountView,
                updateClientView, updateAccountView, transferMoneyView);
    }
}