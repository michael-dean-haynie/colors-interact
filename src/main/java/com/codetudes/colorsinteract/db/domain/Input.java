package com.codetudes.colorsinteract.db.domain;

import com.codetudes.colorsinteract.enums.InputTypeEnum;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Input {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // timestamp (in game frames, the first frame after this state has been applied)
    private Long logicalFrame;

    private InputTypeEnum type;

    @OneToOne(cascade = CascadeType.ALL)
    private Player targetPlayer;

    @OneToOne(cascade = CascadeType.ALL)
    private PlayerState targetPlayerState;
}
