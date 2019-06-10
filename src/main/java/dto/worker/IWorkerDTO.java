package dto.worker;

import dto.address.IAddress;
import dto.workPlace.IWorkPlaceDTO;

import java.time.LocalDate;
import java.util.List;

public interface IWorkerDTO {
	
	// Methods
	
	String getFirstName();
	void setFirstName(String firstName);
	
	String getSurName();
	void setSurName(String surName);
	
	String getEmail();
	void setEmail(String email);
	
	LocalDate getBirthday();
	void setBirthday(LocalDate birthday);
	
	IAddress getHomeAddress();
	void setHomeAddress(IAddress homeAddress);
	
	List<IWorkPlaceDTO> getIWorkPlaces();
	void setIWorkPlaces(List<IWorkPlaceDTO> workPlaces);
	
	int getWorkerID();
	void setWorkerID(int workerID);
}
