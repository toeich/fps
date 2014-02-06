
package de.jworks.fpp.labs.indesign.soap;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the de.jworks.fpp.labs.indesign.soap package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SessionID_QNAME = new QName("http://ns.adobe.com/InDesign/soap/", "sessionID");
    private final static QName _FieldData_QNAME = new QName("", "data");
    private final static QName _ObjectStart_QNAME = new QName("", "start");
    private final static QName _ObjectEnd_QNAME = new QName("", "end");
    private final static QName _RunScriptResponseScriptResult_QNAME = new QName("", "scriptResult");
    private final static QName _RunScriptResponseErrorString_QNAME = new QName("", "errorString");
    private final static QName _RunScriptRunScriptParameters_QNAME = new QName("", "runScriptParameters");
    private final static QName _RunScriptParametersScriptFile_QNAME = new QName("", "scriptFile");
    private final static QName _RunScriptParametersScriptText_QNAME = new QName("", "scriptText");
    private final static QName _RunScriptParametersScriptLanguage_QNAME = new QName("", "scriptLanguage");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: de.jworks.fpp.labs.indesign.soap
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Result }
     * 
     */
    public Result createResult() {
        return new Result();
    }

    /**
     * Create an instance of {@link RunScript }
     * 
     */
    public RunScript createRunScript() {
        return new RunScript();
    }

    /**
     * Create an instance of {@link RunScriptParameters }
     * 
     */
    public RunScriptParameters createRunScriptParameters() {
        return new RunScriptParameters();
    }

    /**
     * Create an instance of {@link EndSession }
     * 
     */
    public EndSession createEndSession() {
        return new EndSession();
    }

    /**
     * Create an instance of {@link BeginSessionResponse }
     * 
     */
    public BeginSessionResponse createBeginSessionResponse() {
        return new BeginSessionResponse();
    }

    /**
     * Create an instance of {@link BeginSession }
     * 
     */
    public BeginSession createBeginSession() {
        return new BeginSession();
    }

    /**
     * Create an instance of {@link RunScriptResponse }
     * 
     */
    public RunScriptResponse createRunScriptResponse() {
        return new RunScriptResponse();
    }

    /**
     * Create an instance of {@link Data }
     * 
     */
    public Data createData() {
        return new Data();
    }

    /**
     * Create an instance of {@link Field }
     * 
     */
    public Field createField() {
        return new Field();
    }

    /**
     * Create an instance of {@link Record }
     * 
     */
    public Record createRecord() {
        return new Record();
    }

    /**
     * Create an instance of {@link IDSPScriptArg }
     * 
     */
    public IDSPScriptArg createIDSPScriptArg() {
        return new IDSPScriptArg();
    }

    /**
     * Create an instance of {@link List }
     * 
     */
    public List createList() {
        return new List();
    }

    /**
     * Create an instance of {@link ObjectList }
     * 
     */
    public ObjectList createObjectList() {
        return new ObjectList();
    }

    /**
     * Create an instance of {@link Object }
     * 
     */
    public Object createObject() {
        return new Object();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ns.adobe.com/InDesign/soap/", name = "sessionID")
    public JAXBElement<Long> createSessionID(Long value) {
        return new JAXBElement<Long>(_SessionID_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Data }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "data", scope = Field.class)
    public JAXBElement<Data> createFieldData(Data value) {
        return new JAXBElement<Data>(_FieldData_QNAME, Data.class, Field.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "start", scope = Object.class)
    public JAXBElement<Object> createObjectStart(Object value) {
        return new JAXBElement<Object>(_ObjectStart_QNAME, Object.class, Object.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "end", scope = Object.class)
    public JAXBElement<Object> createObjectEnd(Object value) {
        return new JAXBElement<Object>(_ObjectEnd_QNAME, Object.class, Object.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Data }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "scriptResult", scope = RunScriptResponse.class)
    public JAXBElement<Data> createRunScriptResponseScriptResult(Data value) {
        return new JAXBElement<Data>(_RunScriptResponseScriptResult_QNAME, Data.class, RunScriptResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "errorString", scope = RunScriptResponse.class)
    public JAXBElement<String> createRunScriptResponseErrorString(String value) {
        return new JAXBElement<String>(_RunScriptResponseErrorString_QNAME, String.class, RunScriptResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RunScriptParameters }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "runScriptParameters", scope = RunScript.class)
    public JAXBElement<RunScriptParameters> createRunScriptRunScriptParameters(RunScriptParameters value) {
        return new JAXBElement<RunScriptParameters>(_RunScriptRunScriptParameters_QNAME, RunScriptParameters.class, RunScript.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "scriptFile", scope = RunScriptParameters.class)
    public JAXBElement<String> createRunScriptParametersScriptFile(String value) {
        return new JAXBElement<String>(_RunScriptParametersScriptFile_QNAME, String.class, RunScriptParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "scriptText", scope = RunScriptParameters.class)
    public JAXBElement<String> createRunScriptParametersScriptText(String value) {
        return new JAXBElement<String>(_RunScriptParametersScriptText_QNAME, String.class, RunScriptParameters.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "scriptLanguage", scope = RunScriptParameters.class)
    public JAXBElement<String> createRunScriptParametersScriptLanguage(String value) {
        return new JAXBElement<String>(_RunScriptParametersScriptLanguage_QNAME, String.class, RunScriptParameters.class, value);
    }

}
