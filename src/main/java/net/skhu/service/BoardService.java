package net.skhu.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import net.skhu.dto.BoardCreateDto;
import net.skhu.dto.BoardDetailsDto;
import net.skhu.dto.Pagination;
import net.skhu.entity.Board;
import net.skhu.entity.User;
import net.skhu.repository.BoardRepository;
import net.skhu.repository.UserRepository;

@Service
public class BoardService {

	@Autowired
	public BoardRepository boardRepository;
	@Autowired
	public UserRepository userRepository;
	@Autowired
	public ModelMapper modelMapper;

	public List<Board> findAll(){
		return boardRepository.findAll();
	}

	public List<Board> findAll(Pagination pagination){
		return boardRepository.findAll(pagination);
	}

	public BoardDetailsDto findOne(int id) {
		Board board = boardRepository.getOne(id);
		BoardDetailsDto boardDetailsDto = modelMapper.map(board, BoardDetailsDto.class);

		return boardDetailsDto;
	}

	public void save(BoardCreateDto boardCreate) {
		Board board = createBoard(boardCreate);
		boardRepository.save(board);
		return;
	}
	public void edit(BoardDetailsDto boardDetailsDto) {
		Board board = modelMapper.map(boardDetailsDto, Board.class);
		boardRepository.save(board);

		return;
	}
	public Board createBoard(BoardCreateDto boardCreate) {
		Board board = new Board();

		board.setContent(boardCreate.getContent());
		board.setTitle(boardCreate.getTitle());
		User user = userRepository.findByUserId(boardCreate.getUserId());
		board.setUser(user);

		return board;
	}

	public Page<Board> getBoardList(Pageable pageable) {
		//pageable의 page는 index처럼 0부터 시작하기 때문에
		//클라이언트가 보는 페이지에서 -1 처리를 해주어야 한다.
		int page = (pageable.getPageNumber() == 0)? 0 : (pageable.getPageNumber() - 1);
		pageable = PageRequest.of(page,  10, Sort.Direction.DESC, "reportingDate");

		return boardRepository.findAll(pageable);

	}

	public boolean hasErrors(BoardCreateDto boardCreate, BindingResult bindingResult) {
		//spring validation에서의 에러처리
		if(bindingResult.hasErrors())
			return true;

		return false;
	}

	public void deleteOne(int id) {
		boardRepository.deleteById(id);
		return;
	}
}
