package adventure;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;
import helpers.CaptureSystemOutput;
import org.junit.jupiter.api.DisplayName;
<<<<<<< HEAD
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;
=======
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
>>>>>>> 54bcede (added lab03 skeleton)

import static com.google.common.truth.Truth.assertWithMessage;

@CaptureSystemOutput
public class AdventureGameTests {
<<<<<<< HEAD
    static final String DATA_PATH = "tests/data/";
    static final Class<?> BEE_CLASS = BeeCountingStage.class;
    static final Class<?> SPECIES_CLASS = SpeciesListStage.class;
    static final Class<?> PALINDROME_CLASS = PalindromeStage.class;
    static final Class<?> MACHINE_CLASS = MachineStage.class;
    static final Class<?> GAME_CLASS = AdventureGame.class;

    /** Returns a game starting at the given stage class for the given input file. */
    private AdventureGame getGameStartingAt(Class<?> stageClass) {
        In in = new In(new File(DATA_PATH + stageClass.getSimpleName() + "/input.txt"));
        StdRandom.setSeed(1337);
        AdventureStage stage;
        try {
            stage = (AdventureStage) stageClass.getConstructor(In.class).newInstance(in);
        } catch (InvocationTargetException | InstantiationException |
                 IllegalAccessException | NoSuchMethodException e) {
=======
    static final String PREFIX_PATH = "solution/tests/data/";
    static final String BEE_PATH = "BeeCountingStage";
    static final String SPECIES_PATH = "SpeciesListStage";
    static final String PALINDROME_PATH = "PalindromeStage";
    static final String MACHINE_PATH = "MachineStage";
    static final String GAME_PATH = "game";

    static final Map<String, Class<?>> NAME_TO_STAGE = Map.of(
            BEE_PATH, BeeCountingStage.class,
            SPECIES_PATH, SpeciesListStage.class,
            PALINDROME_PATH, PalindromeStage.class,
            MACHINE_PATH, MachineStage.class,
            GAME_PATH, AdventureGame.class
    );

    private AdventureGame getGame(String stagePath) {
        In in = new In(new File(PREFIX_PATH + stagePath + "/" + "input.txt"));
        StdRandom.setSeed(1337);
        AdventureStage stage;
        try {
            stage = (AdventureStage) NAME_TO_STAGE.get(stagePath)
                    .getConstructor(In.class).newInstance(in);
        } catch (InvocationTargetException |
                 InstantiationException |
                 IllegalAccessException |
                 NoSuchMethodException e) {
>>>>>>> 54bcede (added lab03 skeleton)
            throw new RuntimeException(e);
        }
        return new AdventureGame(in, stage);
    }

<<<<<<< HEAD
    private void compareOutputToExpected(Class<?> clazz, CaptureSystemOutput.OutputCapture capture) {
        String expected = new In(new File(DATA_PATH + clazz.getSimpleName() + "/answers.txt")).readAll();
        String cleanedExpected = expected.replace("\r\n", "\n").strip();
        String cleanedCapture = capture.toString().replace("\r\n", "\n").strip();

        assertWithMessage("Game outputs for " + clazz.getSimpleName() + " did not match")
                .that(cleanedCapture).isEqualTo(cleanedExpected);
    }

    private static Stream<Arguments> argumentsForTestStage() {
        return Stream.of(
                Arguments.of(Named.of("BeeCountingStage", BEE_CLASS)),
                Arguments.of(Named.of("SpeciesListStage", SPECIES_CLASS)),
                Arguments.of(Named.of("PalindromeStage", PALINDROME_CLASS)),
                Arguments.of(Named.of("MachineStage", MACHINE_CLASS))
        );
    }

    @DisplayName("Individual stage tests")
    @ParameterizedTest
    @MethodSource("argumentsForTestStage")
    public void testStage(Class<?> stage, CaptureSystemOutput.OutputCapture capture) {
        getGameStartingAt(stage).handleStage();
        compareOutputToExpected(stage, capture);
=======
    private void compareOutputToExpected(String stagePath, CaptureSystemOutput.OutputCapture capture) {
        String expected = new In(new File(PREFIX_PATH + stagePath + "/" + "answers.txt")).readAll();
        String cleanedExpected = expected.replace("\r\n", "\n").strip();
        String cleanedCapture = capture.toString().replace("\r\n", "\n").strip();

        assertWithMessage("Game outputs for " + NAME_TO_STAGE.get(stagePath).getSimpleName() + " did not match")
                .that(cleanedCapture).isEqualTo(cleanedExpected);
    }

    @DisplayName("Individual stage tests")
    @ParameterizedTest(name = "{0}")
    @ValueSource(strings = {BEE_PATH, SPECIES_PATH, PALINDROME_PATH, MACHINE_PATH})
    public void testStage(String stagePath, CaptureSystemOutput.OutputCapture capture) {
        getGame(stagePath).handleStage();
        compareOutputToExpected(stagePath, capture);
>>>>>>> 54bcede (added lab03 skeleton)
    }

    @DisplayName("Integration test with incorrect inputs")
    @Test
    public void testGame(CaptureSystemOutput.OutputCapture capture) {
<<<<<<< HEAD
        In in = new In(new File(DATA_PATH + GAME_CLASS.getSimpleName() + "/input.txt"));
        StdRandom.setSeed(1337);
        AdventureGame game = new AdventureGame(in);
        game.play();
        compareOutputToExpected(GAME_CLASS, capture);
=======
        In in = new In(new File(PREFIX_PATH + GAME_PATH + "/" + "input.txt"));
        StdRandom.setSeed(1337);
        AdventureGame game = new AdventureGame(in);
        game.play();
        compareOutputToExpected(GAME_PATH, capture);
>>>>>>> 54bcede (added lab03 skeleton)
    }

}
