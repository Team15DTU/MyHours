package dto.employer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dto.job.IJobDTO;

import java.awt.*;
import java.util.List;

@JsonDeserialize(as = EmployerDTO.class)
public interface IEmployerDTO {
	
	// Methods

	int getEmployerID();
	void setEmployerID(int employerID);

	int getWorkerID();
	void setWorkerID(int workerID);

	String getName();
	void setName(String name);

	//@JsonIgnore
	Color getColor();
	//@JsonIgnore
	void setColor(Color color);

	//@JsonIgnore
	String getTelephone();
	//@JsonIgnore
	void setTelephone(String telephone);

	//@JsonIgnore
	java.util.List<IJobDTO> getIJobList();
	//@JsonIgnore
	void setIJobList(List<IJobDTO> iJobList);

	
}
