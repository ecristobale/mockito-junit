package com.ecristobale.testing.junit5;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.time.Duration;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@DisplayName("Money Transaction Service test")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
/*
 * Other Display Name Generators
 *
 * - Standard
 * - Simple
 * - IndicativeSentences
 *
 */
public class MoneyTransactionServiceTest2 {

    private static final String MONEY_AMOUNT_EXCEPTION_MSG = "Money amount should be greater than 0";
    private static final String ACCOUNT_EXCEPTION_MSG = "Accounts shouldn't be null";
    private static final double RANDOM_MONEY_AMOUNT = 100;
    private static final double ZERO_MONEY_AMOUNT = 0;
    private static final double MORE_THAN_RANDOM_MONEY_AMOUNT = 200;
    private static final double NEGATIVE_MONEY_AMOUNT = -1;

    private MoneyTransactionService testInstance;

    //	@Before 			// in JUnit 4
    @BeforeEach // in JUnit 5
    void setUp() {
        testInstance = new MoneyTransactionService();
    }

    //	@After 			// in JUnit 4
    @AfterEach // in JUnit 5
    void tearDown() {
        // this method will be executed after each test method
    }

    //	@BeforeClass	// in JUnit 4 and static method!
    @BeforeAll // in JUnit 5 and static method!
    static void beforeAll() {
        // this method will be executed before all tests
    }

    //	@AfterClass // in JUnit 4
    @AfterAll // in JUnit 5
    static void afterAll() {

    }

    @Test
    @DisplayName("Verify money transaction from one account to another")
//	void transferMoneyTest() {
    void shouldTransferMoneyFromOneAccountToAnother() {
        // GIVEN
        var account1 = new Account(RANDOM_MONEY_AMOUNT);
        var account2 = new Account(ZERO_MONEY_AMOUNT);

        // WHEN
        testInstance.transferMoney(account1, account2, RANDOM_MONEY_AMOUNT);

        // THEN
        assertEquals(ZERO_MONEY_AMOUNT, account1.getMoneyAmount());
        assertEquals(RANDOM_MONEY_AMOUNT, account2.getMoneyAmount());
    }

    @Test
    void should_Throw_Exception_If_Account_From_Is_Null() {
        // GIVEN
        Account account1 = null;
        Account account2 = new Account(RANDOM_MONEY_AMOUNT);

        // WHEN & THEN
        var exception = assertThrows(IllegalArgumentException.class,
                () -> testInstance.transferMoney(account1, account2,
                        RANDOM_MONEY_AMOUNT));

        assertEquals(ACCOUNT_EXCEPTION_MSG, exception.getMessage());
    }

//	@org.junit.Test(expected = IllegalArgumentException.class)
//	public void shouldThrowExceptionIfAccountFromIsNull2() {
//		// GIVEN
//		Account account1 = null;
//		Account account2 = new Account(RANDOM_MONEY_AMOUNT);
//		testInstance = new MoneyTransactionService();
//
//		// WHEN & THEN
//		testInstance.transferMoney(account1, account2, RANDOM_MONEY_AMOUNT);
//	}

    @Test
    void shouldThrowExceptionIfAccountToIsNull() {
        // GIVEN
        Account account1 = new Account(RANDOM_MONEY_AMOUNT);
        Account account2 = null;

        // WHEN & THEN
        var exception = assertThrows(IllegalArgumentException.class,
                () -> testInstance.transferMoney(account1, account2,
                        RANDOM_MONEY_AMOUNT));

        assertEquals(ACCOUNT_EXCEPTION_MSG, exception.getMessage());
    }

    @Test
    void shouldThrowNotEnoughMoneyExceptionWhenTransferMoreMoney() {
        // GIVEN
        var account1 = new Account(RANDOM_MONEY_AMOUNT);
        var account2 = new Account(ZERO_MONEY_AMOUNT);

        // WHEN
        assertThrows(NotEnoughMoneyException.class,
                () -> testInstance.transferMoney(account1, account2,
                        MORE_THAN_RANDOM_MONEY_AMOUNT));

    }

