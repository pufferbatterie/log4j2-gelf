package org.graylog2.log4j2;

import java.util.Map;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.apache.logging.log4j.core.config.plugins.Plugin;


@Plugin(name = "Log4jConfigurationFactory", category = ConfigurationFactory.CATEGORY)
public class GelfConfigurationFactory extends ConfigurationFactory {

	protected String server;
	protected Integer port = 12201;
	protected String hostName;
	protected Filter filter;
	protected String patternLayout;
	protected Map<String, String> additionalFields;
	protected String name = "GelfAppender";
	protected Boolean ignoreExceptions = true;
	protected Integer queueSize = 512;
	protected Integer connectTimeout = 1000;
	protected Integer reconnectDelay = 500;
	protected Integer sendBufferSize = -1;
	protected Boolean tcpNoDelay = false;
	protected Boolean tcpKeepAlive = false;
	protected Boolean includeSource = true;
	protected Boolean includeThreadContext = true;
	protected Boolean includeStackTrace = true;
	protected Boolean includeExceptionCause = false;
	
	protected String protocol = "UDP";
	protected Boolean tlsEnabled = false;
	protected Boolean tlsEnableCertificateVerification = true;
	protected String tlsTrustCertChainFilename;

	public GelfConfigurationFactory withServer(String server) {
		this.server = server;
		return this;
	}

	public GelfConfigurationFactory withPort(Integer port) {
		this.port = port;
		return this;
	}

	public GelfConfigurationFactory withHostName(String hostName) {
		this.hostName = hostName;
		return this;
	}

	public GelfConfigurationFactory withFilter(Filter filter) {
		this.filter = filter;
		return this;
	}

	public GelfConfigurationFactory withPatternLayout(String patternLayout) {
		this.patternLayout = patternLayout;
		return this;
	}

	public GelfConfigurationFactory withAdditionalFields(Map<String, String> additionalFields) {
		this.additionalFields = additionalFields;
		return this;
	}

	public GelfConfigurationFactory withName(String name) {
		this.name = name;
		return this;
	}

	public GelfConfigurationFactory withIgnoreExceptions(Boolean ignoreExceptions) {
		this.ignoreExceptions = ignoreExceptions;
		return this;
	}

	public GelfConfigurationFactory withProtocol(String protocol) {
		this.protocol = protocol;
		return this;
	}

	public GelfConfigurationFactory withQueueSize(Integer queueSize) {
		this.queueSize = queueSize;
		return this;
	}

	public GelfConfigurationFactory withConnectTimeout(Integer connectTimeout) {
		this.connectTimeout = connectTimeout;
		return this;
	}

	public GelfConfigurationFactory withReconnectDelay(Integer reconnectDelay) {
		this.reconnectDelay = reconnectDelay;
		return this;
	}

	public GelfConfigurationFactory withSendBufferSize(Integer sendBufferSize) {
		this.sendBufferSize = sendBufferSize;
		return this;
	}

	public GelfConfigurationFactory withTcpNoDelay(Boolean tcpNoDelay) {
		this.tcpNoDelay = tcpNoDelay;
		return this;
	}

	public GelfConfigurationFactory withTcpKeepAlive(Boolean tcpKeepAlive) {
		this.tcpKeepAlive = tcpKeepAlive;
		return this;
	}

	public GelfConfigurationFactory withIncludeSource(Boolean includeSource) {
		this.includeSource = includeSource;
		return this;
	}

	public GelfConfigurationFactory withIncludeThreadContext(Boolean includeThreadContext) {
		this.includeThreadContext = includeThreadContext;
		return this;
	}

	public GelfConfigurationFactory withIncludeStackTrace(Boolean includeStackTrace) {
		this.includeStackTrace = includeStackTrace;
		return this;
	}

	public GelfConfigurationFactory withIncludeExceptionCause(Boolean includeExceptionCause) {
		this.includeExceptionCause = includeExceptionCause;
		return this;
	}

	public GelfConfigurationFactory withTlsEnabled(Boolean tlsEnabled) {
		this.tlsEnabled = tlsEnabled;
		return this;
	}

	public GelfConfigurationFactory withTlsEnableCertificateVerification(Boolean tlsEnableCertificateVerification) {
		this.tlsEnableCertificateVerification = tlsEnableCertificateVerification;
		return this;
	}

	public GelfConfigurationFactory withTlsTrustCertChainFilename(String tlsTrustCertChainFilename) {
		this.tlsTrustCertChainFilename = tlsTrustCertChainFilename;
		return this;
	}


	@Override
	protected String[] getSupportedTypes() {
		return new String[] { "*" };
	}

	@Override
	public Configuration getConfiguration(LoggerContext arg0, ConfigurationSource arg1) {
		final ConfigurationBuilder<BuiltConfiguration> builder = newConfigurationBuilder();
		builder.setPackages("org.graylog2.log4j2");
		final AppenderComponentBuilder gelfAppenderBuilder = builder
			.newAppender(name, "GELF")
    		.add(builder.newLayout("PatternLayout")
    			.addAttribute("pattern", "%msg"))
    			.addAttribute("server", server)
    			.addAttribute("port", port)
    			.addAttribute("hostName", hostName)
    			.addAttribute("name", name)
    			.addAttribute("ignoreExceptions", ignoreExceptions)
    			.addAttribute("queueSize", queueSize)
    			.addAttribute("connectTimeout", connectTimeout)
    			.addAttribute("reconnectDelay", reconnectDelay)
    			.addAttribute("sendBufferSize", sendBufferSize)
    			.addAttribute("tcpNoDelay", tcpNoDelay)
    			.addAttribute("tcpKeepAlive", tcpKeepAlive)
    			.addAttribute("includeSource", includeSource)
    			.addAttribute("includeThreadContext", includeThreadContext)
    			.addAttribute("includeStackTrace", includeStackTrace)
    			.addAttribute("includeExceptionCause", includeExceptionCause)
    			.addAttribute("protocol", protocol)
    			.addAttribute("tlsEnabled", tlsEnabled)
    			.addAttribute("tlsEnableCertificateVerification", tlsEnableCertificateVerification)
    			.addAttribute("tlsTrustCertChainFilename", tlsTrustCertChainFilename);


		if(additionalFields != null )
			additionalFields.forEach((k,v)->{
				if(k != null && v != null)
					gelfAppenderBuilder.addComponent(
						builder.newKeyValuePair(k, v)); //newer api??
			});
		
		
		builder.add(
				builder.newRootLogger(Level.ALL)
					.add(builder.newAppenderRef(name)));
		
		builder.add(gelfAppenderBuilder);

		return builder.build();
	}

}
