package com.revature;

import org.apache.log4j.Logger;

import com.revature.controller.AccountController;
import com.revature.controller.AccountControllerImpl;
import com.revature.controller.AuthController;
import com.revature.controller.AuthControllerImpl;
import com.revature.controller.UserController;
import com.revature.controller.UserControllerImpl;

import io.javalin.Javalin;

public class MainDriver {
	private static final Logger LOGGER = Logger.getLogger(MainDriver.class);
	 
	private static final String LOGIN_PATH = "/login";
	private static AuthController authController = new AuthControllerImpl();
	
	private static final String USER_PATH = "/user";
	private static final UserController userController = new UserControllerImpl();
	private static final AccountController accController = new AccountControllerImpl();
//	private static PlanetController planetController = new PlanetControllerImpl();
	
	public static void main(String[] args) {
		Javalin app = Javalin.create(
				config -> {
					config.addStaticFiles("/public");
				}
			).start(9000);
		LOGGER.debug("Starting the Javalin Server now");
		
		app.post("/login", ctx -> authController.login(ctx));
		app.get("/logout", ctx -> authController.logout(ctx));
		app.post("/register", ctx -> userController.insertUser(ctx));
		app.get("/accounts", ctx -> userController.selectAccsById(ctx));
		app.post("/accounts", ctx -> accController.insertAccount(ctx));
		app.post("/deposit", ctx -> accController.depositMoney(ctx));
		app.post("/withdraw", ctx -> accController.withdrawMoney(ctx));
		app.post("/transfer", ctx -> accController.transferMoney(ctx)); //still need to implement with transaction
		
		//For employee
		app.post("/emplogin", ctx -> authController.employeeLogin(ctx));
		app.get("/unaccounts", ctx -> userController.selectUnapprovedUsers(ctx));
		app.post("/unaccounts", ctx -> userController.approveRejectUsers(ctx));
		app.get("/allaccounts", ctx -> userController.selectApprovedUsers(ctx));
		app.post("/useraccounts", ctx -> userController.employeeSelectAccsById(ctx));
		
//		app.get(USER_PATH, ctx -> planetController.getAllPlanets(ctx));
//		app.post(USER_PATH, ctx -> planetController.postPlanet(ctx));
//		app.delete(USER_PATH, ctx -> planetController.deletePlanet(ctx));
		
		//cookies and sessions and tokens!
	}

}
