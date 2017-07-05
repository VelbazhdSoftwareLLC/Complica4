package eu.veldsoft.complica4.model.ia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.veldsoft.complica4.model.Board;
import eu.veldsoft.complica4.model.Piece;
import eu.veldsoft.complica4.model.Util;

/**
 * MonteCarlo Tree search meta heuristic is used as A.I.
 * 
 * https://en.wikipedia.org/wiki/Monte_Carlo_tree_search
 * 
 * @author Todor Balabanov
 */
public class MonteCarloArtificialIntelligence extends AbstractArtificialIntelligence {
	/**
	 * Milliseconds used for move calculation.
	 */
	private int time = 0;

	/**
	 * Constructor with parameters.
	 *
	 * @param time
	 *            Time for calculations.
	 */
	public MonteCarloArtificialIntelligence(int time) {
		super();

		this.time = time;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int move(Board board, int player) {
		super.move(board, player);

		Board original = board;
		board = new Board(original);

		Map<Integer, Integer> counters = new HashMap<Integer, Integer>();
		List<Integer> valid = new ArrayList<Integer>();
		for (int m = 0; m < Board.COLS; m++) {
			valid.add(m);
		}

		/*
		 * Initialize counters.
		 */
		for (Integer m : valid) {
			counters.put(m, 0);
		}

		/*
		 * Calculate when to stop.
		 */
		long time = this.time + System.currentTimeMillis();

		/*
		 * Experiments are limited according to the available time.
		 */
		Integer move = null;
		while (System.currentTimeMillis() < time) {
			Collections.shuffle(valid);
			move = valid.get(0);

			board.addTo(move.intValue(), Piece.get(player));
			board.next();

			/*
			 * Play until someone win.
			 */
			while (board.hasWinner() == false) {
				/*
				 * Select random column to play.
				 */
				board.addTo(Util.PRNG.nextInt(Board.COLS), Piece.whoPlays(board.getTurn()));

				/*
				 * Move to next player if the turn was valid.
				 */
				board.next();
			}
			board.setGameOver();

			/*
			 * Calculate total score.
			 */
			Map<Piece, Integer> score = board.score();
			int others = 0;
			for (Piece key : score.keySet()) {
				others += score.get(key);
			}

			/*
			 * Others have current player score that is why it should be
			 * multiplied by two.
			 */
			counters.put(move, counters.get(move) + 2 * score.get(Piece.whoPlays(original.getTurn())) - others);

			/*
			 * Reinitialize the board for the next experiment.
			 */
			board = new Board(original);
		}

		/*
		 * Evaluate the possible moves by finding the biggest number.
		 */
		int max = (Collections.max(counters.values()));
		for (Map.Entry<Integer, Integer> entry : counters.entrySet()) {
			if (entry.getValue() != max) {
				continue;
			}

			move = entry.getKey();

			/*
			 * There is no need to loop over the entire map.
			 */
			break;
		}

		return move.intValue();
	}
}
