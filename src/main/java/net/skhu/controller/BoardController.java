package net.skhu.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.skhu.config.SrUserDetails;
import net.skhu.dto.BoardCreateDto;
import net.skhu.dto.BoardDetailsDto;
import net.skhu.dto.UserDetailsDto;
import net.skhu.entity.Board;
import net.skhu.service.BoardService;
import net.skhu.service.UserService;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	@Autowired
	private UserService userService;

	/*************************************게시글 조회***************************************/
	@RequestMapping("")
	public String board(Pageable pageable, Model model) {
		Page<Board> boardList = boardService.getBoardList(pageable);
		model.addAttribute("boardList", boardList);
		return "board/board.html";
	}

	//상세페이지 조회
	@RequestMapping("/details/{id}")
	public String detailPage(@PathVariable("id") int id, Model model , HttpServletRequest request,
			 @AuthenticationPrincipal SrUserDetails srUserDetails) {
		BoardDetailsDto boardDetailsDto = boardService.findOne(id);

		Principal principal = request.getUserPrincipal();


		if(srUserDetails != null) {
			UserDetailsDto userDetailsDto = userService.findOne(srUserDetails.getUsername());
			model.addAttribute("user", userDetailsDto);
		}

		model.addAttribute("board", boardDetailsDto);
		return "board/details.html";
	}

	/*************************************게시글 작성***************************************/
	@GetMapping("/create")
	public String create(Model model, HttpServletRequest request) {
		if(request.isUserInRole("ROLE_LOGINCHECK")) {
			model.addAttribute(new BoardCreateDto());
			return "board/create.html";

		}
		else
			return "redirect:/board";

	}
	@PostMapping("/create")
	public String create(Model model, Pageable pageable,
			@Valid BoardCreateDto boardCreate, BindingResult bindingResult, HttpServletRequest request) {

		if(request.isUserInRole("ROLE_LOGINCHECK")) {
			/*에러확인(validation)*/
			if(boardService.hasErrors(boardCreate, bindingResult))
				return "board/create.html";

			/*로그인된 user의 id을 조회하여 BoardCreate객체에 채운다.*/
			Principal principal = request.getUserPrincipal();
			boardCreate.setUserId(principal.getName());



			boardService.save(boardCreate);
			Page<Board> boardList = boardService.getBoardList(pageable);
			model.addAttribute("boardList", boardList);

			return "board/board.html";
		}
		else
			return "redirect:/board";
	}
	/*************************************게시글 수정***************************************/
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") int id, Model model) {
		BoardDetailsDto boardDetailsDto = boardService.findOne(id);

		model.addAttribute(new BoardCreateDto());
		model.addAttribute("boardDetailsDto", boardDetailsDto);
		return "board/edit";
	}
	@PostMapping("/edit/{id}")
	public String edit(@PathVariable("id") int id, Model model, BoardDetailsDto boardDetailsDto
			,@AuthenticationPrincipal SrUserDetails srUserDetails, HttpServletRequest request) {

		if(request.isUserInRole("ROLE_LOGINCHECK")) {

			boardDetailsDto.setUser(userService.findOneUser(srUserDetails.getUsername()));
			boardService.edit(boardDetailsDto);


			return "redirect:/board/details/" + id;
		}
		return "redirect:/board";

	}
	/*************************************게시글 삭제***************************************/
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") int id, Model model, HttpServletRequest request) {
		//로그인여부 확인
		if(request.isUserInRole("ROLE_LOGINCHECK")){

			boardService.deleteOne(id);
			return "redirect:/board";

		}
		else
			return "error/noAuthority";

	}

	}
