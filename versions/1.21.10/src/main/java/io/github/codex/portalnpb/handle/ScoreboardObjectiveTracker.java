package io.github.codex.portalnpb.handle;

import io.github.codex.portalnpb.PortalNpbMod;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public final class ScoreboardObjectiveTracker {
	private static final Set<Objective> OBJECTIVES = new LinkedHashSet<>();

	private ScoreboardObjectiveTracker() {
	}

	public static void add(Objective objective) {
		if (objective.getCriteria() == ObjectiveCriteria.DUMMY && objective.getName().endsWith(PortalNpbMod.OBJECTIVE_SUFFIX)) {
			OBJECTIVES.add(objective);
		}
	}

	public static void remove(Objective objective) {
		OBJECTIVES.remove(objective);
	}

	public static Collection<Objective> getObjectives() {
		return OBJECTIVES;
	}
}
