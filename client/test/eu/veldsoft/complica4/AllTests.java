package eu.veldsoft.complica4;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import eu.veldsoft.complica4.model.BoardTest;
import eu.veldsoft.complica4.model.ExampleTest;
import eu.veldsoft.complica4.model.PieceTest;
import eu.veldsoft.complica4.model.UtilTest;
import eu.veldsoft.complica4.model.ia.AbstractArtificialIntelligenceTest;
import eu.veldsoft.complica4.model.ia.AlphaBetaArtificialIntelligenceTest;
import eu.veldsoft.complica4.model.ia.ArtificialIntelligenceTest;
import eu.veldsoft.complica4.model.ia.NeuralNetworkArtificialIntelligenceTest;
import eu.veldsoft.complica4.model.ia.NoValidMoveExceptionTest;
import eu.veldsoft.complica4.model.ia.RandomArtificialIntelligenceTest;
import eu.veldsoft.complica4.model.ia.SimpleRulesArtificialIntelligenceTest;

/**
 * Runs all tests classes. No class implementation is needed. Simply running the
 * class will run all classes listed as suite classes.
 * 
 * @author Georgi Gospodinov
 */
@RunWith(Suite.class)
@SuiteClasses({ BoardTest.class, ExampleTest.class, PieceTest.class, UtilTest.class,
		AbstractArtificialIntelligenceTest.class, AlphaBetaArtificialIntelligenceTest.class,
		ArtificialIntelligenceTest.class, NeuralNetworkArtificialIntelligenceTest.class, NoValidMoveExceptionTest.class,
		RandomArtificialIntelligenceTest.class, SimpleRulesArtificialIntelligenceTest.class })
public class AllTests {

}
