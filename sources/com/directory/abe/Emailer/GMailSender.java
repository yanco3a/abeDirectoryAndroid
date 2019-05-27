package com.directory.abe.Emailer;

import com.directory.abe.Developer;
import com.sun.mail.smtp.SMTPAddressFailedException;
import com.sun.mail.smtp.SMTPAddressSucceededException;
import com.sun.mail.smtp.SMTPSendFailedException;
import com.sun.mail.smtp.SMTPSenderFailedException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Security;
import java.util.Properties;
import java.util.regex.Pattern;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.http.protocol.HTTP;

public class GMailSender extends Authenticator {
    private static final String TAG = GMailSender.class.getSimpleName();
    private String mailhost = "smtp.gmail.com";
    private String password;
    private Session session;
    private String user;

    public class ByteArrayDataSource implements DataSource {
        private byte[] data;
        private String type;

        public ByteArrayDataSource(byte[] data, String type) {
            this.data = data;
            this.type = type;
        }

        public ByteArrayDataSource(byte[] data) {
            this.data = data;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContentType() {
            if (this.type == null) {
                return "application/octet-stream";
            }
            return this.type;
        }

        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(this.data);
        }

        public String getName() {
            return "ByteArrayDataSource";
        }

        public OutputStream getOutputStream() throws IOException {
            throw new IOException("Not Supported");
        }
    }

    static {
        Security.addProvider(new JSSEProvider());
    }

    public GMailSender(String user, String password) {
        this.user = user;
        this.password = password;
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", this.mailhost);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.reportsuccess", "false");
        props.setProperty("mail.smtp.quitwait", "false");
        this.session = Session.getDefaultInstance(props, this);
        this.session.setDebug(true);
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(this.user, this.password);
    }

    public synchronized boolean sendMail(String subject, String body, String sender, String recipients) {
        boolean z;
        if (!subject.equals("")) {
            if (!body.equals("")) {
                if (!sender.equals("")) {
                    if (!recipients.equals("")) {
                        try {
                            if (isRFC5322Standard(recipients)) {
                                MimeMessage message = new MimeMessage(this.session);
                                DataHandler handler = new DataHandler(new ByteArrayDataSource(body.getBytes(), HTTP.PLAIN_TEXT_TYPE));
                                message.setSender(new InternetAddress(sender));
                                message.setSubject(subject);
                                message.setDataHandler(handler);
                                if (recipients.indexOf(44) > 0) {
                                    message.setRecipients(RecipientType.TO, InternetAddress.parse(recipients));
                                } else {
                                    message.setRecipient(RecipientType.TO, new InternetAddress(recipients));
                                    Transport transport = this.session.getTransport("smtp");
                                    transport.connect(this.mailhost, Developer.ADMIN_USERNAME, Developer.ADMIN_PASSWORD);
                                    transport.sendMessage(message, message.getAllRecipients());
                                    transport.close();
                                }
                                z = true;
                            } else {
                                z = false;
                            }
                        } catch (SMTPAddressSucceededException sase) {
                            sase.printStackTrace();
                            z = false;
                        } catch (SMTPAddressFailedException safe) {
                            safe.printStackTrace();
                            z = false;
                        } catch (SMTPSendFailedException ssfe) {
                            ssfe.printStackTrace();
                            z = false;
                        } catch (SMTPSenderFailedException ssafe) {
                            ssafe.printStackTrace();
                            z = false;
                        } catch (SendFailedException se) {
                            se.printStackTrace();
                            z = false;
                        } catch (AddressException ae) {
                            ae.printStackTrace();
                            z = false;
                        } catch (MessagingException me) {
                            me.printStackTrace();
                            z = false;
                        } catch (Exception e) {
                            e.printStackTrace();
                            z = false;
                        }
                    }
                }
            }
        }
        z = false;
        return z;
    }

    private boolean isRFC5322Standard(String givenEmail) {
        return Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$").matcher(givenEmail).matches();
    }
}
