package uk.ac.cf.spring.Group13Project1.email;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    @Mock
    private JavaMailSender mydependency;

    @Test
    public void testProcessData() {

    }

    @Service
    public class EmailServices {

        private static final Logger LOG = LoggerFactory.getLogger(EmailServices.class);

        public void test() {
            LOG.trace("Mail sent to newstarterbipsync@gmail.com");
        }
    }

        @Mock
        private JavaMailSender javaMailSender;


//        @Test
//        public void testSendEmailWithAttachment() throws MessagingException {
//            // Arrange
//            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//            when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
//
//            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
//
//            // Assert
//            verify(javaMailSender).createMimeMessage();
//            verify(javaMailSender).send(mimeMessage);
//        }
    }

