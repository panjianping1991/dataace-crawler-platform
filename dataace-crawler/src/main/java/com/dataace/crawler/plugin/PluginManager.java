package com.dataace.crawler.plugin;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;



public class PluginManager  {
    

    private URLClassLoader urlClassLoader;

    
    private static PluginManager pluginManager = new PluginManager();
    
    private PluginManager() {

    }
    
    public static PluginManager getInstance(){
    	if(null==pluginManager){
			synchronized(PluginManager.class){
				if(null==pluginManager){
					pluginManager = new PluginManager();
				}
			}
		}
		return pluginManager;
    }
    
    
   

    public void updateUrlClassLoader(List<String> jarPaths){
        if(null != jarPaths){
            int size = jarPaths.size();
            URL[] urls = new URL[size];
            try {
                for (int i = 0; i < size; i++) {
                    urls[i] = new URL(jarPaths.get(i));
                }
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            urlClassLoader = new URLClassLoader(urls);
        }
    }
    

    public Object getClazzInstance(String className){
    	
        Class<?> clazz;
        try {
            clazz = urlClassLoader.loadClass(className);
            return clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
          
        }
     
        return null;
    }
}
