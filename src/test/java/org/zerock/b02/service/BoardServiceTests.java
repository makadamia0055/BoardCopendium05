package org.zerock.b02.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b02.dto.BoardDTO;
import org.zerock.b02.dto.PageRequestDTO;
import org.zerock.b02.dto.PageResponseDTO;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
@SpringBootTest
class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister(){
        log.info(boardService.getClass().getName());

        BoardDTO boardDTO = BoardDTO.builder()
                .title("sample title")
                .content("sample content")
                .writer("sample writer")
                .build();

        Long bno = boardService.register(boardDTO);

        log.info("bno:" + bno);
    }

    @Test
    public void testModify(){

        BoardDTO boardDTO = BoardDTO.builder()
                .title("sample title")
                .content("sample content")
                .writer("sample writer")
                .build();

        Long bno = boardService.register(boardDTO);
        log.info(bno);
        // 변경에 필요한 데이터만
        BoardDTO updateBoardDTO = BoardDTO.builder()
                .bno(bno)
                .title("Updated... 101")
                .content("Updated content 101")
                .build();

        boardService.modify(updateBoardDTO);

    }

    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("tcw")
                .keyword("1")
                .page(1)
                .size(10)
                .build();

        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);

        log.info(responseDTO);
    }
}