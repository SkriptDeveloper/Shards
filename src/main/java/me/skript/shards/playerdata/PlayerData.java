package me.skript.shards.playerdata;


import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class PlayerData {

    private UUID uuid;

    private long balance;

    private boolean payEnabled;


    public PlayerData(UUID uuid){
        this.uuid = uuid;
    }
}
