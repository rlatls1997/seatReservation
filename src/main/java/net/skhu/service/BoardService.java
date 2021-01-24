package net.skhu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.skhu.entity.Board;
import net.skhu.repository.BoardRepository;

@Service
public class BoardService {

	@Autowired
	public BoardRepository boardRepository;

	public List<Board> findAll(){
		return boardRepository.findAll();
	}

}
