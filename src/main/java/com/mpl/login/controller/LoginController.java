package com.mpl.login.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mpl.login.response.LoginRequest;
import com.mpl.login.response.LoginResponse;
import com.mpl.login.service.SoapService;

/**

 * Esta clase define el controlador principal para el servicio de login

 * @author: Juan David Gallego G.

 * @version: 20/08/2020

 */

 

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class LoginController {

	@Value("${login.soap.endpoint}")
	private String urlSoapEndpoint;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	    * Método encargado de realizar el llamado al servicio soap y retornar una respuesta al cliente
	     
	    * @parms:ResponseEntity<Object> retorna un objeto de tipo LoginResponse con los datos de sesion
	 
	*/
	@PostMapping
	public ResponseEntity<Object> getSession(@RequestBody LoginRequest request) {
		logger.info("Init call service soap");
		String session=SoapService.callSoapWebService(urlSoapEndpoint,request);
		LoginResponse response=new LoginResponse(session, "Operacion exitosa");
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

}
