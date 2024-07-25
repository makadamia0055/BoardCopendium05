package org.zerock.b02.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.b02.domain.Board;
import org.zerock.b02.domain.Reply;
import org.zerock.b02.dto.BoardListReplyCountDTO;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
@SpringBootTest
class ReplyRepositoryTests {
    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private BoardRepository boardRepository;


    @Test
    public void testInsert(){
        // 실제 DB에 있는 bno
        Long bno = 3L;

        Board board = Board.builder().bno(bno).build();

        Reply reply = Reply.builder()
                .board(board)
                .replyText("댓글....")
                .replyer("replyer1")
                .build();

        replyRepository.save(reply);

    }

    @Transactional
    @Test
    public void testBoardReplies(){
        Long bno = 3L;

        Pageable pageable = PageRequest.of(0, 10, Sort.by("rno").descending());

        Page<Reply> result = replyRepository.listOfBoard(bno, pageable);

        result.getContent().forEach(reply ->log.info(reply));
    }

    @Test
    @Transactional
    @Commit
    public void testRemoveAll(){
        Long bno = 1L;

        replyRepository.deleteByBoard_Bno(bno);

        boardRepository.deleteById(bno);
    }



}