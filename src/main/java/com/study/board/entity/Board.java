package com.study.board.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity  // DB 테이블
@Data  // Lombok
public class Board {
   @Id  // PRIMARY KEY
   @GeneratedValue(strategy = GenerationType.IDENTITY)  // IDENTITY : MariaDB에서 사용함.
   private Integer id;
   private String title;
   private String content;
   private String filename;
   private String filepath;
}
