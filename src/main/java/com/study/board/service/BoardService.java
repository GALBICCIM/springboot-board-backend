package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {
   @Autowired  // 인터페이스 객체 자동 생성 어노테이션
   private BoardRepository boardRepository;

   // 글 작성 처리
   public void boardWrite(Board board, MultipartFile file) throws Exception {
      String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";  // 프로젝트 파일 경로
      UUID uuid = UUID.randomUUID();  // 난수 식별자
      String fileName = uuid + "_" + file.getOriginalFilename();  // 파일 이름 지정

      File saveFile = new File(projectPath, fileName);  // 저장할 파일 생성
      file.transferTo(saveFile);  // 파일 저장

      board.setFilename(fileName);  // DB에 파일 이름 저장
      board.setFilepath("/files/" + fileName);  // DB에 파일 경로 저장

      boardRepository.save(board);
   }

   // 게시글 리스트 처리
   public List<Board> boardList() {
      return boardRepository.findAll();
   }

   // 특정 게시글 불러오기
   public Board boardView(Integer id) {
      return boardRepository.findById(id).get();
   }

   // 특정 게시글 삭제
   public void boardDelete(Integer id) {
      boardRepository.deleteById(id);
   }
}
