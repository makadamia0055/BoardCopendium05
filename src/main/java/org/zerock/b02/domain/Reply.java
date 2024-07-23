package org.zerock.b02.domain;

import jakarta.persistence.*;
import lombok.*;

@ToString(exclude = "board")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Reply", indexes = {
        @Index(name="idx_reply_board_bno", columnList = "board_bno")
})
@Entity
public class Reply extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    private String replyText;

    private String replyer;
}
