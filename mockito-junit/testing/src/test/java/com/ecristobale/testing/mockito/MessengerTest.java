package com.ecristobale.testing.mockito;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@RunWith(MockitoJUnitRunner.class) // INIT MOCK with ANNOTATIONS in JUnit 4
@ExtendWith(MockitoExtension.class) // INIT MOCK with ANNOTATIONS <-- RECOMMENDED WAY
public class MessengerTest {

    private static final String RANDOM_MESSAGE = "Message";
    private static final String RANDOM_EMAIL = "email@email.com";

    // One of the ways to initialize mocks marked with annotation in JUnit 4
//    @Rule
//    public MockitoRule rule = MockitoJUnit.rule();


    @Mock // We can CREATE MOCK with @Mock annotation
    private TemplateEngine templateEngineMock;
    @Mock
    private MailServer mailServerMock;
    @InjectMocks
    private Messenger messenger;
    @Captor// Used when we want to CAPTURE the argument of the method that was executing on the entity
    private ArgumentCaptor<Email> captor;

    private AutoCloseable closeable; // INIT MOCK with ANNOTATIONS



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


}
