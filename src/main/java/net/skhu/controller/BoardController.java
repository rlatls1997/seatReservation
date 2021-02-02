package net.skhu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import net.skhu.entity.Board;
import net.skhu.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;

	/*********글 목록 조회*************/
	@RequestMapping("")
	public String board(Pageable pageable, Model model) {
		Page<Board> boardList = boardService.getBoardList(pageable);
		model.addAttribute("boardList", boardList);
		return "board/board.html";
	}


}
