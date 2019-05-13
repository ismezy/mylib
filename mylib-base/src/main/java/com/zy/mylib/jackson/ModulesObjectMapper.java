package com.zy.mylib.jackson;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.TimeZone;


/**
 * @author ASUS
 */
public class ModulesObjectMapper extends ObjectMapper {
	private static final long serialVersionUID = 4449957714393351443L;
	private List<Module> modules;

	public List<Module> getModules() {
		return modules;
	}

	public void setModules(List<Module> modules) {
		this.modules = modules;
		this.registerModules(modules);
	}
	
	public void setTimeZoneString(String timeZone) {
		TimeZone tz = TimeZone.getTimeZone(timeZone);
		setTimeZone(tz);
	}
}
