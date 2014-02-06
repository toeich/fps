package de.jworks.fpp.labs.indesign;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import de.jworks.fpp.labs.indesign.soap.Data;
import de.jworks.fpp.labs.indesign.soap.ObjectFactory;
import de.jworks.fpp.labs.indesign.soap.RunScriptParameters;
import de.jworks.fpp.labs.indesign.soap.Service;
import de.jworks.fpp.labs.indesign.soap.ServicePortType;

public class InDesignService {
	
	private static final String remoteUrl = "http://dtp-server:9086/service";
	private static final String remoteShare = "smb://dtp-server/InDesign_CS6/";
	private static final String remoteDomain = "eggheads";
	private static final String remoteUsername = "service";
	private static final String remotePassword = "!eggheads.42";
	private static final String localShare = "E:/InDesign_CS6";
	
	public static void main(String[] args) throws Exception {
		ServicePortType servicePortType = new Service().getService();
		
		BindingProvider bindingProvider = (BindingProvider) servicePortType;
		Map<String, Object> requestContext = bindingProvider.getRequestContext();
		requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://dtp-server:9086/service");
		
		ObjectFactory objectFactory = new ObjectFactory();
		
		RunScriptParameters runScriptParameters = objectFactory.createRunScriptParameters();
		runScriptParameters.setScriptLanguage(objectFactory.createRunScriptParametersScriptLanguage("javascript"));
		runScriptParameters.setScriptText(objectFactory.createRunScriptParametersScriptText("err"));
		Holder<Integer> errorNumber = new Holder<Integer>();
		Holder<String> errorString = new Holder<String>();
		Holder<Data> scriptResult = new Holder<Data>();
		servicePortType.runScript(runScriptParameters, errorNumber, errorString, scriptResult);
		
		System.out.println(errorNumber.value);
		System.out.println(errorString.value);
		System.out.println(scriptResult.value);
		
		SmbFile share = new SmbFile("smb://dtp-server/InDesign_CS6/", new NtlmPasswordAuthentication("eggheads", "service", "!eggheads.42"));
		SmbFile file = new SmbFile(share, "test.txt");
		Writer writer = new OutputStreamWriter(file.getOutputStream());
		writer.write("Hello world");
		writer.close();
	}
	
}
