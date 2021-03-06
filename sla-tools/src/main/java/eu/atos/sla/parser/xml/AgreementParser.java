/**
 * Copyright 2014 Atos
 * Contact: Atos <roman.sosa@atos.net>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package eu.atos.sla.parser.xml;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.atos.sla.parser.IParser;
import eu.atos.sla.parser.ParserException;
import eu.atos.sla.parser.ValidationHandler;
import eu.atos.sla.parser.data.wsag.Agreement;
/**
 * 
 * @author Elena Garrido
 */
public class AgreementParser implements IParser<Agreement> {
	private static Logger logger = LoggerFactory.getLogger(AgreementParser.class);

	/*
	 * getWsagObject receives in serializedData the object information in xml 
	 * must returns a eu.atos.sla.parser.data.wsag.Agreement 
	 */
	@Override
	public Agreement getWsagObject(String serializedData) throws ParserException{
		Agreement agreementXML = null;
		try{
			logger.info("Will parse {}", serializedData);
			JAXBContext jaxbContext = JAXBContext.newInstance(Agreement.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			jaxbUnmarshaller.setEventHandler(new ValidationHandler());
			agreementXML = (Agreement)jaxbUnmarshaller.unmarshal(new StringReader(serializedData));
	    	logger.info("Agreement parsed {}", agreementXML);
		}catch(JAXBException e){
			throw new ParserException(e);
		}
    	return agreementXML;
	}
	/*
	 * getWsagAsSerializedData receives in serializedData xml  
	 * must return information following and xml in wsag standard.
	 */
	@Override
	public String getWsagAsSerializedData(String serializedData) throws ParserException {
		return serializedData;
	}

	/*
	 * getSerializedData receives in wsagSerialized the information in wsag standard as it was generated with the
	 * getWsagAsSerializedData method and returns it in xml 
	 */
	@Override
	public String getSerializedData(String wsagSerialized) throws ParserException{
		return wsagSerialized;
	}
	
	
}
