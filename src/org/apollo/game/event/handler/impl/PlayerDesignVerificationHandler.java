package org.apollo.game.event.handler.impl;

import org.apollo.game.event.handler.EventHandler;
import org.apollo.game.event.handler.EventHandlerContext;
import org.apollo.game.event.impl.PlayerDesignEvent;
import org.apollo.game.model.Appearance;
import org.apollo.game.model.entity.Player;
import org.apollo.game.model.setting.Gender;

/**
 * An {@link EventHandler} that verifies {@link PlayerDesignEvent}s.
 * 
 * @author Graham
 */
public final class PlayerDesignVerificationHandler extends EventHandler<PlayerDesignEvent> {

	@Override
	public void handle(EventHandlerContext ctx, Player player, PlayerDesignEvent event) {
		if (!valid(event.getAppearance())) {
			ctx.breakHandlerChain();
		}
	}

	/**
	 * Checks if an appearance combination is valid.
	 * 
	 * @param appearance The appearance combination.
	 * @return {@code true} if so, {@code false} if not.
	 */
	private boolean valid(Appearance appearance) {
		int[] colors = appearance.getColors();
		int[] maxColors = new int[] { 11, 15, 15, 5, 7 };
		for (int i = 0; i < colors.length; i++) {
			if (colors[i] < 0 || colors[i] > maxColors[i]) {
				return false;
			}
		}

		Gender gender = appearance.getGender();
		if (gender == Gender.MALE) {
			return validMaleStyle(appearance);
		} else if (gender == Gender.FEMALE) {
			return validFemaleStyle(appearance);
		}
		throw new IllegalArgumentException("Player can only be either male or female.");
	}

	/**
	 * Checks if a {@link Gender#FEMALE} style combination is valid.
	 * 
	 * @param appearance The appearance combination.
	 * @return {@code true} if so, {@code false} if not.
	 */
	private boolean validFemaleStyle(Appearance appearance) {
		int[] styles = appearance.getStyle();
		int[] minStyles = new int[] { 45, 255, 56, 61, 67, 70, 79 };
		int[] maxStyles = new int[] { 54, 255, 60, 65, 68, 77, 80 };
		for (int i = 0; i < styles.length; i++) {
			if (styles[i] < minStyles[i] || styles[i] > maxStyles[i]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks if a {@link Gender#MALE} style combination is valid.
	 * 
	 * @param appearance The appearance combination.
	 * @return {@code true} if so, {@code false} if not.
	 */
	private boolean validMaleStyle(Appearance appearance) {
		int[] styles = appearance.getStyle();
		int[] minStyles = new int[] { 0, 10, 18, 26, 33, 36, 42 };
		int[] maxStyles = new int[] { 8, 17, 25, 31, 34, 40, 43 };
		for (int i = 0; i < styles.length; i++) {
			if (styles[i] < minStyles[i] || styles[i] > maxStyles[i]) {
				return false;
			}
		}
		return true;
	}

}