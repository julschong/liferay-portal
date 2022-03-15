package com.liferay.mail;

import com.liferay.mail.exception.MailEngineException;
import com.liferay.mail.kernel.model.Account;
import com.liferay.mail.kernel.model.FileAttachment;
import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.model.SMTPAccount;

import java.util.List;

import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.InternetHeaders;

public interface MailEngine {

	public Session getSession();

	public Session getSession(Account account);

	public Session getSession(boolean cache);

	public void send(byte[] bytes) throws MailEngineException;

	public void send(
			InternetAddress from, InternetAddress to, String subject,
			String body)
		throws MailEngineException;

	public void send(
			InternetAddress from, InternetAddress to, String subject,
			String body, boolean htmlFormat)
		throws MailEngineException;

	public void send(
			InternetAddress from, InternetAddress[] to, InternetAddress[] cc,
			InternetAddress[] bcc, InternetAddress[] bulkAddresses,
			String subject, String body, boolean htmlFormat,
			InternetAddress[] replyTo, String messageId, String inReplyTo)
		throws MailEngineException;

	public void send(
			InternetAddress from, InternetAddress[] to, InternetAddress[] cc,
			InternetAddress[] bcc, InternetAddress[] bulkAddresses,
			String subject, String body, boolean htmlFormat,
			InternetAddress[] replyTo, String messageId, String inReplyTo,
			List<FileAttachment> fileAttachments)
		throws MailEngineException;

	public void send(
			InternetAddress from, InternetAddress[] to, InternetAddress[] cc,
			InternetAddress[] bcc, InternetAddress[] bulkAddresses,
			String subject, String body, boolean htmlFormat,
			InternetAddress[] replyTo, String messageId, String inReplyTo,
			List<FileAttachment> fileAttachments, SMTPAccount smtpAccount)
		throws MailEngineException;

	public void send(
			InternetAddress from, InternetAddress[] to, InternetAddress[] cc,
			InternetAddress[] bcc, InternetAddress[] bulkAddresses,
			String subject, String body, boolean htmlFormat,
			InternetAddress[] replyTo, String messageId, String inReplyTo,
			List<FileAttachment> fileAttachments, SMTPAccount smtpAccount,
			InternetHeaders internetHeaders)
		throws MailEngineException;

	public void send(
			InternetAddress from, InternetAddress[] to, InternetAddress[] cc,
			InternetAddress[] bcc, String subject, String body)
		throws MailEngineException;

	public void send(
			InternetAddress from, InternetAddress[] to, InternetAddress[] cc,
			InternetAddress[] bcc, String subject, String body,
			boolean htmlFormat, InternetAddress[] replyTo, String messageId,
			String inReplyTo)
		throws MailEngineException;

	public void send(
			InternetAddress from, InternetAddress[] to, InternetAddress[] cc,
			String subject, String body)
		throws MailEngineException;

	public void send(
			InternetAddress from, InternetAddress[] to, InternetAddress[] cc,
			String subject, String body, boolean htmlFormat)
		throws MailEngineException;

	public void send(
			InternetAddress from, InternetAddress[] to, String subject,
			String body)
		throws MailEngineException;

	public void send(
			InternetAddress from, InternetAddress[] to, String subject,
			String body, boolean htmlFormat)
		throws MailEngineException;

	public void send(MailMessage mailMessage) throws MailEngineException;

	public void send(String from, String to, String subject, String body)
		throws MailEngineException;

}