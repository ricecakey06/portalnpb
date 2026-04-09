package io.github.codex.portalnpb.mixin;

import io.github.codex.portalnpb.handle.ScoreboardObjectiveTracker;
import net.minecraft.network.chat.Component;
//#if MC >= 12100
import net.minecraft.network.chat.numbers.NumberFormat;
//#endif
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Scoreboard.class)
public abstract class ScoreboardMixin {
	@Inject(method = "addObjective", at = @At("RETURN"))
//#if MC >= 12100
	private void portalnpb$onObjectiveAdded(String name, ObjectiveCriteria criteria, Component displayName, ObjectiveCriteria.RenderType renderType, boolean displayAutoUpdate, NumberFormat numberFormat, CallbackInfoReturnable<Objective> cir) {
//#else
//$$ private void portalnpb$onObjectiveAdded(String name, ObjectiveCriteria criteria, Component displayName, ObjectiveCriteria.RenderType renderType, boolean displayAutoUpdate, CallbackInfoReturnable<Objective> cir) {
//#endif
		ScoreboardObjectiveTracker.add(cir.getReturnValue());
	}

	@Inject(method = "removeObjective", at = @At("HEAD"))
	private void portalnpb$onObjectiveRemoved(Objective objective, CallbackInfo ci) {
		ScoreboardObjectiveTracker.remove(objective);
	}
}
