package com.telco.osb.cache.clean;
import javax.ejb.Local;

@Local
public interface CachePublishLocal {

	public String clearCacheRemote(long delay);

}
