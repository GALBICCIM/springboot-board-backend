package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class BoardController {
   // 메소드 주입
   @Autowired
   private BoardService boardService;

   // 주소 지정 어노테이션
   // localhost:8080/board/write
   @GetMapping("/board/write")
   public String boardWriteForm() {
      return "boardWrite";
   }

   @PostMapping("/board/write-pro")
   public String boardWritePro(Board board, Model model, @RequestParam(name = "file") MultipartFile file) throws Exception {
      boardService.boardWrite(board, file);
      model.addAttribute("message", "글 작성이 완료되었습니다.");
      model.addAttribute("searchUrl", "/board/list");

      return "writeMessage";
   }

   @GetMapping("/board/list")
   public String boardList(Model model) {
      // list라는 이름으로 리스트를 받아서 넘김.
      model.addAttribute("list", boardService.boardList());
      return "boardList";
   }

   @GetMapping("/board/view")  // localhost:8080/board/view?id=id GET 방식
   public String boardView(Model model, @RequestParam(name = "id") Integer id) {
      model.addAttribute("board", boardService.boardView(id));

      return "boardView";
   }

   @GetMapping("/board/delete")
   public String boardDelete(@RequestParam(name = "id") Integer id) {
      boardService.boardDelete(id);

      return "redirect:/board/list";
   }

   @GetMapping("/board/modify/{id}")
   public String boardModify(Model model, @PathVariable("id") Integer id) {
      model.addAttribute("board", boardService.boardView(id));

      return "boardModify";
   }

   @PostMapping("/board/update/{id}")
   public String boardUpdate(@PathVariable("id") Integer id, Board board, Model model, MultipartFile file) throws Exception {
      // 기존 보드 불러오기
      Board boardTemp = boardService.boardView(id);
      // 기존 보드에서 수정되어 넘어온 보드의 제목과 내용으로 덮어쓰기
      boardTemp.setTitle(board.getTitle());
      boardTemp.setContent(board.getContent());

      // 덮어쓴 보드를 다시 서버로 요청
      boardService.boardWrite(boardTemp, file);

      // 상속 html의 속성값을 받아와서 값을 바꿈
      model.addAttribute("message", "글 수정이 완료되었습니다.");
      model.addAttribute("searchUrl", "/board/list");

      return "updateMessage";
   }
}
