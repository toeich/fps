package de.jworks.fpp.labs.indesign;

import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
	private static final String localShare = "E:/InDesign_CS6/";
	
	public static void main(String[] args) throws Exception {
		TemplateProcessor templateProcessor = new TemplateProcessor();
		
		SmbFile share = new SmbFile(remoteShare, new NtlmPasswordAuthentication(remoteDomain, remoteUsername, remotePassword));

		// clean up remote files
		{
			SmbFile inddFile = new SmbFile(share, "test.indd");
			if (inddFile.exists()) {
				inddFile.delete();
			}

			SmbFile idmlFile = new SmbFile(share, "test.idml");
			if (idmlFile.exists()) {
				idmlFile.delete();
			}
		}
		
		// copy indd file to server
		{
			SmbFile inddFile = new SmbFile(share, "test.indd");

			InputStream inputStream = InDesignService.class.getResourceAsStream("/test5.indd");
			OutputStream outputStream = inddFile.getOutputStream();
			IOUtils.copy(inputStream, outputStream);
			outputStream.close();
			inputStream.close();
		}
		
		// create script
		String script;
		{
			Map<String, Object> input = new HashMap<String, Object>();
			input.put("inddFile", localShare + "test.indd");
			input.put("idmlFile", localShare + "test.idml");

			script = templateProcessor.process("exportAsIDML.ftl", input);
		}

		// execute script
		{
			ServicePortType servicePortType = new Service().getService();

			BindingProvider bindingProvider = (BindingProvider) servicePortType;
			Map<String, Object> requestContext = bindingProvider.getRequestContext();
			requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, remoteUrl);

			ObjectFactory objectFactory = new ObjectFactory();

			RunScriptParameters runScriptParameters = objectFactory.createRunScriptParameters();
			runScriptParameters.setScriptLanguage(objectFactory.createRunScriptParametersScriptLanguage("javascript"));
			runScriptParameters.setScriptText(objectFactory.createRunScriptParametersScriptText(script));
			Holder<Integer> errorNumber = new Holder<Integer>();
			Holder<String> errorString = new Holder<String>();
			Holder<Data> scriptResult = new Holder<Data>();
			servicePortType.runScript(runScriptParameters, errorNumber, errorString, scriptResult);

			if (errorNumber.value != 0) {
				System.out.println("ERROR: " + errorString.value);
			}
		}

		// create script
		{
			Map<String, Object> input = new HashMap<String, Object>();
			input.put("inddFile", localShare + "test.indd");
			input.put("jpgFile", localShare + "test.jpg");

			script = templateProcessor.process("exportAsJPG.ftl", input);
		}

		// execute script
		{
			ServicePortType servicePortType = new Service().getService();

			BindingProvider bindingProvider = (BindingProvider) servicePortType;
			Map<String, Object> requestContext = bindingProvider.getRequestContext();
			requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, remoteUrl);

			ObjectFactory objectFactory = new ObjectFactory();

			RunScriptParameters runScriptParameters = objectFactory.createRunScriptParameters();
			runScriptParameters.setScriptLanguage(objectFactory.createRunScriptParametersScriptLanguage("javascript"));
			runScriptParameters.setScriptText(objectFactory.createRunScriptParametersScriptText(script));
			Holder<Integer> errorNumber = new Holder<Integer>();
			Holder<String> errorString = new Holder<String>();
			Holder<Data> scriptResult = new Holder<Data>();
			servicePortType.runScript(runScriptParameters, errorNumber, errorString, scriptResult);

			if (errorNumber.value != 0) {
				System.out.println("ERROR: " + errorString.value);
			}
		}

		// copy idml file from server and find page item bounds
		{
			SmbFile idmlFile = new SmbFile(share, "test.idml");
			if (idmlFile.exists())
			{
				File tmpFile = File.createTempFile("InDesignService_", ".idml");
				InputStream inputStream = idmlFile.getInputStream();
				OutputStream outputStream = new FileOutputStream(tmpFile);
				IOUtils.copy(inputStream, outputStream);
				outputStream.close();
				inputStream.close();
				
				DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
				
				XPathFactory xpathFactory = XPathFactory.newInstance();
				XPath xpath = xpathFactory.newXPath();
				
				ZipFile zipFile = new ZipFile(tmpFile);
				
				String designmapEntryName = "designmap.xml";
				ZipEntry designmapEntry = zipFile.getEntry(designmapEntryName);
				Document designmapDocument = documentBuilder.parse(zipFile.getInputStream(designmapEntry));
				
				NodeList spreadNodes = (NodeList) xpath.evaluate("//*[local-name()='Spread']", designmapDocument, XPathConstants.NODESET);
				for (Node spreadNode : new NodeListIterable(spreadNodes)) {
					String spreadEntryName = xpath.evaluate("@src", spreadNode);
					ZipEntry spreadEntry = zipFile.getEntry(spreadEntryName);
					Document spreadDocument = documentBuilder.parse(zipFile.getInputStream(spreadEntry));
					
					Node pageNode = (Node) xpath.evaluate("//Page", spreadDocument, XPathConstants.NODE);
					
					AffineTransform pageTransform = new AffineTransform(asArray(xpath.evaluate("@ItemTransform", pageNode)));
					
					NodeList pageItemNodes = (NodeList) xpath.evaluate(".//TextFrame|.//Rectangle", spreadDocument, XPathConstants.NODESET);
					for (Node pageItemNode : new NodeListIterable(pageItemNodes)) {
						System.out.println(pageItemNode.getNodeName() + ": " + xpath.evaluate("@Self", pageItemNode));
						
						AffineTransform pageItemTransform = new AffineTransform(asArray(xpath.evaluate("@ItemTransform", pageItemNode)));
						
						NodeList parentNodes = (NodeList) xpath.evaluate("parent::*", pageItemNode, XPathConstants.NODESET);
						for (Node parentNode : new NodeListIterable(parentNodes)) {
							AffineTransform parentTransform = new AffineTransform(asArray(xpath.evaluate("@ItemTransform", parentNode)));
							pageItemTransform.preConcatenate(parentTransform);
						}
						
						pageItemTransform.preConcatenate(pageTransform.createInverse());
						
						System.out.println(" Path Points:");
						double top = Double.MAX_VALUE;
						double left = Double.MAX_VALUE;
						NodeList pathPointTypeNodes = (NodeList) xpath.evaluate(".//PathPointType", pageItemNode, XPathConstants.NODESET);
						for (Node pathPointTypeNode : new NodeListIterable(pathPointTypeNodes)) {
							double[] pts = asArray(xpath.evaluate("@Anchor", pathPointTypeNode));
							pageItemTransform.transform(pts, 0, pts, 0, 1);
							
							top = Math.min(top, pts[0]);
							left = Math.min(left, pts[1]);
							
							System.out.format("  (%.3f, %.3f)\n", pts[0] * 25.4 / 72, pts[1] * 25.4 / 72);
						}
						
						Node imageNode = (Node) xpath.evaluate("Image", pageItemNode, XPathConstants.NODE);
						if (imageNode != null) {
							System.out.println(" Image:");
							AffineTransform imageTransform = new AffineTransform(asArray(xpath.evaluate("@ItemTransform", imageNode)));
							System.out.println("orig: " + imageTransform);
							
							imageTransform.preConcatenate(pageItemTransform);

							double offsetX = imageTransform.getTranslateX() - top;
							double offsetY = imageTransform.getTranslateY() - left;
							double scaleX = Math.hypot(imageTransform.getShearX(), imageTransform.getScaleX());
							double scaleY = Math.hypot(imageTransform.getShearY(), imageTransform.getScaleY());
							double angle = -Math.atan2(imageTransform.getShearY(), imageTransform.getScaleY());
							
							AffineTransform newImageTransform = new AffineTransform();
							newImageTransform.translate(offsetX + top, offsetY + left);
							newImageTransform.rotate(-angle);
							newImageTransform.scale(scaleX, scaleY);
							newImageTransform.preConcatenate(pageItemTransform.createInverse());
							System.out.println("new:  " + newImageTransform);
							
							System.out.format("  offsetX = %.3f\n", offsetX * 25.4 / 72);
							System.out.format("  offsetY = %.3f\n", offsetY * 25.4 / 72);
							System.out.format("  scaleX  = %.3f\n", scaleX * 100);
							System.out.format("  scaleY  = %.3f\n", scaleY * 100);
							System.out.format("  angle   = %.3f\n", Math.toDegrees(angle));
						}
						
						System.out.println();
					}
				}
				
				zipFile.close();
				
				tmpFile.delete();
			}
		}
	}
	
	private static double[] asArray(String s) {
		String[] sa = s.split(" ");
		double[] da = new double[sa.length];
		for (int i = 0; i < sa.length; i++) {
			da[i] = Double.parseDouble(sa[i]);
		}
		return da;
	}
	
	public static class NodeListIterable implements Iterable<Node> {
		
		private final NodeList nodeList;

		public NodeListIterable(NodeList nodeList) {
			this.nodeList = nodeList;
		}

		@Override
		public Iterator<Node> iterator() {
			return new NodeListIterator(nodeList);
		}
		
	}
	
	public static class NodeListIterator implements Iterator<Node> {
		
		private final NodeList nodeList;

		int index = 0;
		
		public NodeListIterator(NodeList nodeList) {
			this.nodeList = nodeList;
		}

		@Override
		public boolean hasNext() {
			return index < nodeList.getLength();
		}
		
		@Override
		public Node next() {
			return nodeList.item(index++);
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
	
}
