package com.mpl.login.service;

import java.io.FileWriter;


/**

 * Esta clase permite el llamado al servicio soap, asi como la devolucion de la respuesta

 * @author: Juan David Gallego G.

 * @version: 20/08/2020

 */
import java.io.IOException;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SoapService {

	private static final Logger logger = LoggerFactory.getLogger(SoapService.class);

	
	/**

    * Método que permite agregar parametros a la solicitud soap
    
    * @parms:soapMessage mensaje a enviar a servicio SOAP

    */

	private static void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {
		
		logger.info("add params to soap service");
		SOAPPart soapPart = soapMessage.getSOAPPart();
		String myNamespace = "web";
		String myNamespaceURI = "http://webservices.sakaiproject.org/";
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);
		SOAPBody soapBody = envelope.getBody();
		SOAPElement soapBodyElem = soapBody.addChildElement("login", myNamespace);
		SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("id");
		SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("pw");
		soapBodyElem2.addTextNode("admin");
		soapBodyElem1.addTextNode("admin");
	}
	
	
	
	/**

    * Método que realiza al llamado al servicio SOAP y retorna la respuesta
     
    * @parms:soapEndpointUrl url de el servicio a ser llamado
    * 
    * @return:session creada

    */

	public static String callSoapWebService(String soapEndpointUrl) {
		try {
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();

			logger.info("Send SOAP message");

			SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(), soapEndpointUrl);
			SOAPBody soapBody = soapResponse.getSOAPBody();
			logger.info("finish SOAP message {}",soapResponse);
			NodeList nodes = soapBody.getElementsByTagName("return");
			Node node = nodes.item(0);
			String msgContent = node != null ? node.getTextContent() : "";
			soapConnection.close();
			saveJsonFile(msgContent);
			return msgContent;
		} catch (Exception e) {
			logger.error("error{}",e);
			return e.getMessage();
		}
	}
  
	
	/**

	    * Método encargado de almacenar el archivo json con los datos de la sesion
	     
	    * @parms:session variable para almacenar en el archivo json
	 
	*/
	private static void saveJsonFile(String session) {
		    JSONObject details = new JSONObject();
	        details.put("id", "admin");
	        details.put("pw", "admin");
	        details.put("session", session);
	
	        try (FileWriter file = new FileWriter("src/main/resources/details.json")) {
	        	 
	            file.write(details.toJSONString());
	            file.flush();
	 
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		
	}
	
	
	/**
	    * Método encargado de crear el mensaje para ser enviado al servicio SOAP
	    * 
	    * @return:SOAPMessage	 
	 
	*/
	private static SOAPMessage createSOAPRequest() throws Exception {
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		createSoapEnvelope(soapMessage);
		soapMessage.saveChanges();
		return soapMessage;
	}

}
