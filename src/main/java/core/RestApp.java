package core;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import core.ServiceDB;
import core.ServiceGG;

public class RestApp extends Application {
	private Set<Object> singletons = new HashSet<Object>();

	public RestApp() {
		singletons.add(new ServiceDB());
		singletons.add(new ServiceGG());
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}
