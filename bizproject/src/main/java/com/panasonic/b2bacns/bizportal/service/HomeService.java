/**
 * 
 */
package com.panasonic.b2bacns.bizportal.service;

import java.util.List;

import com.panasonic.b2bacns.bizportal.home.vo.HomeCompanySiteVO;
import com.panasonic.b2bacns.bizportal.home.vo.LocationVO;
import com.panasonic.b2bacns.bizportal.home.vo.Weather;

/**
 * @author fshan
 *
 */
public interface HomeService {
	List<Weather> getForecastInfo(long locationId, String currentTime);

	List<LocationVO> getLocationList(Long companyId);

	HomeCompanySiteVO getHomeSiteByCompanyId(Long companyId);
}
