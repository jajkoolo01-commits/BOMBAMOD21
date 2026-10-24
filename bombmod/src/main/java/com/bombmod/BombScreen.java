package com.bombmod;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class BombScreen extends Screen {
	public BombScreen() {
		super(Text.literal("Menu bomb"));
	}

	@Override
	protected void init() {
		int w = 200, h = 20, x = this.width / 2 - w / 2;
		int y = this.height / 2 - 55;

		addBomb("TNT pod nogami", 0, x, y, w, h);
		addBomb("Wybuch tam, gdzie patrzysz", 1, x, y + 25, w, h);
		addBomb("Deszcz TNT", 2, x, y + 50, w, h);
		addBomb("MEGA BOMBA", 3, x, y + 75, w, h);

		addDrawableChild(ButtonWidget.builder(Text.literal("Zamknij"), b -> close())
				.dimensions(x, y + 105, w, h).build());
	}

	private void addBomb(String name, int type, int x, int y, int w, int h) {
		addDrawableChild(ButtonWidget.builder(Text.literal(name), b -> {
			ClientPlayNetworking.send(new BombPayload(type));
			close();
		}).dimensions(x, y, w, h).build());
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		super.render(context, mouseX, mouseY, delta);
		context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, this.height / 2 - 80, 0xFFFFFF);
	}

	@Override
	public boolean shouldPause() {
		return false;
	}
}
