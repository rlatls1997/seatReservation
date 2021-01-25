package net.skhu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import net.skhu.entity.Board;
import net.skhu.model.Pagination;
import net.skhu.repository.BoardRepository;

@Service
public class BoardService {

	@Autowired
	public BoardRepository boardRepository;

	public List<Board> findAll(){
		return boardRepository.findAll();
	}

	public List<Board> findAll(Pagination pagination){
		return boardRepository.findAll(pagination);
	}

	public Page<Board> getBoardList(Pageable pageable) {
		//pageable의 page는 index처럼 0부터 시작하기 때문에
		//클라이언트가 보는 페이지에서 -1 처리를 해주어야 한다.
		int page = (pageable.getPageNumber() == 0)? 0 : (pageable.getPageNumber() - 1);
		pageable = PageRequest.of(page,  10, Sort.Direction.DESC, "reportingDate");

		return boardRepository.findAll(pageable);
	}
}
