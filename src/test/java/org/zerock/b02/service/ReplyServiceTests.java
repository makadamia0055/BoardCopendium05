package org.zerock.b02.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b02.dto.ReplyDTO;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
@SpringBootTest
class ReplyServiceTests {

    @Autowired
    private ReplyService replyService;

    @Test
    public void testRegister(){
        ReplyDTO replyDTO = ReplyDTO.builder()
                .replyText("ReplyDTO Text")
                .replyer("replyer")
                .bno(3L)
                .build();

        log.info(replyService.register(replyDTO));
    }

    @Test
    public void testDeleteNonExistentEntity() {
        Long nonExistentId = 999L;
        assertThrows(NoSuchElementException.class, () -> {
            replyService.remove(nonExistentId);
        });
    }
}