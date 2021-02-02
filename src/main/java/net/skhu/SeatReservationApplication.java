package net.skhu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SeatReservationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeatReservationApplication.class, args);
	}
	/*
	 @Bean
	    public CommandLineRunner initData(UserRepository userRepository, BoardRepository boardRepository) {
	        return args ->
	            IntStream.rangeClosed(1, 154).forEach(i -> {
	                User user = userRepository.getOne(10);

	                Board board = new Board();
	                board.setContent("hello"+i);
	                board.setTitle("안녕하세여"+1);
	                board.setUser(user);


	                boardRepository.save(board);
	            });
	    }
	    */
}
