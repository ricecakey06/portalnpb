package io.github.codex.portalnpb.mixin;

import io.github.codex.portalnpb.api.PortalBreakCacheAccess;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public abstract class BlockItemMixin {
	@Inject(method = "place", at = @At("HEAD"))
	private void portalnpb$beforePlace(BlockPlaceContext context, CallbackInfoReturnable<InteractionResult> cir) {
		Level world = context.getLevel();
		if (world.isClientSide() || !(context.getPlayer() instanceof ServerPlayer player)) {
			return;
		}
		((PortalBreakCacheAccess) world).portalnpb$markPlayerUpdated(player);
	}
}