    @Test
    void shouldThrowExceptionWhenTransferNegativeAmount() {
        // GIVEN
        var account1 = new Account();
        var account2 = new Account();

        // WHEN
        var exception = assertThrows(IllegalArgumentException.class,
                () -> testInstance.transferMoney(account1, account2,
                        NEGATIVE_MONEY_AMOUNT));

        assertEquals(MONEY_AMOUNT_EXCEPTION_MSG, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenTransferZeroMoneyAmount() {
        // GIVEN
        var account1 = new Account();
        var account2 = new Account();

        // WHEN
        var exception = assertThrows(IllegalArgumentException.class,
                () -> testInstance.transferMoney(account1, account2,
                        ZERO_MONEY_AMOUNT));

        assertEquals(MONEY_AMOUNT_EXCEPTION_MSG, exception.getMessage());
    }

    // ================ Other Assertions examples from JUnit

    @Test
    void groupedAssertionsExamples() {
        // In a grouped assertion all assertions are executed, and all
        // failures will be reported together.

        // GIVEN
        var account1 = new Account(RANDOM_MONEY_AMOUNT);
        var account2 = new Account(ZERO_MONEY_AMOUNT);

        // WHEN
        testInstance.transferMoney(account1, account2, RANDOM_MONEY_AMOUNT);

        // THEN
        assertAll("money transaction",
                () -> assertEquals(ZERO_MONEY_AMOUNT, account1.getMoneyAmount()),
                () -> assertEquals(RANDOM_MONEY_AMOUNT, account2.getMoneyAmount())
        );
    }

    @Test
    void dependentAssertionsExample() {
        // Within a code block, if an assertion fails the
        // subsequent code in the same block will be skipped.

        // GIVEN
        var account1 = new Account(RANDOM_MONEY_AMOUNT);
        var account2 = new Account(ZERO_MONEY_AMOUNT);

        assertAll("Money Transaction",
                () -> {
                    // WHEN
                    boolean isTransactionSuccess = testInstance.transferMoney(account1, account2, RANDOM_MONEY_AMOUNT);
                    assertTrue(isTransactionSuccess);

                    // Executed only if the previous assertion is valid.
                    assertAll("Money amount is changed on the accounts",
                            () -> assertEquals(ZERO_MONEY_AMOUNT, account1.getMoneyAmount()),
                            () -> assertEquals(RANDOM_MONEY_AMOUNT, account2.getMoneyAmount())
                    );
                }
        );
    }

    @Test
    void testWithTimeoutExample() {
        // GIVEN
        var account1 = new Account(RANDOM_MONEY_AMOUNT);
        var account2 = new Account(ZERO_MONEY_AMOUNT);

        assertTimeout(Duration
                .ofSeconds(1), () ->
                testInstance
                        .transferMoney(account1, account2, RANDOM_MONEY_AMOUNT));
    }

    @Test
    @Timeout(2) // 2 seconds by default
//	@Timeout(value = 100, unit = TimeUnit.MILLISECONDS) // It is configurable
    void timeoutNotExceededWithResult() {
        // GIVEN
        var account1 = new Account(RANDOM_MONEY_AMOUNT);
        var account2 = new Account(ZERO_MONEY_AMOUNT);

        // The following assertion succeeds, and returns the supplied object.
        boolean actualResult = assertTimeout(Duration.ofSeconds(1), () -> {
            return testInstance
                    .transferMoney(account1, account2, RANDOM_MONEY_AMOUNT);
        });
        assertTrue(actualResult);
    }

    @Test
    void testOnlyOnEcristobaleWorkstation() {
        assumeTrue("ecristobale".equals(System.getenv("USERNAME")),
                () -> "Aborting this test, because it is running not on "
                        + "laptop of ecristobale");

        // GIVEN
        var account1 = new Account(RANDOM_MONEY_AMOUNT);
        var account2 = new Account(ZERO_MONEY_AMOUNT);

        // The following assertion succeeds, and returns the supplied object.
        boolean actualResult = assertTimeout(Duration.ofSeconds(1), () -> {
            return testInstance
                    .transferMoney(account1, account2, RANDOM_MONEY_AMOUNT);
        });
        assertTrue(actualResult);
    }

    @ParameterizedTest
    @ValueSource(ints = {100, 200, 50, -10}) //4 times executed, 1 skipped because of negative value (no pass assumption)
    void parametrizedTestExample(int moneyAmount) {
        assumeTrue(moneyAmount > 0, () -> "Money amount can't be negative");

        var account1 = new Account(moneyAmount);
        var account2 = new Account(ZERO_MONEY_AMOUNT);

        assertTrue(testInstance.transferMoney(account1, account2, moneyAmount));

    }


    @ParameterizedTest // automatic values: null, empty
    @NullSource
    @EmptySource
    @NullAndEmptySource
    void nullAndEmptySources(String text) {
        assertTrue(text == null || text.trim().isEmpty());
    }


    @ParameterizedTest
    @MethodSource("sourceMethod")
    void testMethodSource(String arg) {
        assertNotNull(arg);
    }

    // return type can be any type that can be converted to a Stream.
    // For example: Collection, Iterator, Iterable, IntStream, DoubleStream, LongStream
    static Stream<String> sourceMethod() {
        return Stream.of("John", "Doe", "Ecristobale");
    }

    @ParameterizedTest
    @CsvSource({
            "apple,         1",
            "banana,        2",
            "'lemon, lime', 0xF1"
    })
//	@Ignore
//	@Disabled
//	@Disabled("Disabled until Defect #11 will be fixed")
    void testWithCsvSource(String fruit, int rank) {
        assertNotNull(fruit);
        assertNotEquals(0, rank);
    }
}
