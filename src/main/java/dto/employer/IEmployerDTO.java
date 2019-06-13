package dto.employer;

import dto.job.IJobDTO;

import java.awt.*;
import java.util.List;

public interface IEmployerDTO {
	
	// Methods

	int getWorkplaceID();
	void setWorkplaceID(int workplaceID);

	int getWorkerID();
	void setWorkerID(int workerID);

	String getName();
	void setName(String name);
	
	Color getColor();
	void setColor(Color color);

	String getTelephone();
	void setTelephone(String telephone);
	
	java.util.List<IJobDTO> getIJobList();
	void setIJobList(List<IJobDTO> iJobList);

	
}
