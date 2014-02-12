package de.jworks.fpp.labs.indesign;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class TemplateProcessor {

	// @Inject
	private Configuration configuration;
	{
		configuration = new Configuration();
		configuration.setClassForTemplateLoading(TemplateProcessor.class, "/templates/");
	}

	public String process(String templateName, Map<String, Object> input) {
		try {
			StringWriter stringWriter = new StringWriter();
			process(templateName, input, stringWriter);
			return stringWriter.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void process(String templateName, Map<String, Object> input, Writer writer) {
		try {
			Template template = configuration.getTemplate(templateName);
			template.process(input, writer);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
