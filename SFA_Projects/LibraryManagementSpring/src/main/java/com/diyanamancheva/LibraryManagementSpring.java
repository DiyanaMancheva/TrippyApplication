package com.diyanamancheva;

import com.diyanamancheva.author.AuthorPresenter;
import com.diyanamancheva.book.BookPresenter;
import com.diyanamancheva.client.ClientPresenter;
import com.diyanamancheva.util.ConsoleReader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryManagementSpring implements CommandLineRunner {
	private static final String GREETING_MESSAGE =
		"------------------------------------------\n" +
		"Welcome to Library Management Application!\n" +
		"------------------------------------------\n" +
		"Please choose a menu, to proceed further:\n" +
		"1. Orders\n" +
		"2. Books\n" +
		"3. Clients\n" +
		"4. Authors\n" +
		"5. Exit\n" +
		"------------------------------------------\n" +
		"Your choice: \n" +
		"------------------------------------------";
	private static final int INPUT_MIN_VALUE = 1;
	private static final int INPUT_MAX_VALUE = 5;

	private final AuthorPresenter authorPresenter;
	private final ClientPresenter clientPresenter;
	private final BookPresenter bookPresenter;

	public LibraryManagementSpring(AuthorPresenter authorPresenter, ClientPresenter clientPresenter,
																 BookPresenter bookPresenter) {
		this.authorPresenter = authorPresenter;
		this.clientPresenter = clientPresenter;
		this.bookPresenter = bookPresenter;
	}

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementSpring.class, args);
	}

	@Override
	public void run(String... args) {
		System.out.println(GREETING_MESSAGE);

		int choice = ConsoleReader.readWithinRange(INPUT_MIN_VALUE, INPUT_MAX_VALUE);

		switch (choice) {
			case 1:
				//orderPresenter.showOrderMenu();
				break;
			case 2:
				bookPresenter.showBookMenu();
				break;
			case 3:
				clientPresenter.showClientMenu();
				break;
			case 4:
				authorPresenter.showAuthorMenu();
				break;
			case 5:
				//return;
		}
	}

}
