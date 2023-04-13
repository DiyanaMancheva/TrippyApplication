package com.diyanamancheva;

import com.diyanamancheva.util.ConsoleReader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryManagementSpring implements CommandLineRunner {
	private static final String GREETING_MESSAGE= "Welcome to Library Management Application!\n" +
																								"Please choose a menu, to proceed further:\n" +
																								"1. Orders\n" +
																								"2. Books\n" +
																								"3. Clients\n" +
																								"4. Authors\n" +
																								"5. Exit\n" +
																								"Your choice: ";
	private static final int INPUT_MIN_VALUE = 1;

	private static final int INPUT_MAX_VALUE = 5;

	//public LibraryManagementSpring(ItemPresenter itemPresenter) {
	//	this.itemPresenter = itemPresenter;
	//}

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementSpring.class, args);
	}

	@Override
	public void run(String... args) {
		System.out.println(GREETING_MESSAGE);

		int choice = ConsoleReader.readWithinRange(INPUT_MIN_VALUE, INPUT_MAX_VALUE);

		switch (choice) {
			case 1:
				System.out.printf("You chose %d", choice);
				break;
			case 2:
				//bookPresenter.showBookMenu();
				break;
			case 3:
				//clientPresenter.showClientMenu();
				break;
			case 4:
				//authorPresenter.showAuthorMenu();
				break;
			case 5:
				return;
		}
	}

}
