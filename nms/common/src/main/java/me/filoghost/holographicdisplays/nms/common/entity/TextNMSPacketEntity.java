/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.holographicdisplays.nms.common.entity;

import me.filoghost.holographicdisplays.common.PositionCoordinates;
import me.filoghost.holographicdisplays.nms.common.IndividualTextPacketGroup;
import me.filoghost.holographicdisplays.nms.common.PacketGroup;

public interface TextNMSPacketEntity extends NMSPacketEntity {

    double ARMOR_STAND_Y_OFFSET = -0.29;
    double ARMOR_STAND_TEXT_HEIGHT = 0.23;

    PacketGroup newSpawnPackets(PositionCoordinates position, String text, boolean sneaking);

    IndividualTextPacketGroup newSpawnPackets(PositionCoordinates position, boolean sneaking);

    PacketGroup newChangePackets(String text, boolean sneaking);

    IndividualTextPacketGroup newChangePackets(boolean sneaking);
    default IndividualTextPacketGroup newChangePackets() {
        return newChangePackets(false);
    }


}
