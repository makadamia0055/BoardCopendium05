package org.zerock.b02.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.Comparator;

@ToString(exclude = "board")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BoardImage implements Comparable<BoardImage> {

    @Id
    private String uuid;

    private String fileName;

    private int ord;

    @ManyToOne
    private Board board;

    @Override
    public int compareTo(BoardImage other){
        return this.ord - other.ord;
    }

    public void changeBoard(Board board){
        this.board = board;
    }

}
