package com.ecristobale.testing.mockito;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalMatchers.*;
import static org.mockito.Mockito.*;

//@RunWith(MockitoJUnitRunner.class) // INIT MOCK with ANNOTATIONS in JUnit 4
@ExtendWith(MockitoExtension.class) // INIT MOCK with ANNOTATIONS <-- RECOMMENDED WAY
public class MessengerTest2 {

    private static final String RANDOM_MESSAGE = "Message";
    private static final String RANDOM_EMAIL = "email@email.com";

    // One of the ways to initialize mocks marked with annotation in JUnit 4
//    @Rule
//    public MockitoRule rule = MockitoJUnit.rule();


    @Mock
    private TemplateEngine templateEngineMock;

    /*
     * Other possible answers:
          -	CALLS_REAL_METHODS
        -	RETURNS_DEFAULTS
        -	RETURNS_MOCKS
        -	RETURNS_SELF
        -	RETURNS_SMART_NULLS

     */
    // We can CREATE MOCK with @Mock annotation
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private MailServer mailServerMock;
    @InjectMocks
    private Messenger messenger;
    @Captor// Used when we want to CAPTURE the argument of the method that was executing on the entity
    private ArgumentCaptor<Email> captor;

    private AutoCloseable closeable; // INIT MOCK with ANNOTATIONS

    @Spy
    private ArrayList<Integer> list;


    @BeforeEach
    void setUp() {
        // We can CREATE MOCK based on the Interface or a Class
//    	templateEngine = mock(TemplateEngine.class);

        // DEPRECATED INIT MOCK with ANNOTATIONS
//    	MockitoAnnotations.initMocks(this);

        // just another version of INIT MOCK with ANNOTATIONS
        // pay attention to tear down method - we have to call close method
//    	closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
//    	closeable.close(); // INIT MOCK with ANNOTATIONS
    }

    @Test
    void shouldSendMessage() {
        // GIVEN
        var client = new Client(RANDOM_EMAIL);
        var template = new Template();
//      @Mock: when mock.method is called with SPECIFIC client and template return SPECIFIC RANDOM_MESSAGE
        when(templateEngineMock.prepareMessage(client, template)).thenReturn(RANDOM_MESSAGE);

//    	Row below will print: RANDOM_MESSAGE
//    	System.out.println(templateEngineMock.prepareMessage(client, template));

//      Row below will print: NULL because they are not the SPECIFIC parameters defined in when(Line 65)
//    	System.out.println(templateEngineMock.prepareMessage(new Client(RANDOM_EMAIL), new Template()));

        // WHEN
//      @InjectMock: annotated object is created and all the MOCK OBJECTS are INJECTED into it
        messenger.sendMessage(client, template);

        // THEN
//      Verify if the method was called with the SPECIFIC parameters: client and template
        verify(templateEngineMock).prepareMessage(client, template);
//      Verify if the method send an object Email (any object of Email class, did not define mock behavior so we do not search for specific parameter object)
//          We only verify if the method was called on when (sendMessage) method
        verify(mailServerMock).send(any(Email.class));
    }


    @Test
    void shouldSendMessageWithArgumentMatchers() {
        // given
        var client = new Client(RANDOM_EMAIL);
        var template = new Template();
        when(templateEngineMock.prepareMessage(any(Client.class),
                any(Template.class))).thenReturn(RANDOM_EMAIL);
        /*
         * Other possible matchers:
         * - anyString()
         * - anyInt()
         * - etc
         */


        // this statements will throw an EXCEPTION: not possible SPECIFIC client and ANY template
//    	when(templateEngineMock.prepareMessage(client, any(Template.class)))
//    		.thenReturn(RANDOM_EMAIL);

        // write this statement instead with eq() argument matcher --> SPECIFIC client with EQUALITY and ANY template
//    	when(templateEngineMock.prepareMessage(eq(client), any(Template.class)))
//			.thenReturn(RANDOM_EMAIL);

        /*
         * Also otehr matchers available:
         * - eq()
         * - isA(Class type)
         * - isNull()
         * - isNotNull()
         * - matches(regex)
         * - etc
         */

        // when
        messenger.sendMessage(client, template);

        // then
        verify(templateEngineMock).prepareMessage(client, template);
        verify(mailServerMock).send(any(Email.class));
    }


