package DTOs.workPlace;

import DTOs.job.JobDTO;

import java.awt.*;
import java.util.List;

public interface IWorkPlaceDTO {
	
	// Methods
	String getName();
	void setName(String name);
	
	Color getColor();
	void setColor(Color color);
	
	int getTlf();
	void setTlf(int tlf);
	
	java.util.List<JobDTO> getJobDTOList();
	void setJobDTOList(List<JobDTO> jobDTOList);
}
