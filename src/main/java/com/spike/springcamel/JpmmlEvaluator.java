package com.spike.springcamel;

import com.jayway.jsonpath.internal.filter.Evaluator;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.model.rest.RestBindingMode;
import org.dmg.pmml.FieldName;
import org.jpmml.evaluator.EvaluatorUtil;
import org.jpmml.evaluator.InputField;
import org.jpmml.evaluator.LoadingModelEvaluatorBuilder;
import org.jpmml.evaluator.ModelEvaluator;
import org.jpmml.evaluator.visitors.DefaultVisitorBattery;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

@Component
public class JpmmlEvaluator {
    public void evaluate(String pmml, Exchange exchange) throws JAXBException, SAXException {

        ModelEvaluator evaluator = new LoadingModelEvaluatorBuilder()
                .load(JpmmlEvaluator.class.getClassLoader().getResourceAsStream("pmml/" + pmml + ".pmml"))
                .build();
        evaluator.verify();

        Map<String, Object> input = (Map<String, Object>) exchange.getIn().getBody();

        Map<FieldName, Object> arguments = (Map<FieldName, Object>) EvaluatorUtil.encodeKeys(input);

        Map<FieldName, Object> results = evaluator.evaluate(arguments);
        Map<String, Object> resultRecord = (Map<String, Object>) EvaluatorUtil.decodeAll(results);

        resultRecord.put("timestamp", LocalDateTime.now().toString());

        exchange.getIn().setBody(resultRecord);
    }

}

