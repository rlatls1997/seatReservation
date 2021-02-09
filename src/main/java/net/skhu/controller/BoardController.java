package net.skhu.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.skhu.entity.Board;
import net.skhu.model.BoardCreate;
import net.skhu.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;

	/*************************************게시글 조회***************************************/
	@RequestMapping("")
	public String board(Pageable pageable, Model model) {
		Page<Board> boardList = boardService.getBoardList(pageable);
		model.addAttribute("boardList", boardList);
		return "board/board.html";
	}

	/*************************************게시글 작성***************************************/
	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute(new BoardCreate());
		return "board/edit.html";
	}
	@PostMapping("/create")
	public String create(Model model, Pageable pageable,
			@Valid BoardCreate boardCreate, BindingResult bindingResult, HttpServletRequest request) {

		/*에러확인(validation)*/
		if(boardService.hasErrors(boardCreate, bindingResult))
			return "board/edit.html";

		/*로그인된 user의 id을 조회하여 BoardCreate객체에 채운다.*/
		Principal principal = request.getUserPrincipal();
		boardCreate.setUserId(principal.getName());



		boardService.save(boardCreate);
		Page<Board> boardList = boardService.getBoardList(pageable);
		model.addAttribute("boardList", boardList);
		return "board/board.html";
	}

}
