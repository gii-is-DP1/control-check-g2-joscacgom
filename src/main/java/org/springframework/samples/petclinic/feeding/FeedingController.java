package org.springframework.samples.petclinic.feeding;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerService;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.pet.PetService;
import org.springframework.samples.petclinic.pet.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FeedingController {
	private static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "feedings/createOrUpdateFeedingForm";

	private final FeedingService feedingService;
	private final PetService petService;

	@Autowired
	public FeedingController(FeedingService feedingService,PetService petService) {
		this.feedingService = feedingService;
		this.petService=petService;
                
	}
	
	@GetMapping(value = "/feeding/create")
	public String initCreationForm(Feeding feeding, ModelMap model) {
		Feeding f = new Feeding();
		model.put("feeding", f);
		model.put("feedingTypes", this.feedingService.getAllFeedingTypes());
		model.put("pets", this.petService.getAllPets());
		return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/feeding/create")
	public String processCreationForm(@Valid Feeding feeding,Pet pet,BindingResult result, ModelMap model) throws DataAccessException, UnfeasibleFeedingException {		
		if (result.hasErrors()) {
		
			model.put("feeding", feeding);
			model.put("feedingTypes", this.feedingService.getAllFeedingTypes());
			model.put("pets", this.petService.getAllPets());
			return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
		}
		else {
                    try{
                    	//feeding.setFeedingType(null);
                    	feeding.setPet(pet);
                    	feeding.setFeedingType(feeding.getFeedingType());
                    	this.feedingService.save(feeding);
                    	
                    	
                    }catch(UnfeasibleFeedingException ex){
                        result.rejectValue("String.", "La mascota seleccionada no se le puede asignar el plan de alimentación especificado.");
                        return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
                    }
                    return "redirect:/welcome";
		}
	}

}
