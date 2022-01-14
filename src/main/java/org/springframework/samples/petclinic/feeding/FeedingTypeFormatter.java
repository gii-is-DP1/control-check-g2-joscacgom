package org.springframework.samples.petclinic.feeding;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.pet.PetService;
import org.springframework.stereotype.Component;

@Component
public class FeedingTypeFormatter implements Formatter<FeedingType>{
	private FeedingService feedingService;

	@Autowired
	public FeedingTypeFormatter(FeedingService feedingService) {
		this.feedingService = feedingService;
	}
    @Override
    public String print(FeedingType object, Locale locale) {
        // TODO Auto-generated method stub
        return object.getName();
    }

    @Override
    public FeedingType parse(String text, Locale locale) throws ParseException {
        // TODO Auto-generated method stub
    	FeedingType fp=feedingService.getFeedingType(text);
    	if(fp == null) {
    		throw new ParseException(text,0);
    	}else {
    		return fp;
    	}
       
    }
    
}
