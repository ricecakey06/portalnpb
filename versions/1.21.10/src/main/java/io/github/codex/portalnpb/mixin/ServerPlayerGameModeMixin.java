package io.github.codex.portalnpb.mixin;

import io.github.codex.portalnpb.api.PortalBreakCacheAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerGameMode.class)
public abstract class ServerPlayerGameModeMixin {
	@Shadow
	@Final
	protected ServerPlayer player;

	@Inject(method = "destroyBlock", at = @At("HEAD"))
	private void portalnpb$beforeBreak(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		((PortalBreakCacheAccess) this.player.level()).portalnpb$markPlayerUpdated(this.player);
	}
}