    @Test
    public void shouldThrowExceptionWhenTemplateEngineThrowsException() {
        // GIVEN
        var client = new Client(RANDOM_EMAIL);
        var template = new Template();
//      when mock.method is called with SPECIFIC client and template throw IllegalArgumentException
        when(templateEngineMock.prepareMessage(
                client, template))
                .thenThrow(new IllegalArgumentException());

        // WHEN & THEN
//      It has to throw an IllegalArgumentException on sendMessage method because of the mock behavior (exception)
        assertThrows(IllegalArgumentException.class, () ->
                messenger.sendMessage(client, template)
        );
    }

    // Verify that inside the sendMessage method the email address is set to the client's email address
    @Test
    public void shouldSetClientEmailToAddressInEmail() {
        // GIVEN
        var client = new Client(RANDOM_EMAIL);
        var template = new Template();
        when(templateEngineMock.prepareMessage(client, template))
                .thenReturn(RANDOM_MESSAGE);

        // WHEN
        messenger.sendMessage(client, template);

        // THEN
        verify(templateEngineMock).prepareMessage(client, template);
        // Verify that the email sent was the email created by the messenger on the sendMessage method
        verify(mailServerMock).send(captor.capture());
        // Verify that the email address created on the sendMessage method is the same as the client's email address
        assertEquals(client.getEmail(), captor.getValue().getAddress());
    }



//    =================================== examples 2 - MockitoAdvanced Features

    @Test
    void shouldSendMessageOnlyOneTime() {
        // GIVEN
        var client = new Client(RANDOM_EMAIL);
        var template = new Template();
        when(templateEngineMock.prepareMessage(client, template))
                .thenReturn(RANDOM_MESSAGE);

        // WHEN
        messenger.sendMessage(client, template);

        // THEN
        // Verifify if the method was called ONLY ONE time
        verify(templateEngineMock, times(1)).prepareMessage(client, template);
        verify(mailServerMock, times(1)).send(any(Email.class));


        verify(mailServerMock, atLeastOnce()).send(any(Email.class));
        verify(mailServerMock, atLeast(1)).send(any(Email.class));
        verify(mailServerMock, atMost(1)).send(any(Email.class));
//        verify(mailServerMock, never()).send(any(Email.class)); // this will fail
    }


    @Test
    void shouldSendMessageAndPrepareTemplateBeforeThat() {
        // GIVEN
        var inOrder = inOrder(templateEngineMock, mailServerMock);
        var client = new Client(RANDOM_EMAIL);
        var template = new Template();
        when(templateEngineMock.prepareMessage(client, template))
                .thenReturn(RANDOM_MESSAGE);

        // WHEN
        messenger.sendMessage(client, template);

        // THEN
        inOrder.verify(templateEngineMock).prepareMessage(client, template);
        inOrder.verify(mailServerMock).send(any(Email.class));

        // Like this test will fail
//        inOrder.verify(mailServerMock).send(any(Email.class));
//        inOrder.verify(templateEngineMock).prepareMessage(client, template);
    }


    @Test
    void shouldSendMessageAndThereIsNoMoreInteractionsWithTemplateEngine() {
        // GIVEN
        var inOrder = inOrder(templateEngineMock, mailServerMock);
        var client = new Client(RANDOM_EMAIL);
        var template = new Template();
        when(templateEngineMock.prepareMessage(client, template))
                .thenReturn(RANDOM_MESSAGE);


        // WHEN
        messenger.sendMessage(client, template);

        // THEN
        inOrder.verify(templateEngineMock).prepareMessage(client, template);
        inOrder.verify(mailServerMock).send(any(Email.class));
        verifyNoMoreInteractions(templateEngineMock, mailServerMock);

//        In this case verifyNoMoreInteractions will throw an exception
//        templateEngineMock.evaluateTemplate(null);
//        verifyNoMoreInteractions(templateEngineMock);

    }

