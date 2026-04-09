package io.github.codex.portalnpb.mixin;

import io.github.codex.portalnpb.api.PortalBreakCacheAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
//#if MC >= 12110
import net.minecraft.util.RandomSource;
//#endif
//#if MC >= 12110
import net.minecraft.world.level.LevelReader;
//#else
//$$ import net.minecraft.world.level.LevelAccessor;
//#endif
//#if MC >= 12110
import net.minecraft.world.level.ScheduledTickAccess;
//#endif
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NetherPortalBlock.class)
public abstract class NetherPortalBlockMixin {
	@Inject(method = "updateShape", at = @At("RETURN"))
//#if MC >= 12110
	private void portalnpb$onInvalidated(
		BlockState state,
		LevelReader level,
		ScheduledTickAccess scheduledTickAccess,
		BlockPos pos,
		Direction direction,
		BlockPos neighborPos,
		BlockState neighborState,
		RandomSource random,
		CallbackInfoReturnable<BlockState> cir
	) {
//#else
//$$ private void portalnpb$onInvalidated(
//$$ 	BlockState state,
//$$ 	Direction direction,
//$$ 	BlockState neighborState,
//$$ 	LevelAccessor level,
//$$ 	BlockPos pos,
//$$ 	BlockPos neighborPos,
//$$ 	CallbackInfoReturnable<BlockState> cir
//$$ ) {
//#endif
		if (!(level instanceof ServerLevel serverLevel)) {
			return;
		}
		if (!state.is(Blocks.NETHER_PORTAL)) {
			return;
		}
		if (cir.getReturnValue().is(Blocks.AIR)) {
			((PortalBreakCacheAccess) serverLevel).portalnpb$incrementPortalBreaks();
		}
	}
}
