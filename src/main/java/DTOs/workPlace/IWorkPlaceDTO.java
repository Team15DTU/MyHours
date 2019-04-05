package DTOs.workPlace;

import DTOs.job.IJobDTO;
import DTOs.job.JobDTO;

import java.awt.*;
import java.util.List;

public interface IWorkPlaceDTO {
	
	// Methods

	int getWorkplaceID();
	void setWorkplaceID(int workplaceID);

	int getWorkerID();
	void setWorkerID(int workerID);

	String getName();
	void setName(String name);
	
	Color getColor();
	void setColor(Color color);

	int getTelephone();
	void setTelephone(int telephone);
	
	java.util.List<IJobDTO> getIJobList();
	void setIJobList(List<IJobDTO> iJobList);

	
}
