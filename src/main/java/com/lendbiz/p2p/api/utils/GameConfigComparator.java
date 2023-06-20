package com.lendbiz.p2p.api.utils;

import java.util.Comparator;

import com.lendbiz.p2p.api.entity.GameConfigEntity;

public class GameConfigComparator implements Comparator<GameConfigEntity> {

    @Override
    public int compare(GameConfigEntity o1, GameConfigEntity o2) {
        return o1.getId() - o2.getId();
    }

}