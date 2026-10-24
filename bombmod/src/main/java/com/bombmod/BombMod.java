package com.bombmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.TntEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BombMod implements ModInitializer {
	@Override
	public void onInitialize() {
		PayloadTypeRegistry.playC2S().register(BombPayload.ID, BombPayload.CODEC);

		ServerPlayNetworking.registerGlobalReceiver(BombPayload.ID, (payload, context) -> {
			ServerPlayerEntity player = context.player();
			World world = player.getWorld();

			switch (payload.type()) {
				case 0 -> { // TNT pod nogami
					TntEntity tnt = new TntEntity(world, player.getX(), player.getY(), player.getZ(), player);
					tnt.setFuse(40);
					world.spawnEntity(tnt);
				}
				case 1 -> { // wybuch tam, gdzie patrzysz
					Vec3d pos = target(player);
					world.createExplosion(null, pos.x, pos.y, pos.z, 4.0f, World.ExplosionSourceType.TNT);
				}
				case 2 -> { // deszcz TNT
					for (int i = 0; i < 20; i++) {
						double x = player.getX() + (world.random.nextDouble() - 0.5) * 20;
						double z = player.getZ() + (world.random.nextDouble() - 0.5) * 20;
						TntEntity tnt = new TntEntity(world, x, player.getY() + 15, z, player);
						tnt.setFuse(60 + world.random.nextInt(40));
						world.spawnEntity(tnt);
					}
				}
				case 3 -> { // mega bomba
					Vec3d pos = target(player);
					world.createExplosion(null, pos.x, pos.y, pos.z, 15.0f, true, World.ExplosionSourceType.TNT);
				}
				default -> {}
			}
		});
	}

	private static Vec3d target(ServerPlayerEntity player) {
		HitResult hit = player.raycast(60.0, 0.0f, false);
		return hit.getPos();
	}
}
