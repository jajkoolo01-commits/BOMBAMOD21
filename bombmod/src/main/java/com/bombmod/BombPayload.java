package com.bombmod;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record BombPayload(int type) implements CustomPayload {
	public static final CustomPayload.Id<BombPayload> ID =
			new CustomPayload.Id<>(Identifier.of("bombmod", "bomb"));
	public static final PacketCodec<RegistryByteBuf, BombPayload> CODEC =
			PacketCodec.tuple(PacketCodecs.INTEGER, BombPayload::type, BombPayload::new);

	@Override
	public CustomPayload.Id<? extends CustomPayload> getId() {
		return ID;
	}
}
