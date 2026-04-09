package io.github.codex.portalnpb.mixin;

import io.github.codex.portalnpb.api.PortalBreakCacheAccess;
import io.github.codex.portalnpb.handle.ScoreboardObjectiveTracker;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.ScoreAccess;
import net.minecraft.world.scores.ScoreHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public abstract class ServerWorldMixin implements PortalBreakCacheAccess {
	@Unique
	private final Map<UUID, Integer> portalnpb$playerBreakCounts = new HashMap<>();
	@Unique
	private final Map<UUID, String> portalnpb$playerNames = new HashMap<>();
	@Unique
	private UUID portalnpb$lastUpdatingPlayerUuid;
	@Unique
	private boolean portalnpb$hasUpdateThisTick;

	@Override
	public void portalnpb$markPlayerUpdated(ServerPlayer player) {
		UUID uuid = player.getUUID();
		this.portalnpb$lastUpdatingPlayerUuid = uuid;
		this.portalnpb$hasUpdateThisTick = true;
		this.portalnpb$playerNames.put(uuid, player.getScoreboardName());
		this.portalnpb$playerBreakCounts.putIfAbsent(uuid, 0);
	}

	@Override
	public void portalnpb$incrementPortalBreaks() {
		if (!this.portalnpb$hasUpdateThisTick || this.portalnpb$lastUpdatingPlayerUuid == null) {
			return;
		}
		this.portalnpb$playerBreakCounts.merge(this.portalnpb$lastUpdatingPlayerUuid, 1, Integer::sum);
	}

	@Override
	public void portalnpb$flushPortalBreakScores() {
		if (this.portalnpb$playerBreakCounts.isEmpty()) {
			this.portalnpb$clearCache();
			return;
		}

		ServerLevel world = (ServerLevel) (Object) this;
		for (Map.Entry<UUID, Integer> entry : this.portalnpb$playerBreakCounts.entrySet()) {
			int breaks = entry.getValue();
			if (breaks <= 0) {
				continue;
			}

			ServerPlayer player = world.getServer().getPlayerList().getPlayer(entry.getKey());
			String scoreName = player != null ? player.getScoreboardName() : this.portalnpb$playerNames.get(entry.getKey());
			if (scoreName == null) {
				continue;
			}

			ScoreHolder holder = ScoreHolder.forNameOnly(scoreName);
			for (Objective objective : ScoreboardObjectiveTracker.getObjectives()) {
				ScoreAccess score = world.getScoreboard().getOrCreatePlayerScore(holder, objective);
				score.set(score.get() + breaks);
			}
		}

		this.portalnpb$clearCache();
	}

	@Inject(method = "tick", at = @At("TAIL"))
	private void portalnpb$flushAtEndOfTick(CallbackInfo ci) {
		this.portalnpb$flushPortalBreakScores();
	}

	@Unique
	private void portalnpb$clearCache() {
		this.portalnpb$playerBreakCounts.clear();
		this.portalnpb$playerNames.clear();
		this.portalnpb$lastUpdatingPlayerUuid = null;
		this.portalnpb$hasUpdateThisTick = false;
	}
}
