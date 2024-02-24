package it.step.casseAutomatiche.services;

import java.util.List;

import it.step.casseAutomatiche.models.TypeOfMeasure;

public interface TypeOfMeasureService {

	public List<TypeOfMeasure> getAll();
	public TypeOfMeasure getOneById(Integer id);
	public TypeOfMeasure saveTypeOfMeasure(TypeOfMeasure typeOfMeasure);
	public Boolean deleteTypeOfMeasureById(Integer id);
	
}
