package com.codetudes.colorsinteract.contracts;

import lombok.Data;

import java.util.List;

@Data
public class GameDTO {
    private Long id;

    private int logicalFrameRate;

    private Long gameStart = System.currentTimeMillis();

    private List<InputDTO> inputs;
}