    @Test
    void shouldCallRealMethodOnMockExample() {
        Client client = new Client("");
        Template template = new Template();

        // Println will return 'null' because we did NOT DEFINE BEHAVIOR for prepareMessage method
        System.out.println(templateEngineMock.prepareMessage(client, template));

        when(templateEngineMock.prepareMessage(client, template)).thenCallRealMethod();
        // 'Some template' will be returned because that is the REAL RESULT of prepareMessage
        System.out.println(templateEngineMock.prepareMessage(client, template));
    }


    @Test
    void shouldNotThrowExceptionWithDeepStub() {
        // GIVEN
        var client = new Client(RANDOM_EMAIL);
        var template = new Template();

        // Not null because of DEEP STUBS: @Mock(answer = Answers.RETURNS_DEEP_STUBS)
        var validator = mailServerMock.getValidator();

        // this line will throw NPE without DEEP STUBS!!!
        validator.validate(new Email());
    }

    @Test
    void additionalMatchersDemo() {
        // STUB allows to set behavior for methods with arguments
        // mocks object are more for verification of interactions
        List<String> listMock = mock(List.class);

    	when(listMock.get(or(eq(1), eq(2)))).thenReturn("more");
//    	when(listMock.get(gt(2))).thenThrow(new RuntimeException());

    	System.out.println(listMock.get(1)); // more is returned
    	System.out.println(listMock.get(2)); // more is returned
//    	System.out.println(listMock.get(3)); // RuntimeException is thrown


        /* There are other matchers, such as:
         * - or()
         * - not()
         * - lt()		- less than
         * - leq()		- less than or equal to
         * - gt()		- greater than
         * - geq()		- greater than or equal to
         * - find(regex)
         * - eq()
         * - cmpEq()
         * - aryEq()
         * - and()
         *
         *
         */

    }

    @Test
    void verifyVoidMethodExample() {
        // STUB: allows to set behavior for void methods
        List<String> listMock = mock(List.class);

        // You CANNOT use "when method" like below with VOID methods
//    	when(listMock.add(1, "first argument")).thenThrow(new IllegalArgumentException());

        // instead you can set behavior to VOID methods with DOTHROW:
//    	doThrow(IllegalArgumentException.class).when(listMock).add(1, "first argument");
//    	listMock.add(1, "");




//    	ArgumentCaptor<Comparator<String>> captor = ArgumentCaptor.forClass(Comparator.class);
//    	doNothing().when(listMock).sort(captor.capture());



        doAnswer(methodInvocation -> {
            Object arg0 = methodInvocation.getArgument(0);
            Object arg1 = methodInvocation.getArgument(1);

            assertEquals(3, arg0);
            assertEquals("List Element", arg1);
            return null;
        }).when(listMock).add(any(Integer.class), any(String.class));
        listMock.add(3, "List Element");
    }


    @Test
    public void shouldThrowExceptionWhenMockFinalClass() {
        // CANNOT stub FINAL class
//		String string = mock(String.class);
//		when(string.length()).thenReturn(20);
//		assertThat(string.length(), equalTo(20));

        // Can't mock static method
//		when(Map.of()).thenReturn(null);

        // Can't mock equals of hashCode

    }

    @Test
    void spyExample() {
        List<Integer> integerList = new ArrayList<>();
        List<Integer> spy = spy(integerList);

        // OutOfBoundsException because get method is called on REAL list that is EMPTY
//    	when(spy.get(0)).thenReturn(0);

        // Instead of when, we can use doReturn: return 0 when we call get(0) on spy list
        doReturn(0).when(spy).get(0);
        System.out.println(spy.get(0));

        verify(spy).get(0);
    }


}
