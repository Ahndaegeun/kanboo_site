package com.kanboo.www.dto.board;

import com.kanboo.www.domain.entity.board.BoardFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardFileDTO {

    private Long fileIdx;
    private BoardDTO board;
    private String fileName;
    private String filePath;
    private String fileSize;
    private String extensionName;
    private String file;

    public BoardFile dtoToEntity() {
        return BoardFile.builder()
                .fileIdx(fileIdx)
                .fileName(fileName)
                .filePath(filePath)
                .fileSize(fileSize)
                .extensionName(extensionName)
                .build();
    }
}
