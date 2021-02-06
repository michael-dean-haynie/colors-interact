package com.codetudes.colorsinteract.contracts;

import com.codetudes.colorsinteract.enums.InputTypeEnum;
import lombok.Data;

@Data
public class InputDTO {
    private Long id;

    private Long logicalFrame;

    private InputTypeEnum type;

    private PlayerDTO targetPlayer;

    private PlayerStateDTO targetPlayerState;
}
