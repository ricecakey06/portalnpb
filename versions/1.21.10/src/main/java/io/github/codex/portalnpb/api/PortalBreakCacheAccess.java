package io.github.codex.portalnpb.api;

import net.minecraft.server.level.ServerPlayer;

public interface PortalBreakCacheAccess {
	void portalnpb$markPlayerUpdated(ServerPlayer player);

	void portalnpb$incrementPortalBreaks();

	void portalnpb$flushPortalBreakScores();
}
