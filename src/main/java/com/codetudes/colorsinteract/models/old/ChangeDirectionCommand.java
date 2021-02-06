package com.codetudes.colorsinteract.models.old;

import com.codetudes.colorsinteract.enums.DirectionEnum;
import lombok.Data;

@Data
public class ChangeDirectionCommand {
    private Player player;
    private DirectionEnum direction;
}
