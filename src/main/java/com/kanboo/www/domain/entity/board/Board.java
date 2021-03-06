package com.kanboo.www.domain.entity.board;

import com.kanboo.www.domain.entity.global.CodeDetail;
import com.kanboo.www.domain.entity.member.Member;
import com.kanboo.www.dto.board.BoardDTO;
import com.kanboo.www.dto.board.BoardFileDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "board")
public class Board {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_idx")
    private Member member;
    private String boardCn;
    private LocalDateTime boardDate;
    private String delAt;
    private int totalComments;
    private int totalLikes;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_detail_idx")
    private CodeDetail codeDetail;
    private String fileAt;

    @OneToOne(mappedBy = "board")
    private BoardFile boardFile;

    @OneToMany(mappedBy = "board")
    Set<Likes> likesList = new HashSet<>();

    @OneToMany(mappedBy = "board")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private Set<BoardReport> reportsList = new HashSet<>();

    public void changeDelAt(String delAt) {
        this.delAt = delAt;
    }

    public void changeBoardCn(String boardCn){
        this.boardCn = boardCn;
    }

    public void changeFileAt(String fileAt){
        this.fileAt = fileAt;
    }

    public void increaseLikes(){
        this.totalLikes += 1;
    }

    public void decreaseLikes(){
        this.totalLikes -= 1;
    }

    public void increaseTotalComments(){
        this.totalComments += 1;
    }

    public void decreaseTotalComments(){
        this.totalComments -= 1;
    }

    public BoardDTO entityToDto() {
        BoardFileDTO bfd = null;
        if("Y".equals(fileAt) && boardFile != null) {
            bfd = boardFile.entityToDto();
        }

        boolean isLike = false;
        if(!likesList.isEmpty()) {
            isLike = true;
        }

        boolean isReport = false;
        if(!reportsList.isEmpty()) {
            isReport = true;
        }

        return BoardDTO.builder()
                .boardIdx(boardIdx)
                .member(member.entityToDto())
                .boardCn(boardCn)
                .boardDate(boardDate)
                .delAt(delAt)
                .codeDetail(codeDetail.entityToDto())
                .fileAt(fileAt)
                .totalComments(totalComments)
                .totalLikes(totalLikes)
                .isLike(isLike)
                .isReport(isReport)
                .boardFileDTO(bfd)
                .build();
    }
}
