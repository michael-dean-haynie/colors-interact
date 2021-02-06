package com.codetudes.colorsinteract.contracts;

import lombok.Data;

@Data
public class RemovePlayerDTO {
    PlayerDTO player;

    // the logical frame according to the client making the remove player request
    Long logicalFrame;

    PlayerStateDTO playerState;
}
